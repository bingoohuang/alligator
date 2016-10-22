package learning.hacker;

import com.google.common.base.Stopwatch;
import lombok.SneakyThrows;
import lombok.val;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by bingoohuang on 2016/10/11.
 */
public class StopWatchDemo {
    @SneakyThrows
    public static void main(String[] args) {
        val stopwatch = Stopwatch.createStarted();
        TimeUnit.MILLISECONDS.sleep(1000 + new Random().nextInt(2000));
        stopwatch.stop();
        System.out.println("stopwatch:" + stopwatch);
    }
}
