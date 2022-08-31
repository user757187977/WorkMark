package codetop.a字符串;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/8/31 09:15
 */
public class 字符串相加2 {

    public static String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int add = 0;
        while (i >= 0 || j >= 0 || add > 0) {
            int x = 0;
            if (i >= 0) {
                x = num1.charAt(i) - '0';
                i--;
            }
            int y = 0;
            if (j >= 0) {
                y = num2.charAt(j) - '0';
                j--;
            }
            int temp = x + y + add;
            result.append(temp % 10);
            add = temp / 10;
        }
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(addStrings("345", "89"));
    }
}
