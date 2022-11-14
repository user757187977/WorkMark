package code.hot100.d树;

import code.数据结构.TreeNode;

/**
 * @Description https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/comments/
 * @Author lishoupeng
 * @Date 2022/11/14 22:16
 */
public class 树的公共祖先 {

    public static TreeNode dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = dfs(root.left, p, q);
        TreeNode right = dfs(root.right, p, q);
        if (left == null && right == null) return null;
        if (left == null) return right;
        if (right == null) return left;
        return root;
    }

    public static void main(String[] args) {
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        TreeNode asw = dfs(treeNode1, treeNode4, treeNode5);
        System.out.println(asw.val);
    }

}
