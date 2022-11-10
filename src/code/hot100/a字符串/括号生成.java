package code.hot100.a字符串;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description https://leetcode.cn/problems/generate-parentheses/
 * @Author lishoupeng
 * @Date 2022/9/16 09:17
 */
public class 括号生成 {

    public static List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        generateOne(res, "", n, n);
        return res;
    }

    // left right 代表可用的 左 右括号数, 初始都是 n 个可用
    private static void generateOne(List<String> list, String string, int left, int right) {
        System.out.println("String:" + string + "; left:" + left + "; right:" + right);
        if (left == 0 && right == 0) {
            list.add(string);
            System.out.println(list);
            return;
        }
        if (left > 0) generateOne(list, string + "(", left - 1, right);
        // 可用右括号大于左括号时, 说明有 左括号 已经先放置, 才会是有效的括号组合
        if (right > left) generateOne(list, string + ")", left, right - 1);
    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }

}
