package code.hot100.a字符串;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description https://leetcode.cn/problems/longest-palindromic-substring/
 * @Author lishoupeng
 * @Date 2022/9/16 09:09
 * 回文子串:
 * 上海自来水来自海上
 * 我爱你爱我
 */
public class 最长回文子串 {

    private static int maxLength = 1;
    private static String asw = "";

    public static String longestPalindrome(String s) {
        List<String> children = findChild(s);
        for (String child : children) {
            checkHuiWen(child);
        }
        return asw;
    }

    public static List<String> findChild(String s) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(s.charAt(i));
            result.add(stringBuilder.toString());
            for (int j = i + 1; j < s.length(); j++) {
                stringBuilder.append(s.charAt(j));
                result.add(stringBuilder.toString());
            }
        }
        return result;
    }

    public static void checkHuiWen(String s) {
        if (s.length() <= maxLength) {
            return;
        }
        int middle = s.length() / 2;
        int leftMin = 0;
        int leftMax = middle - 1;
        int rightMin;
        int rightMax = s.length() - 1;
        int maxLengthTemp = 0;
        // 从中间向两边扩散
        if (s.length() % 2 == 0) {
            rightMin = middle;
        } else {
            maxLengthTemp = 1;
            rightMin = middle + 1;
        }
        while (leftMin <= leftMax && rightMin <= rightMax) {
            if (s.charAt(leftMax) == s.charAt(rightMin)) {
                leftMax--;
                rightMin++;
                maxLengthTemp = maxLengthTemp + 2;
            } else {
                break;
            }
        }
        if (maxLengthTemp > maxLength) {
            maxLength = maxLengthTemp;
            asw = s;
        }
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
//        System.out.println(longestPalindrome("abc上海自来水来自海上xyz"));
    }
}
