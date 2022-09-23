package 算法.codetop.d树;

import 算法.数据结构.TreeNode;

/**
 * @Description https://leetcode.cn/problems/maximum-depth-of-binary-tree/solution/er-cha-shu-de-zui-da-shen-du-by-leetcode-solution/
 * @Author lishoupeng
 * @Date 2022/9/7 09:10
 */
public class 二叉树最大深度 {

    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            System.out.println("当前的 root:" + root.val + " 开始递归左节点");
            int leftHeight = maxDepth(root.left);
            System.out.println("当前的 root:" + root.val + " 开始递归右节点");
            int rightHeight = maxDepth(root.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode0 = new TreeNode(0);
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        treeNode0.left = treeNode1;
        treeNode0.right = treeNode2;
        treeNode1.left = treeNode3;
        System.out.println("二叉树的最大深度:" + maxDepth(treeNode0));
    }
}
