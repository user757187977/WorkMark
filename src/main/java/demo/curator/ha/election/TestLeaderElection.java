package demo.curator.ha.election;

import demo.curator.ha.CuratorFactory;
import demo.curator.ha.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.utils.CloseableUtils;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description TestLeaderElection.
 * @Author lishoupeng
 * @Date 2023/1/16 16:29
 */
@Slf4j
public class TestLeaderElection {

    private static final int TEN = 10;
    private CuratorFactory curatorFactory;
    List<MyLeaderElection> leaderSelectorListenerList = new ArrayList<>();
    private final CountDownLatch countDownLatch = new CountDownLatch(TEN);
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(TEN);

    @Test
    public void test() {
        curatorFactory = new CuratorFactory();
        for (int i = 1; i <= TEN; i++) {
            int finalI = i;
            fixedThreadPool.execute(() -> {
                leaderSelectorListenerList.add(new MyLeaderElection(curatorFactory.getCuratorFramework(), "Client #" + finalI));
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("countDownLatch.await got error", e);
            Thread.currentThread().interrupt();
        } finally {
            fixedThreadPool.shutdown();
        }
        // 模拟在 1min 内, 节点 抢主-执行-释放主 的过程.
        Utils.sleepQuietly(60);
    }

    /**
     * 测试完毕关闭连接
     */
    @After
    public void close() {
        for (MyLeaderElection election : leaderSelectorListenerList) {
            CloseableUtils.closeQuietly(election);
        }
        curatorFactory.close();
    }

}
