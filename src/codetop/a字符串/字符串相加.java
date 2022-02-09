package codetop.a字符串;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/2 14:29
 * 输入: num1 = "11", num2 = "123"
 * 输出: "134"
 * 输入: num1 = "456", num2 = "77"
 * 输出: "533"
 */
public class 字符串相加 {

    public static String add(String num1, String num2) {
        String[] arr    = new String[Math.max(num1.length(), num2.length())];
        String   result = "";
        int      length = Math.min(num1.length(), num2.length());
        for (int i = length-1; length >= 0; length--) {
            int temp = Integer.parseInt(String.valueOf(num1.charAt(i))) +  Integer.parseInt(String.valueOf(num2.charAt(i)));
            System.out.println(temp);
//            temp % 10?

        }
        return result;
    }

    public static void main(String[] args) {
        String result = add("11", "123");
        System.out.println(result);
    }
}
