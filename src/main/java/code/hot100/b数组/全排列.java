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
        int[] visited = new int[nums.length];
        backtrack(asw, nums, new ArrayList<>(), visited);
        return asw;
    }

    private static void backtrack(List<List<Integer>> asw, int[] nums, ArrayList<Integer> tmp, int[] visited) {
        if (tmp.size() == nums.length) {
            asw.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] == 1) continue;
            visited[i] = 1;
            tmp.add(nums[i]);
            backtrack(asw, nums, tmp, visited);
            visited[i] = 0;
            tmp.remove(tmp.size() - 1);
        }
    }


    public static void main(String[] args) {
        System.out.println(permute(new int[]{1, 2, 3}));
    }
}
