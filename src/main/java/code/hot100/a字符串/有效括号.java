package code.hot100.a字符串;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description https://leetcode.cn/problems/valid-parentheses/
 * @Author lishoupeng
 * @Date 2022/9/16 09:00
 * 输入：s = "()"
 * 输出：true
 */
public class 有效括号 {
    public static boolean isValid(String s) {
        boolean asw = false;
        Map<Character, Character> map = new HashMap<>(3);
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        for (int i = 0; i < s.length() - 1; i = i + 2) {
            char char1 = s.charAt(i);
            char char2 = s.charAt(i + 1);
            if (map.get(char1) != null && map.get(char1).equals(char2)) {
                asw = true;
            } else {
                asw = false;
                break;
            }
        }
        return asw;
    }

    public static void main(String[] args) {
        System.out.println(isValid("(){}[]"));
        System.out.println(isValid("(){}1[]"));
    }
}
