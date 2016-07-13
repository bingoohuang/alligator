package com.github.bingoohuang.redis.pipe;

import com.github.bingoohuang.utils.Durations;
import com.google.common.base.Splitter;
import com.google.common.net.HostAndPort;
import joptsimple.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Pipe {
    public static void main(String[] args) throws IOException {
        pipe(args);
    }

    static Splitter splitter = Splitter.on(' ');
    static Splitter setSplitter = Splitter.on(' ').limit(3);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void pipe(String[] args) throws IOException {
        System.out.println(dateFormat.format(new Date()));

        OptionParser parser = new OptionParser();
        OptionSpecBuilder hash = parser.accepts("hash");
        OptionSpec<String> redis = parser.accepts("redis").withOptionalArg().defaultsTo("127.0.0.1:6379");
        OptionSpec<String> file = parser.accepts("file").withRequiredArg();
        OptionSpec<Integer> max = parser.accepts("max").withOptionalArg().ofType(Integer.class).defaultsTo(Integer.MAX_VALUE);

        OptionSet options = parser.parse(args);

        long startMillis = System.currentTimeMillis();

        String fileName = file.value(options);
        String redisArg = redis.value(options);
        HostAndPort redisConfig = HostAndPort.fromString(redisArg).withDefaultPort(6379);
        boolean useRedisHash = options.has(hash);
        Integer maxValue = max.value(options);

        try (
                InputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);

                Jedis jedis = new Jedis(redisConfig.getHostText(), redisConfig.getPort(), 400000);
                Pipeline p = jedis.pipelined();
        ) {
            jedis.flushDB();

            String line;
            for (int cnt = 0; (line = br.readLine()) != null && cnt < maxValue; ++cnt) {
                if (useRedisHash) redisHash(line, p); else redisSet(line, p);
            }

            p.sync();

            Long dbSize = jedis.dbSize();
            System.out.println("dbSize:" + dbSize);
        }

        long costMillis = System.currentTimeMillis() - startMillis;
        System.out.println("cost : " + Durations.readableDuration(costMillis));
        System.out.println(dateFormat.format(new Date()));
    }

    private static void redisSet(String line, Pipeline p) {
        List<String> parts = setSplitter.splitToList(line);
        String key = parts.get(1);
        String value = parts.get(2);
        p.set(key, value);
    }

    public static void redisHash(String line, Pipeline p) {
        List<String> parts = splitter.splitToList(line);
        int partsSize = parts.size();
        HashMap<String, String> map = new HashMap<>(partsSize / 2 - 1);
        for (int i = 2, ii = partsSize; i + 1 < ii; i += 2) {
            map.put(parts.get(i), parts.get(i + 1));
        }

        String key = parts.get(1);
        p.hmset(key, map);
    }

}
