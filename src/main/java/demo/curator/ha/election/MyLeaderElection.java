package demo.curator.ha.election;

import demo.curator.ha.Constant;
import demo.curator.ha.LeaderDriver;
import demo.curator.ha.Utils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.util.Random;

/**
 * @Description MyLeaderElection.
 * @Author lishoupeng
 * @Date 2023/1/16 16:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyLeaderElection extends LeaderSelectorListenerAdapter implements Closeable, LeaderDriver {

    private final LeaderSelector leaderSelector;
    private final String serverName;
    private boolean running;
    private int becomeLeaderCount = 0;
    private Random random = new Random();

    public MyLeaderElection(CuratorFramework curatorFramework, String serverName) {
        this.leaderSelector = new LeaderSelector(curatorFramework, Constant.ZK_NODE_PATH_ELECTION, this);
        this.serverName = serverName;
        // 自动重新参与选举
        leaderSelector.autoRequeue();
        this.start();
    }

    public void start() {
        leaderSelector.start();
    }

    @Override
    public void close() {
        leaderSelector.close();
    }

    @Override
    public void takeLeadership(CuratorFramework client) {
        this.running = true;
        System.out.printf("%s become active. %n", serverName);
        System.out.printf("%s become active count: %s. %n", serverName, ++becomeLeaderCount);
        doSomething();
        System.out.printf("%s finish job and release active. %n", serverName);
        this.running = false;
    }

    public void doSomething() {
        int temp = 0;
        while (temp < 100) {
            temp += random.nextInt(10);
        }
        Utils.sleepQuietly(1);
    }

    @Override
    public boolean hasLeadership() {
        return leaderSelector.hasLeadership();
    }

    @Override
    public void joinElection() {
        this.start();
    }

    @Override
    public void giveUpElection() {
        this.close();
    }

    @Override
    public void rejoinElection() {
        this.giveUpElection();
        this.joinElection();
    }

    @Override
    public boolean isActive() {
        return isRunning() && hasLeadership();
    }

}

