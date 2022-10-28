package code.hot100.字符串;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description https://leetcode.cn/problems/longest-substring-without-repeating-characters/
 * @Author lishoupeng
 * @Date 2022/9/16 08:47
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 */
public class 无重复字符的最长子串 {

    public static int lengthOfLongestSubstring(String s) {
        int asw = 0;
        for (int i = 0; i < s.length(); i++) {
            Set<Character> set = new HashSet<>();
            StringBuilder stringBuilder = new StringBuilder();
            set.add(s.charAt(i));
            stringBuilder.append(s.charAt(i));
            for (int j = i + 1; j < s.length(); j++) {
                if (!set.contains(s.charAt(j))) {
                    set.add(s.charAt(j));
                    stringBuilder.append(s.charAt(j));
                } else {
                    break;
                }
            }
            asw = Math.max(asw, stringBuilder.length());
        }
        return asw;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }
}
