package 算法.codetop.d树;

import 算法.数据结构.TreeNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/3/4 09:37
 * https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/solution/er-cha-shu-zhong-de-zui-da-lu-jing-he-by-leetcode-/
 */
public class 二叉树的最大路径和 {

    static int maxSum = Integer.MIN_VALUE;

    public static int test(TreeNode root) {
        dfs(root);
        return maxSum;
    }

    public static int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }

        //计算左边分支最大值，左边分支如果为负数还不如不选择
        int leftMax = Math.max(dfs(root.left), 0);
        //计算右边分支最大值，右边分支如果为负数还不如不选择
        int rightMax = Math.max(dfs(root.right), 0);
        //left->root->right 作为路径与已经计算过历史最大值做比较
        int priceNewpath = root.val + leftMax + rightMax;

        // 更新答案
        maxSum = Math.max(maxSum, priceNewpath);

        // 返回节点的最大贡献值
        return root.val + Math.max(leftMax, rightMax);
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
