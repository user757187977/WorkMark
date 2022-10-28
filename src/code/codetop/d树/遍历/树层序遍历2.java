package code.codetop.d树.遍历;

import code.数据结构.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description https://leetcode.cn/problems/binary-tree-level-order-traversal/
 * @Author lishoupeng
 * @Date 2022/9/14 08:43
 */
public class 树层序遍历2 {

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> asw = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                level.add(poll.val);
                if (poll.left != null) queue.add(poll.left);
                if (poll.right != null) queue.add(poll.right);
            }
            asw.add(level);
        }
        return asw;
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
        System.out.println(levelOrder(treeNode0));
    }


}
