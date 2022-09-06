package codetop.e动态规划;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description https://leetcode-cn.com/problems/climbing-stairs/
 * @Author spli
 * @Date 2022/2/28 22:06
 * ----------------------------------------
 * 输入：n = 3; 输出：3
 * 解释：有三种方法可以爬到楼顶。
 * 1. 1 阶 + 1 阶 + 1 阶
 * 2. 1 阶 + 2 阶
 * 3. 2 阶 + 1 阶
 * ----------------------------------------
 * 思路: 爬上第 n 阶的方法数量等于两部分之和
 * 1. 爬上 n-1 阶的方法数量, 因为再爬 1 阶就能到第 n 阶
 * 2. 爬上 n-2 阶的方法数量, 因为再爬 2 阶就能到第 n 阶
 * 所以 f(n) = f(n-1) + f(n-2)
 * 注: n-台阶数, f(n)-方法数
 */
public class 爬楼梯 {

    /**
     * 容易理解版, 但是需要利用额外一个 map 来保存 台阶数与方法数的关系
     *
     * @param n 台阶数
     */
    public static int climbStairs1(int n) {
        // K:台阶数, V:方法数, 也就是 n 与 f(n) 的关系
        Map<Integer, Integer> asw = new HashMap<>();
        asw.put(0, 0);
        asw.put(1, 1);
        asw.put(2, 2);
        for (int i = 3; i <= n; i++) {
            asw.put(i, asw.get(i - 1) + asw.get(i - 2));
        }
        return asw.get(n);
    }

    /**
     * 升级版, 如果不允许使用额外的 map
     *
     * @param n 台阶数
     */
    public static int climbStairs2(int n) {
        // 这里所谓的 n+3 都是为了防止 dp[2] 下标越界
        int[] dp = new int[n + 3];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(climbStairs1(4));
    }


}
