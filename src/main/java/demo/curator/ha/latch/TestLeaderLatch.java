package demo.curator.ha.latch;

import demo.curator.ha.CuratorFactory;
import demo.curator.ha.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description TestLeaderLatch
 * @Author lishoupeng
 * @Date 2023/1/16 16:23
 */
public class TestLeaderLatch {

    private static final int TEN = 10;
    private CuratorFactory curatorFactory;
    List<MyLeaderLatch> myLeaderLatches = new ArrayList<>(TEN);
    private final CountDownLatch countDownLatch = new CountDownLatch(TEN);
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(TEN);

    @Before
    public void init() {
        curatorFactory = new CuratorFactory();
        for (int i = 1; i <= TEN; i++) {
            int finalI = i;
            fixedThreadPool.execute(() -> {
                myLeaderLatches.add(new MyLeaderLatch(curatorFactory.getCuratorFramework(), "Client #" + finalI));
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            fixedThreadPool.shutdown();
        }
        Utils.sleepQuietly(5);
    }

    @Test
    public void testCheckLeader() {
        MyLeaderLatch active;
        active = getActive();
        System.out.printf("current leader is:%s %n", active.getServerId());

        active.close();
        myLeaderLatches.remove(active);

        Utils.sleepQuietly(5);

        active = getActive();
        System.out.printf("current leader is:%s %n", active.getServerId());
    }

    @After
    public void close() {
        for (MyLeaderLatch myLeaderLatch : myLeaderLatches) {
            myLeaderLatch.close();
        }
        curatorFactory.close();
    }

    public MyLeaderLatch getActive() {
        return myLeaderLatches.stream().filter(MyLeaderLatch::isActive).findFirst().orElse(null);
    }

}