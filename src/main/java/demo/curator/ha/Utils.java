package demo.curator.ha;

import java.util.concurrent.TimeUnit;

/**
 * @Description Utils.
 * @Author lishoupeng
 * @Date 2023/1/17 15:18
 */
public class Utils {

    public static void sleepQuietly(int waitSeconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
