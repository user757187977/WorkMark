package 算法.codetop.d树.遍历;

import 算法.数据结构.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description
 * @Author spli
 * @Date 2022/1/26 09:23
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 * 层序遍历明显是一种广度优先遍历
 * 广度优先直接用队列
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[9,20],[15,7]]
 */
public class 树层序遍历 {

    public static List<List<Integer>> test(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res   = new ArrayList<>();
        Queue<TreeNode>     queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int           count = queue.size();
            List<Integer> list  = new ArrayList<>();
            while (count > 0) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                count--;
            }
            res.add(list);
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode treeNode  = new TreeNode(3);
        TreeNode treeNode1 = new TreeNode(9);
        TreeNode treeNode2 = new TreeNode(20);
        TreeNode treeNode3 = new TreeNode(15);
        TreeNode treeNode4 = new TreeNode(7);
        treeNode.setLeft(treeNode1);
        treeNode.setRight(treeNode2);
        treeNode2.setLeft(treeNode3);
        treeNode2.setRight(treeNode4);
        List<List<Integer>> result = test(treeNode);
        result.forEach(ll -> ll.forEach(System.out::println));
    }
}
