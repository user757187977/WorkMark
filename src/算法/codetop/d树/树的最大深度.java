package 算法.codetop.d树;

import 算法.数据结构.TreeNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/3/3 09:53
 */
public class 树的最大深度 {

    static int maxLevel = 0;

    public static int test(TreeNode root) {
        if (root == null) {
            return 0;
        }
        dfs(root, 1);
        return maxLevel;
    }

    public static void dfs(TreeNode root, int level) {
        if (root == null)
            return;
        if (level > maxLevel) maxLevel = level;
        dfs(root.left, level + 1);
        dfs(root.right, level + 1);
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

        int result = test(treeNode);
        System.out.println(result);
    }

}
