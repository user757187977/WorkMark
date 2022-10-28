package code.codetop.a字符串;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/8/31 21:22
 */
public class 无重复字符的最长子串2 {

    public static int lengthOfLongestSubstring(String s) {
        int result = 0;
        int length = s.length();
        Set<Character> check = new HashSet<>();
        for (int i = 0; i < length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(s.charAt(i));
            check.add(s.charAt(i));
            for (int j = i + 1; j < length; j++) {
                if (check.contains(s.charAt(j))) {
                    break;
                } else {
                    sb.append(s.charAt(j));
                    check.add(s.charAt(j));
                }
            }
            if (sb.toString().length() > result) {
                result = sb.toString().length();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }

}
