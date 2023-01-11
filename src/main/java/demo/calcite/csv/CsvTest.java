package demo.calcite.csv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import demo.calcite.utils.CalciteUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2023/1/10 10:55
 */
public class CsvTest {
    public static void main(String[] args) {
        String filePath = "/model.json";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = CalciteUtils.getConnect(filePath);
            statement = connection.createStatement();
            String[] strArray = {
                    "select * from DEMO.JOB"
                    , "select * from DEMO.INSTANCE"
                    , "select * from DEMO.INSTANCE i left join DEMO.JOB j on i.JOBID = j.ID"
            };

            for (String sql : strArray) {
                ResultSet resultSet = statement.executeQuery(sql);
                System.out.println("-------------------------  " + "start sql" + "  -------------------------  ");
                System.out.println(JSON.toJSONString(CalciteUtils.getData(resultSet), SerializerFeature.PrettyFormat));
                System.out.println("-------------------------  " + "end sql" + "  -------------------------  ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(statement).close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
