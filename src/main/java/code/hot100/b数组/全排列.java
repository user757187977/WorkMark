package code.hot100.b数组;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description https://leetcode.cn/problems/permutations/
 * @Author lishoupeng
 * @Date 2023/2/2 08:23
 * 回溯
 */
public class 全排列 {

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> asw = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        int[] visited = new int[nums.length];
        backtrack(asw, temp, nums, visited);
        return asw;
    }

    /*
    def backtrack(路径, 选择列表):
        if 满足结束条件:
            result.add(路径)
            return

        for 选择 in 选择列表:
            做选择
            backtrack(路径, 选择列表)
            撤销选择
    */

    private static void backtrack(List<List<Integer>> asw, List<Integer> tmp, int[] nums, int[] visited) {
        if (tmp.size() == nums.length) {
            asw.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] == 1) continue;
            visited[i] = 1;
            tmp.add(nums[i]);
            backtrack(asw, tmp, nums, visited);
            visited[i] = 0;
            tmp.remove(tmp.size() - 1);
        }
    }


    public static void main(String[] args) {
        System.out.println(permute(new int[]{1, 2, 3}));
    }
}
