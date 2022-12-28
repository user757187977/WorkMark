package demo.calcite;

import org.apache.calcite.sql.SqlCall;
import org.apache.calcite.sql.SqlDataTypeSpec;
import org.apache.calcite.sql.SqlDynamicParam;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlIntervalQualifier;
import org.apache.calcite.sql.SqlLiteral;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.util.SqlVisitor;

/**
 * @Description Visitor.
 * @Author lishoupeng
 * @Date 2022/12/28 15:17
 */
public class Visitor implements SqlVisitor<Void> {

    /**
     * Visits a literal.
     *
     * @param literal Literal
     * @see SqlLiteral#accept(SqlVisitor)
     */
    @Override
    public Void visit(SqlLiteral literal) {
        return null;
    }

    /**
     * Visits a call to a {@link SqlOperator}.
     *
     * @param call Call
     * @see SqlCall#accept(SqlVisitor)
     */
    @Override
    public Void visit(SqlCall call) {
        return null;
    }

    /**
     * Visits a list of {@link SqlNode} objects.
     *
     * @param nodeList list of nodes
     * @see SqlNodeList#accept(SqlVisitor)
     */
    @Override
    public Void visit(SqlNodeList nodeList) {
        return null;
    }

    /**
     * Visits an identifier.
     *
     * @param id identifier
     * @see SqlIdentifier#accept(SqlVisitor)
     */
    @Override
    public Void visit(SqlIdentifier id) {
        return null;
    }

    /**
     * Visits a datatype specification.
     *
     * @param type datatype specification
     * @see SqlDataTypeSpec#accept(SqlVisitor)
     */
    @Override
    public Void visit(SqlDataTypeSpec type) {
        return null;
    }

    /**
     * Visits a dynamic parameter.
     *
     * @param param Dynamic parameter
     * @see SqlDynamicParam#accept(SqlVisitor)
     */
    @Override
    public Void visit(SqlDynamicParam param) {
        return null;
    }

    /**
     * Visits an interval qualifier
     *
     * @param intervalQualifier Interval qualifier
     * @see SqlIntervalQualifier#accept(SqlVisitor)
     */
    @Override
    public Void visit(SqlIntervalQualifier intervalQualifier) {
        return null;
    }
}
