package demo.curator.ha;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Description CuratorFactory.
 * @Author lishoupeng
 * @Date 2023/1/17 11:26
 */
public class CuratorFactory {

    private CuratorFramework curatorFramework;

    public CuratorFactory() {
        initializeCuratorFramework(
                Constant.ZK_CONN_STRING, Constant.ZK_SESSION_TIMEOUT_MS, Constant.ZK_CONNECTION_TIMEOUT_MS,
                Constant.ZK_BASE_SLEEP_TIME_MS, Constant.ZK_MAX_RETRIES
        );
        start();
    }

    /**
     * 初始化 CuratorFramework.
     */
    public void initializeCuratorFramework(
            String zkConnString, int zkSessionTimeoutMs, int zkConnectionTimeoutMs, int zkBaseSleepTimeMs, int zkMaxRetries
    ) {
        curatorFramework = CuratorFrameworkFactory.newClient(
                zkConnString, zkSessionTimeoutMs, zkConnectionTimeoutMs,
                new ExponentialBackoffRetry(zkBaseSleepTimeMs, zkMaxRetries)
        );
    }

    public CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }

    public void start() {
        if (curatorFramework != null && CuratorFrameworkState.STARTED != curatorFramework.getState()) {
            curatorFramework.start();
        }
    }

    public void close() {
        curatorFramework.close();
    }

}
