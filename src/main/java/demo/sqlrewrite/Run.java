package demo.sqlrewrite;

import org.antlr.runtime.TokenRewriteStream;
import org.apache.hadoop.hive.ql.lib.Node;
import org.apache.hadoop.hive.ql.parse.ASTNode;
import org.apache.hadoop.hive.ql.parse.HiveParser_IdentifiersParser;
import org.apache.hadoop.hive.ql.parse.ParseDriver;
import org.apache.hadoop.hive.ql.parse.ParseException;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2023/2/8 17:53
 */
public class Run {

    static class CteRewrite {

        private final TokenRewriteStream tokenRewriteStream;
        private boolean inCTEContext = false;
        private final String content;

        public CteRewrite(TokenRewriteStream tokenRewriteStream, String content) {
            this.tokenRewriteStream = tokenRewriteStream;
            this.content = content;
        }

        public void visit(ASTNode astNode) {
            if (HiveParser_IdentifiersParser.TOK_CTE == astNode.getType()) {
                this.inCTEContext = true;
            }
            if (inCTEContext && HiveParser_IdentifiersParser.TOK_CTE == astNode.getType()) {
                tokenRewriteStream.insertAfter(astNode.getTokenStopIndex(), content);
                this.inCTEContext = false;
            }
            if (astNode.getChildCount() <= 0) {
                return;
            }
            for (Node child : astNode.getChildren()) {
                visit((ASTNode) child);
            }
        }
    }

    public static String addContentAfterWith(String sql, String content) {
        try {
            ParseDriver parseDriver = new ParseDriver();
            ASTNode astNode = parseDriver.parse(sql);
            ParseDriver.ANTLRNoCaseStringStream antlrNoCaseStringStream = parseDriver.new ANTLRNoCaseStringStream(sql);
            ParseDriver.HiveLexerX lexer = parseDriver.new HiveLexerX(antlrNoCaseStringStream);
            TokenRewriteStream tokenRewriteStream = new TokenRewriteStream(lexer);
            new CteRewrite(tokenRewriteStream, "\n" + content + "\n").visit(astNode);
            return tokenRewriteStream.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return sql;
        }
    }

    public static void main(String[] args) {
        String sql = "with a as (select * from b.c) select * from a";
        String content = " INSERT OVERWRITE DIRECTORY '/xx/xxx' stored as avro ";
        String newSql = addContentAfterWith(sql, content);
        System.out.println(newSql);
    }
}
