package com.github.bingoohuang.redis.pipe;

import com.github.bingoohuang.utils.Durations;
import com.google.common.base.Splitter;
import com.google.common.net.HostAndPort;
import joptsimple.OptionParser;
import lombok.Cleanup;
import lombok.val;
import org.joda.time.DateTime;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Pipe {
    public static void main(String[] args) throws IOException {
        pipe(args);
    }

    static Splitter splitter = Splitter.on(' ');
    static Splitter setSplitter = Splitter.on(' ').limit(3);

    public static void pipe(String[] args) throws IOException {
        System.out.println(DateTime.now());

        val parser = new OptionParser();
        val hash = parser.accepts("hash");
        val redis = parser.accepts("redis").withOptionalArg().defaultsTo("127.0.0.1:6379");
        val file = parser.accepts("file").withRequiredArg();
        val max = parser.accepts("max").withOptionalArg().ofType(Integer.class).defaultsTo(Integer.MAX_VALUE);
        val options = parser.parse(args);

        long startMillis = System.currentTimeMillis();

        String fileName = file.value(options);
        String redisArg = redis.value(options);
        val redisConfig = HostAndPort.fromString(redisArg).withDefaultPort(6379);
        boolean useRedisHash = options.has(hash);
        Integer maxValue = max.value(options);

        @Cleanup val fis = new FileInputStream(fileName);
        @Cleanup val isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
        @Cleanup val br = new BufferedReader(isr);

        String redisHost = redisConfig.getHost();
        int redisPort = redisConfig.getPort();
        @Cleanup val jedis = new Jedis(redisHost, redisPort, 400000);
        @Cleanup Pipeline p = jedis.pipelined();

        jedis.flushDB();

        String line;
        for (int cnt = 0; (line = br.readLine()) != null && cnt < maxValue; ++cnt) {
            if (useRedisHash) redisHash(line, p);
            else redisSet(line, p);
        }

        p.sync();

        Long dbSize = jedis.dbSize();
        System.out.println("dbSize:" + dbSize);

        long costMillis = System.currentTimeMillis() - startMillis;
        System.out.println("cost : " + Durations.readableDuration(costMillis));
        System.out.println(DateTime.now());
    }

    private static void redisSet(String line, Pipeline p) {
        val parts = setSplitter.splitToList(line);
        String key = parts.get(1);
        String value = parts.get(2);

        p.set(key, value);
    }

    public static void redisHash(String line, Pipeline p) {
        val parts = splitter.splitToList(line);
        int partsSize = parts.size();
        Map<String, String> map = new HashMap<>(partsSize / 2 - 1);
        for (int i = 2, ii = partsSize; i + 1 < ii; i += 2) {
            map.put(parts.get(i), parts.get(i + 1));
        }

        String key = parts.get(1);
        p.hmset(key, map);
    }

}
