package code.codetop.d树;

import code.数据结构.TreeNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/3/1 09:41
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * 输出：3
 * 解释：节点 5 和节点 1 的最近公共祖先是节点 3 。
 */
public class 二叉树的最近公共祖先 {

    public static TreeNode test(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        if (root == p || root == q) {
            return root;
        }
        TreeNode left  = test(root.left, p, q);
        TreeNode right = test(root.right, p, q);
        if (left != null && right != null) {
            return root;
        } else if (left != null) {
            return left;
        } else if (right != null) {
            return right;
        }
        return null;
    }

    public static void main(String[] args) {
        TreeNode treeNode  = new TreeNode(3);
        TreeNode treeNode1 = new TreeNode(5);
        TreeNode treeNode2 = new TreeNode(1);
        TreeNode treeNode3 = new TreeNode(6);
        TreeNode treeNode4 = new TreeNode(2);
        TreeNode treeNode5 = new TreeNode(0);
        TreeNode treeNode6 = new TreeNode(8);
        TreeNode treeNode7 = new TreeNode(7);
        TreeNode treeNode8 = new TreeNode(4);

        treeNode4.setLeft(treeNode7);
        treeNode4.setRight(treeNode8);

        treeNode1.setLeft(treeNode3);
        treeNode1.setRight(treeNode4);

        treeNode2.setLeft(treeNode5);
        treeNode2.setRight(treeNode6);

        treeNode.setLeft(treeNode1);
        treeNode.setRight(treeNode2);

        TreeNode result = test(treeNode, treeNode1, treeNode2);
        System.out.println(result.getVal());
    }
}
