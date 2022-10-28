package code.codetop.a字符串;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description https://leetcode.cn/problems/longest-palindromic-substring/
 * @Author spli
 * @Date 2022/2/2 13:42
 * 上海自来水来自海上 就是回文串
 * 咱们仍然先滑动窗口 得到 字符串中的每种子串, 然后我们从这个子串中间开始向左右移动, 左指针和右指针相等就可以
 */
public class 最长回文子串 {

    public static List<String> huadong(String s) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            StringBuilder temp = new StringBuilder();
            temp.append(s.charAt(i));
            result.add(temp.toString());
            for (int j = i + 1; j < s.length(); j++) {
                temp.append(s.charAt(j));
                result.add(temp.toString());
            }
        }
        return result;
    }

    public static int huiwenchangdu(String s) {
        int result;
        if (s.length() == 1) {
            result = 1;
            return result;
        }
        int temp  = s.length() / 2;
        int left  = temp - 1;
        int right = temp + 1;
        if (s.length() % 2 == 0) {
            right = temp;
        }
        while (left >= 0 && right <= s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        StringBuilder temp_s = new StringBuilder();
        for (int i = left + 1; i < right; i++) {
            temp_s.append(s.charAt(i));
        }
        System.out.println("字符串:" + s + "的最长回文子串是:" + temp_s);
        return right - left - 1;
    }


    public static void main(String[] args) {
        String       param  = "babad";
        List<String> temp   = huadong(param);
        int          result = 0;
        for (String s : temp) {
            int i = huiwenchangdu(s);
            System.out.println("字符串: " + s + ", 的回文长度: " + i);
            if (i > result) {
                result = i;
            }
        }
        System.out.println("最终结果: " + result);
    }

}
