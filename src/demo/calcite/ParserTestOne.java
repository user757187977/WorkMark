package demo.calcite;

import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;

public class ParserTestOne {

    public static void main(String[] args) throws SqlParseException {
        String sql = "insert overwrite table ares.application_summary partition (p_date = '${yesterday}') " +
                "select business_id, metric, vvv, dt from t3";
        SqlParser parser = SqlParser.create(sql, SqlParser.Config.DEFAULT);
        SqlNode sqlNode = parser.parseStmt();
        System.out.println(sqlNode);
    }

}
