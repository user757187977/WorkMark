package code.codetop.a字符串;

/**
 * @Description https://leetcode.cn/problems/add-strings/
 * @Author spli
 * @Date 2022/2/2 14:29
 * 输入: num1 = "11", num2 = "123"
 * 输出: "134"
 * 输入: num1 = "456", num2 = "77"
 * 输出: "533"
 */
public class 字符串相加 {

    public static String addStrings(String num1, String num2) {
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        int add = 0;
        StringBuilder ans = new StringBuilder();
        while (i >= 0 || j >= 0 || add != 0) {
            int x = 0;
            if (i >= 0) {
                x = num1.charAt(i) - '0';
            }
            int y = 0;
            if (j >= 0) {
                y = num2.charAt(j) - '0';
            }
            int result = x + y + add;
            ans.append(result % 10);
            add = result / 10;
            i--;
            j--;
        }
        // 计算完以后的答案需要翻转过来
        ans.reverse();
        return ans.toString();
    }

    public static void main(String[] args) {
        String result = addStrings("456", "77");
        System.out.println(result);
    }
}
