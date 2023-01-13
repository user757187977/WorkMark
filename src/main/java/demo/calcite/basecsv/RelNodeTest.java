package demo.calcite.basecsv;

import demo.calcite.utils.CalciteUtils;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.sql.SqlExplainLevel;

import java.sql.Connection;
import java.util.Objects;

public class RelNodeTest {

    private static final String SQL1 = "select * from TEST_CSV.TEST01 t1 left join TEST_CSV.TEST02 t2 on t1.NAME1=t2.NAME3 where t1.NAME1='hello'";
    private static final String SQL2 = "select * from TEST_CSV.TEST01 where TEST01.NAME1='hello'";
    private static final String FILE_PATH = "/model.json";
    private static final Connection connection = CalciteUtils.getConnect(FILE_PATH);

    public static void main(String[] args) {
        RelRoot root = Utils.sql2RelRoot(connection, SQL2);
        assert root != null;
        System.out.println("----------------- before optimizer ------------------");
        System.out.println(RelOptUtil.toString(root.rel, SqlExplainLevel.ALL_ATTRIBUTES));
        RelNode relNode = Utils.relNodeOptimization(Objects.requireNonNull(root).rel);
        System.out.println("----------------- after optimizer ------------------");
        System.out.println(RelOptUtil.toString(relNode, SqlExplainLevel.ALL_ATTRIBUTES));
    }


}
