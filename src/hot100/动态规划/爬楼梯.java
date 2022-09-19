package hot100.动态规划;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description https://leetcode.cn/problems/climbing-stairs/
 * @Author lishoupeng
 * @Date 2022/9/16 08:28
 */
public class 爬楼梯 {

    public static int climbStairs(int n) {
        Map<Integer, Integer> aswMap = new HashMap<>();
        aswMap.put(0, 0);
        aswMap.put(1, 1);
        aswMap.put(2, 2);
        for (int i = 3; i <= n; i++) {
            aswMap.put(i, aswMap.get(i - 1) + aswMap.get(i - 2));
        }
        return aswMap.get(n);
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(5));
    }

}
