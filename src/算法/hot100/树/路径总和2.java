package 算法.hot100.树;

import 算法.数据结构.TreeNode;

/**
 * @Description 从任意节点 到 任意节点符合的总数
 * https://leetcode.cn/problems/path-sum-iii/
 * @Author lishoupeng
 * @Date 2022/10/24 08:43
 */
public class 路径总和2 {

    public static int pathSum(TreeNode root, int targetSum) {
        if (root == null) return 0;
        int ret = rootSum(root, targetSum);
        ret += pathSum(root.left, targetSum);
        ret += pathSum(root.right, targetSum);
        return ret;
    }

    public static int rootSum(TreeNode root, int targetSum) {
        int ret = 0;
        if (root == null) return 0;
        int val = root.val;
        if (val == targetSum) {
            ret++;
        }
        ret += rootSum(root.left, targetSum - val);
        ret += rootSum(root.right, targetSum - val);
        return ret;
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
        System.out.println(pathSum(treeNode0, 6));
    }


}
