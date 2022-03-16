package codetop.d树.遍历;

import 数据结构.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Description
 * @Author spli
 * @Date 2022/3/1 09:17
 * 先从左往右, 再从右往左进行下一级遍历
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：[[3],[20,9],[15,7]]
 */
public class 树层序锯齿遍历 {

    public static List<List<Integer>> test(TreeNode root) {
        List<List<Integer>> res   = new ArrayList<>();
        Queue<TreeNode>     queue = new LinkedList<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty()) {
            int           size    = queue.size();
            List<Integer> resTemp = new ArrayList<>();
            while (size > 0) {
                TreeNode treeNode = queue.poll();
                if (treeNode != null) {
                    resTemp.add(treeNode.val);
                    if (null != treeNode.left) {
                        queue.add(treeNode.left);
                    }
                    if (null != treeNode.right) {
                        queue.add(treeNode.right);
                    }
                }
                size--;
            }
            if (i % 2 == 0) {
                Collections.reverse(resTemp);
            }
            res.add(resTemp);
            i++;
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
