package Demo.calcite;

import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;

/**
 * Author yuqi
 * Time 10/5/19 15:12
 **/
public class ParserTestOne {


    public static void main(String[] args) throws SqlParseException {
        String sql = "insert overwrite table ares.application_summary partition (p_date = '${yesterday}') " +
                "select business_id, metric, vvv, dt from t3";

        SqlParser parser = SqlParser.create(sql, SqlParser.Config.DEFAULT);
        SqlNode sqlNode = parser.parseStmt();
        System.out.println(sqlNode);

    }

}
