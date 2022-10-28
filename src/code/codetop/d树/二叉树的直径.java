package code.codetop.d树;

import code.数据结构.TreeNode;

/**
 * @Description https://leetcode.cn/problems/diameter-of-binary-tree/
 * @Author lishoupeng
 * @Date 2022/9/8 08:39
 * 给定一棵二叉树，你需要计算它的直径长度。
 * 一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
 */
public class 二叉树的直径 {

    private static int max = 0;

    public static int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return max;
    }

    public static int dfs(TreeNode root) {
        if (root.left == null && root.right == null) {
            return 1;
        }
        int leftSize = 0;
        if (root.left != null) {
            leftSize = dfs(root.left);
        }
        int rightSize = 0;
        if (root.right != null) {
            rightSize = dfs(root.right);
        }
        max = Math.max(max, leftSize + rightSize);
        return Math.max(leftSize, rightSize);

    }

    public static void main(String[] args) {
        TreeNode treeNode0 = new TreeNode(0);
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        treeNode0.left = treeNode1;
        treeNode0.right = treeNode2;
        treeNode1.left = treeNode3;
//        treeNode1.right = treeNode4;
//        treeNode3.left = treeNode5;
        System.out.println(diameterOfBinaryTree(treeNode0));
    }
}
