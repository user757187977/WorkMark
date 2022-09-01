package codetop.a字符串;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Description https://leetcode.cn/problems/valid-parentheses/
 * @Author spli
 * @Date 2022/1/31 10:53
 * 输入: s = "()"
 * 输出: true
 * 输入：s = "()[]{}"
 * 输出: true
 */
public class 有效括号 {

    public static boolean test(String s) {
        HashMap<Character, Character> map = new HashMap<>();
        map.put('{', '}');
        map.put('(', ')');
        map.put('[', ']');
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.isEmpty() || c != map.get(stack.pop())) {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String  s      = "()[]{}(";
        boolean result = test(s);
        System.out.println("字符串:\"" + s + "\" 是否包含有效括号:" + result);
    }

}
