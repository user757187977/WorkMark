package 算法.codetop.d树;

import 算法.数据结构.TreeNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/3/16 09:59
 * https://leetcode-cn.com/problems/sum-root-to-leaf-numbers/comments/
 * 输入：root = [1,2,3]
 * 输出：25
 * 解释：
 * 从根到叶子节点路径 1->2 代表数字 12
 * 从根到叶子节点路径 1->3 代表数字 13
 * 因此，数字总和 = 12 + 13 = 25
 */
public class 根到叶子结点数字求和 {

    static int result = 0;

    public static int test(TreeNode root) {
        dfs(root, result);
        return result;
    }

    public static void dfs(TreeNode root, int val) {
        if (root == null) {
            return;
        }
        int k = (val * 10 + root.val);
        if (root.left == null && root.right == null) {
            result += k;
        }
        dfs(root.left, k);
        dfs(root.right, k);
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
