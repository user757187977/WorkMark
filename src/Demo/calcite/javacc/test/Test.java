package demo.calcite.javacc.test;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/12/23 11:45
 */
public class Test {

    public static void main(String[] args) throws ParseException {
        String expr = "1 + 1 * 10";
        Calculator calculator = new Calculator(expr);
        double res = calculator.calc();
        System.out.println(res);
    }
}
