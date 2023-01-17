package demo.curator.ha;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2023/1/16 16:47
 */
public class Constant {
    public static final String ZK_CONN_STRING = "localhost:2181";
    public static final int ZK_SESSION_TIMEOUT_MS = 5000;
    public static final int ZK_CONNECTION_TIMEOUT_MS = 10000;
    public static final int ZK_BASE_SLEEP_TIME_MS = 5000;
    public static final int ZK_MAX_RETRIES = 3;
    public static final String ZK_NODE_PATH_LATCH = "/curator/latch";
    public static final String ZK_NODE_PATH_ELECTION = "/curator/election";
}
