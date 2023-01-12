package demo.calcite.run;

import demo.calcite.utils.CalciteUtils;
import demo.calcite.visitor.Visitor;
import lombok.Data;
import org.apache.calcite.adapter.enumerable.EnumerableConvention;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.plan.volcano.VolcanoPlanner;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.sql.SqlExplainLevel;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.validate.SqlValidator;

/**
 * @Description CBOTest.
 * @Author lishoupeng
 * @Date 2022/12/28 16:25
 */
@Data
public class CBOTest {

    /**
     * relNodeFindBestExp.
     *
     * @param relNode https://javadoc.io/doc/org.apache.calcite/calcite-core/1.18.0/org/apache/calcite/rel/RelNode.html
     * @param planner https://javadoc.io/doc/org.apache.calcite/calcite-core/1.18.0/org/apache/calcite/plan/volcano/VolcanoPlanner.html
     * @return RelNode
     */
    public static RelNode cBoRelNodeFindBestExp(RelNode relNode, VolcanoPlanner planner) {
        RelTraitSet desiredTraits = relNode.getCluster().traitSet().replace(EnumerableConvention.INSTANCE);
        // 特征转化
        relNode = planner.changeTraits(relNode, desiredTraits);
        // From Doc: Finds the most efficient expression to implement the query given via RelOptPlanner.setRoot(org.apache.calcite.rel.RelNode).
        // Trans: 对于给定的 Root 寻找最有效的表达式来实现查询
        // 这个 setRoot 的入参是 RelNode
        planner.setRoot(relNode);
        return planner.findBestExp();
    }

    public static void main(String[] args) throws SqlParseException {
        // users 表的字段叫 id, 可以特意写成 ids, 触发下 main.java.demo.calcite.CBOTest.validate() 的报错
        String sql = "select u.id as user_id, u.name as user_name, j.company as user_company, u.age as user_age " +
                "from users u join jobs j on u.id=j.id " +
                "where u.age > 30 and j.id > 10 " +
                "order by user_id";

        SqlNode parsedSqlNode = CalciteUtils.parse(sql);
        Visitor visitor = new Visitor();
        parsedSqlNode.accept(visitor);
        System.out.println("Parser 之后的 SqlNode: " + parsedSqlNode);
        System.out.println("The raw sql: " + sql);
        System.out.println("------------------------");
        System.out.println("orderBy: " + visitor.getOrderByColumnNames());
        System.out.println("select: " + visitor.getSelectColumnNames());
        System.out.println("tables: " + visitor.getSelectTableNames());
        System.out.println("where: " + visitor.getWhereColumnNames());

        SqlValidator sqlValidator = CalciteUtils.createValidator();
        SqlNode validatedSqlNode = CalciteUtils.validate(parsedSqlNode, sqlValidator);
        System.out.println("验证之后的 SqlNode: " + validatedSqlNode.toString());

        VolcanoPlanner volcanoPlanner = CalciteUtils.createVolcanoPlanner();
        RelNode relNode = CalciteUtils.sQLNode2RelNode(validatedSqlNode, sqlValidator, volcanoPlanner);
        System.out.println("RelNode:");
        System.out.println(RelOptUtil.toString(relNode, SqlExplainLevel.ALL_ATTRIBUTES));

        RelNode cBoBestExpRelNode = cBoRelNodeFindBestExp(relNode, volcanoPlanner);
        System.out.println("CBO 优化后: " + RelOptUtil.toString(cBoBestExpRelNode, SqlExplainLevel.ALL_ATTRIBUTES));
    }
}
