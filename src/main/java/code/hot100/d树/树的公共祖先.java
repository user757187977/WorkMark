package code.hot100.d树;

import code.数据结构.TreeNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/comments/
 * @Author lishoupeng
 * @Date 2022/11/14 22:16
 */
public class 树的公共祖先 {

    // 方法一
    public static TreeNode dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = dfs(root.left, p, q);
        TreeNode right = dfs(root.right, p, q);
        if (left == null && right == null) return null;
        if (left == null) return right;
        if (right == null) return left;
        return root;
    }

    // 方法二
    static Map<Integer, TreeNode> parent = new HashMap<>(); //K: 当前节点 V: 父节点
    public static void dfs(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.val, root);
            dfs(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.val, root);
            dfs(root.right);
        }
    }

    static Set<Integer> visited = new HashSet<>(); //保存了 p 递归向上的父节点
    public static TreeNode commonGrand(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root);
        while (p != null) {
            visited.add(p.val);
            p = parent.get(p.val);
        }
        while (q != null) {
            if (visited.contains(q.val)) return q;
            q = parent.get(q.val);
        }
        return null;
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
        // 方法一
        System.out.println(dfs(treeNode1, treeNode4, treeNode5).val);
        // 方法二
        System.out.println(commonGrand(treeNode1, treeNode4, treeNode5).val);
    }

}
