package codetop.字符串;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author spli
 * @Date 2022/1/31 10:55
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3
 * 从 a 开始, 分别出现了 a, ab, abc, abcb 但是 abcb 有两个 b, 有重复, 所以从 a 开始的最长无重复子串是 abc
 * 从 b 开始, 分别出现了 b, bc, bca, bcab 但是 bcab 有两个 b, 有重复, 所以从 b 开始的最长无重复子串是 bca
 */
public class 无重复字符的最长子串 {

    public static String test(String s) {
        String result = null;
        if (null == s || "".equals(s)) {
            return null;
        }
        Set<Character> check  = new HashSet<>();
        int            length = s.length();
        for (int i = 0; i < length; i++) {
            StringBuilder temps  = new StringBuilder();
            char          temp_i = s.charAt(i);
            check.add(temp_i);
            temps.append(temp_i);
            for (int j = i + 1; j < length; j++) {
                char temp_j = s.charAt(j);
                if (check.contains(temp_j)) {
                    break;
                } else {
                    check.add(temp_j);
                    temps.append(temp_j);
                }
            }
            System.out.println(temps.toString());
            check.clear();
        }
        return result;
    }

    public static void main(String[] args) {
        test("abcabcbb");
    }
}
