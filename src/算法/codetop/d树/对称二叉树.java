package 算法.codetop.d树;

import 算法.数据结构.TreeNode;

/**
 * @Description https://leetcode.cn/problems/symmetric-tree/
 * @Author lishoupeng
 * @Date 2022/9/7 09:24
 */
public class 对称二叉树 {

    public static boolean isSymmetric(TreeNode root) {
        return recursion(root.left, root.right);
    }

    public static boolean recursion(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null) return false;
        if (right == null) return false;
        return left.val == right.val
                && recursion(left.left, right.right)
                && recursion(left.right, right.left);
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        TreeNode treeNode1 = new TreeNode(2);
        TreeNode treeNode2 = new TreeNode(2);
        treeNode.left = treeNode1;
        treeNode.right = treeNode2;
        System.out.println(isSymmetric(treeNode));
    }

}
