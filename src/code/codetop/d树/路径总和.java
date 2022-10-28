package code.codetop.d树;

import code.数据结构.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author spli
 * @Date 2022/4/28 21:37
 * https://leetcode-cn.com/problems/path-sum-ii/
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：[[5,4,11,2],[5,8,4,5]]
 */
public class 路径总和 {

    static List<List<Integer>> result = new ArrayList<>();
    static List<Integer>       list   = new ArrayList<>();

    private static List<List<Integer>> test(TreeNode root, int target) {
        dfs(root, target);
        return result;
    }

    public static void dfs(TreeNode root, int target) {
        if (root == null) {
            return;
        }
        System.out.println(root.getVal());
        list.add(root.val);
        target = target - root.val;
        if (root.left == null && root.right == null && target == 0) {
            result.add(new ArrayList<>(list));
        }
        dfs(root.left, target);
        dfs(root.right, target);
        list.remove(list.size() - 1);
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(
                5,
                new TreeNode(
                        4,
                        new TreeNode(
                                11,
                                new TreeNode(7),
                                new TreeNode(2)
                        ),
                        null
                ),
                new TreeNode(
                        8,
                        new TreeNode(13),
                        new TreeNode(
                                4,
                                new TreeNode(5),
                                new TreeNode(1)
                        )
                )
        );
        List<List<Integer>> res = test(treeNode, 22);
        System.out.println("the result: ");
        for (List<Integer> temp : res) {
            for (Integer i : temp) {
                System.out.print(i + ",");
            }
            System.out.println("");
        }
    }
}
