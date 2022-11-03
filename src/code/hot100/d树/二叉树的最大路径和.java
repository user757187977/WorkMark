package code.hot100.d树;

import code.数据结构.TreeNode;

/**
 * @Description https://leetcode.cn/problems/binary-tree-maximum-path-sum/
 * @Author lishoupeng
 * @Date 2022/9/14 09:09
 */
public class 二叉树的最大路径和 {

    static int maxSum = Integer.MIN_VALUE;

    public static int maxPathSum(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftGain = Math.max(maxPathSum(node.left), 0);
        int rightGain = Math.max(maxPathSum(node.right), 0);
        int currentNodeMaxGain = node.val + leftGain + rightGain;
        maxSum = Math.max(maxSum, currentNodeMaxGain);
        return node.val + Math.max(leftGain, rightGain);
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
        maxPathSum(treeNode0);
        System.out.println(maxSum);
    }

}
