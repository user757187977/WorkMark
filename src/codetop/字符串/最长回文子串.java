package codetop.字符串;

/**
 * 回文的意思是正着念和倒着念一样，如：上海自来水来自海上
 */
public class 最长回文子串 {

    public static void main(String[] args) {
        最长回文子串 最长回文子串 = new 最长回文子串();
        System.out.printf("结果：%s\n", 最长回文子串.longestPalindrome("acccacbbbcab"));
    }

    public String longestPalindrome(String s) {
        int[] result = new int[2]; //result 保存结果
        char[] str = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            i = findLongest(str, i, result);
        }
        return s.substring(result[0], result[1] + 1);
    }

    /**
     * 注：「天然」的回文，指的就是后面的字符与当前字符一样的情况；比如：aaa、bbbb，这就是「天然」的回文
     * 遍历到每一个下标 idx，先去找这个下标后面有没有跟当前值一样的，因为你后面值都一样，「天然」地就形成了回文；然后把这一串字符串的首位两个指针当做判断回文的起点；
     * 比如：arr={acbbbbca}
     * 我现在遍历到下标 idx=2，我先去判断值：b 后面一直到下标=5，他们是值一样的，然后我把这一串值：bbbb 的low=2；high=5 当做我去判断回文的两个起点，
     * 然后分别 low-- 和 high++ ；他们对应的值分别为 c 和 c，形成回文，那么就继续...
     * 总结：
     * 用当前的下标往后遍历得到属于重复字符这种「天然」的回文，不管是真的形成「天然」的回文，比如：bbb；还是没有形成「天然」的回文，只是一个单独的字符，比如：b
     * 都要去定义这个「天然」回文的两个指针，low & high，如果 low-- 和 high++ 对应的值相等，就形成了回文
     * Q：那为什么这个方法需要返回 i 呢；
     * A：其实不返回这个 i 也不影响结果，返回 i 确实能少一定次数的遍历，但是我确实没看明白具体原因
     */
    public static int findLongest(char[] str, int low, int[] result) {
        int high = low;
        //先去查找是否有「天然」的回文，这个「天然」回文的字符串的首位两个指针，是我们 low-- & high++ 的第一步；不过，就算没有
        while (high < str.length - 1) {
            char c = str[low];
            char c1 = str[high + 1];
            if (c == c1) {
                high++;
                System.out.printf("进入特殊处理的场景，因为 low 对应的值：%s 与 high+1 对应的值：%s 一样，high++，得到：%s\n", c, c1, high);
            } else {
                break;
            }
        }
        StringBuilder temp = new StringBuilder();
        for (int i = low; i <= high; i++) {
            temp.append(str[i]);
        }
        System.out.printf("当前遍历到下标：%s，「天然」的回文串是：%s，low：%s；high：%s\n", low, temp.toString(), low, high);
        int ans = high;//定位中间部分的最后一个字符
        while (low > 0 && high < str.length - 1) {//从中间向左右扩散
            char lowc = str[low - 1];
            char highc = str[high + 1];
            System.out.printf("low - 1：%s；high + 1：%s\n", lowc, highc);
            if (lowc == highc) {
                System.out.print("low - 1 与 high + 1 形成回文，那就 low-- high++ 再试验下能否形成回文\n");
                low--;
                high++;
            } else {
                System.out.print("low - 1 与 high + 1 没有形成回文，退出\n");
                break;
            }
        }
        if (high - low > result[1] - result[0]) {
            System.out.printf("新生成范围更大，新下标-high：%s，新下标-low：%s；老下标-high：%s，老下标-low：%s\n", high, low, result[1], result[0]);
            result[0] = low;
            result[1] = high;
        }
        System.out.printf("返回的新起点：%s\n", ans);
        return ans;
    }

}
