package demo.curator.ha;

/**
 * @Description LeaderDriver.
 * @Author lishoupeng
 * @Date 2023/1/17 11:57
 */
public interface LeaderDriver {

    boolean hasLeadership();

    void joinElection();

    void giveUpElection();

    void rejoinElection();

    boolean isActive();

}
