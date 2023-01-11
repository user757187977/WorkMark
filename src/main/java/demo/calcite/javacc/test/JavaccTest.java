package demo.calcite.javacc.test;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/12/26 17:40
 */
public class JavaccTest {
    public static void main(String[] args) throws ParseException {
        Calculator calculator = new Calculator("1 + 1 * 2");
        System.out.println(calculator.calc());
    }
}
