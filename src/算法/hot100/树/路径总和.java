package 算法.hot100.树;

import 算法.数据结构.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 从 根 到 叶 符合预期的, 返回路径
 * https://leetcode.cn/problems/path-sum-ii/
 * @Author lishoupeng
 * @Date 2022/10/23 22:06
 */
public class 路径总和 {

    static List<List<Integer>> res = new ArrayList<>();

    public static List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<Integer> cur = new ArrayList<>();
        dfs(root, cur, 0, sum);
        return res;
    }

    public static void dfs(TreeNode node, List<Integer> cur, int sum, int target) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null && node.val + sum == target) {
            cur.add(node.val);
            res.add(new ArrayList<>(cur));
            cur.remove(cur.size() - 1);
            return;
        }
        cur.add(node.val);
        dfs(node.left, cur, sum + node.val, target);
        dfs(node.right, cur, sum + node.val, target);
        cur.remove(cur.size() - 1);
    }

    public static void main(String[] args) {
        TreeNode treeNode0 = new TreeNode(0);
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(6);
        treeNode0.left = treeNode1;
        treeNode0.right = treeNode2;
        treeNode1.left = treeNode3;
        treeNode1.right = treeNode4;
        treeNode2.left = treeNode5;
        treeNode2.right = treeNode6;
        System.out.println(pathSum(treeNode0, 4));
    }
}
