package demo.curator.ha.latch;

import demo.curator.ha.Constant;
import demo.curator.ha.LeaderDriver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description MyLeaderLatch.
 * @Author lishoupeng
 * @Date 2023/1/17 11:03
 */
@Slf4j
@Data
public class MyLeaderLatch implements LeaderLatchListener, LeaderDriver {

    private final LeaderLatch leaderLatch;
    private final String serverId;
    private boolean running;

    public MyLeaderLatch(CuratorFramework curatorFramework, String serverId) {
        this.leaderLatch = new LeaderLatch(curatorFramework, Constant.ZK_NODE_PATH_LATCH, serverId);
        this.serverId = serverId;
        leaderLatch.addListener(this);
        this.joinElection();
        // Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void isLeader() {
        System.out.printf("serverId:%s is leader.%n", serverId);
        running = true;
    }

    @Override
    public void notLeader() {
        System.out.printf("serverId:%s not leader.%n", serverId);
        running = false;
    }

    @Override
    public boolean isActive() {
        return isRunning() && hasLeadership();
    }

    @Override
    public boolean hasLeadership() {
        return leaderLatch.hasLeadership();
    }

    @Override
    public void joinElection() {
        try {
            this.leaderLatch.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void giveUpElection() {
        try {
            leaderLatch.close();
        } catch (IOException e) {
            log.error("Error close:", e);
        }
    }

    @Override
    public void rejoinElection() {
        giveUpElection();
        joinElection();
    }

    public void close() {
        try {
            this.leaderLatch.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void await() {
        try {
            this.leaderLatch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("await got error", e);
            Thread.currentThread().interrupt();
        }
    }

}
