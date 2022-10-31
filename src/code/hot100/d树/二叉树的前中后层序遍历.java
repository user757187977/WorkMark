package code.hot100.d树;

import code.数据结构.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/9/16 08:27
 */
public class 二叉树的前中后层序遍历 {

    public static List<List<Integer>> 层序(TreeNode root) {
        List<List<Integer>> asw = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int count = queue.size();
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                TreeNode poll = queue.poll();
                if (poll != null) {
                    if (poll.left != null) queue.add(poll.left);
                    if (poll.right != null) queue.add(poll.right);
                    temp.add(poll.val);
                }
            }
            asw.add(temp);
        }
        return asw;
    }

    public static List<Integer> 前序(TreeNode root) {
        List<Integer> asw = new ArrayList<>();
        前序(root, asw);
        return asw;
    }

    public static void 前序(TreeNode root, List<Integer> asw) {
        if (root == null) return;
        asw.add(root.val);
        前序(root.left, asw);
        前序(root.right, asw);
    }

    public static List<Integer> 中序(TreeNode root) {
        List<Integer> asw = new ArrayList<>();
        中序(root, asw);
        return asw;
    }

    public static void 中序(TreeNode root, List<Integer> asw) {
        if (root == null) return;
        中序(root.left, asw);
        asw.add(root.val);
        中序(root.right, asw);
    }

    public static List<Integer> 后序(TreeNode root) {
        List<Integer> asw = new ArrayList<>();
        后序(root, asw);
        return asw;
    }

    public static void 后序(TreeNode root, List<Integer> asw) {
        if (root == null) return;
        后序(root.left, asw);
        后序(root.right, asw);
        asw.add(root.val);
    }

    public static void main(String[] args) {
        TreeNode treeNode0 = new TreeNode(3);
        TreeNode treeNode1 = new TreeNode(9);
        TreeNode treeNode2 = new TreeNode(20);
        TreeNode treeNode3 = new TreeNode(15);
        TreeNode treeNode4 = new TreeNode(7);
        treeNode0.left = treeNode1;
        treeNode0.right = treeNode2;
        treeNode2.left = treeNode3;
        treeNode2.right = treeNode4;
        System.out.println("层序: " + 层序(treeNode0));
        System.out.println("前序: " + 前序(treeNode0));
        System.out.println("中序: " + 中序(treeNode0));
        System.out.println("后序: " + 后序(treeNode0));
    }

}
