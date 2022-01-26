package codetop.字符串;

import java.util.HashSet;
import java.util.Set;

/**
 * 滑动窗口思路：
 * 1.在字符串 S 中使用左右指针，left = right = 0，left、right 两个指针形成一个封闭区间称为窗口，[left,right]
 * 2.不断增加 right，扩大窗口，最大化的符合窗口字符串的要求
 * 3.出现不符合的场景，停止增加 right，转而增加一位 left，另启一个窗口，重复第二步
 * 4.当符合场景，我们执行第二步，我们移动 right，逐渐「试探」这个最大范围；当不符合时，我们执行第三步，停止「试探」调整 left，「灵活」的调整我们的目标，然后重新开始「试探」；
 * 对比 abcabcbb
 * 先「试探」得到窗口为 abc，满足要求，当再进入 a，窗口变成 abca，不满足要求，所以我们要「灵活」的调整窗口
 * 怎么调整窗口？
 * 左指针移动一位，也就是把左边的元素移除就行，循环上面的步骤，直到满足题目要求
 * 一直维持这样的窗口，找出窗口出现最长的长度时候，得结果
 * 总结：
 * 在起点处，定义左右两个指针，移动右指针，在符合你需求的情况下，最大化你的窗口
 * 当不符合场景时候，右指针停，左指针移动一位，因为上面在移动右指针的场景是在左指针没变化的场景下移动的，那么这时候我们移动左指针，让我们有个新的「机会」来寻找符合我需求的情况
 * 左指针移动一位之后，右指针继续向右移动，再次去寻找符合你需求的情况，当右指针再次被中断的时，移动左指针，一直到左指针达到尽头
 */
public class 无重复字符的最长子串 {

    public static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();// 哈希集合，记录每个字符是否出现过
        int n = s.length();
        int right = -1;// 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int result = 0;
        for (int left = 0; left < n; ++left) {
            System.out.printf("左指针：%s，右指针：%s\n", left, right);
            System.err.printf("左：%s；右：%s\n", left, right);
            if (left != 0) {
                Character c = s.charAt(left - 1);
                set.remove(c);// 左指针向右移动一格，移除一个字符
                System.out.printf("删除左指针的元素：%s，现在的 set：%s\n", c, set.toString());
            }
            while (right + 1 < n) {
                Character character = s.charAt(right + 1);
                System.out.printf("当前右指针：%s，拿到的字符：%s\n", right, character);
                if (set.contains(character)) {
                    System.out.printf("set 中包含：%s，于是退出有右指针循环\n", character);
                    break;
                } else {
                    set.add(character);
                    ++right;
                    System.out.printf("set 中没有 %s，于是把：%s 放入 set 中，现在的 set：%s，右指针：%s\n", character, character, set.toString(), right);
                }
                System.err.printf("左：%s；右：%s；value：%s\n", left, right, set.toString());
            }
            result = Math.max(result, right - left + 1);// 第 i 到 rk 个字符是一个极长的无重复字符子串
            System.out.printf("当前 result：%s\n", result);
        }
        return result;
    }


    public static void main(String[] args) {
        String temp = "abcabcbb";
        System.out.printf("%s 最后结果：%s\n", temp, lengthOfLongestSubstring("abcdabcbb"));
        /**
         *  left right value
         *  0    0     a
         *  0    1     ab
         *  0    2     abc
         *  0    3     abca
         *  1    3     bca
         *  1    4     bcab
         *  2    4     cab
         *  2    5     cabc
         *  3    5     abc
         *  3    6     abcb
         *  4    6     bcb
         *  5    6     cb
         *  5    7     cbb
         *  6    7     bb
         *  7    7     b
         * 最大值：3
         */
    }
}
