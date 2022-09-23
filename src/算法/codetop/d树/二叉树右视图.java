package 算法.codetop.d树;

import 算法.数据结构.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description
 * @Author spli
 * @Date 2022/3/3 09:18
 * https://leetcode-cn.com/problems/binary-tree-right-side-view/
 * 输入: [1,2,3,null,5,null,4]
 * 输出: [1,3,4]
 * 解释: 层序遍历的最后一个节点就是右视图
 */
public class 二叉树右视图 {

    public static List<Integer> test(TreeNode root) {
        List<Integer>   res   = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode temp = queue.poll();
                if (temp.left != null) {
                    queue.add(temp.left);
                }
                if (temp.right != null) {
                    queue.add(temp.right);
                }
                if (i == size - 1) {
                    res.add(temp.val);
                }
            }
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
        List<Integer> result = test(treeNode);
        result.forEach(System.out::println);
    }
}
