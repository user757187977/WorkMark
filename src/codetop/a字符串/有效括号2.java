package codetop.a字符串;

import java.util.HashMap;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/9/1 08:58
 */
public class 有效括号2 {
    public static boolean isValid(String s) {
        HashMap<Character, Character> map = new HashMap<>();
        map.put('{', '}');
        map.put('(', ')');
        map.put('[', ']');
        int length = s.length() - 2;
        for (int i = 0; i <= length; i = i + 2) {
            char s1 = s.charAt(i);
            char s2 = s.charAt(i + 1);
            // 成对
            if (!map.get(s1).equals(s2)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isValid("(){}[]{}"));
    }
}
