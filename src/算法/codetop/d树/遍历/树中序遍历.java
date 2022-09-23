package 算法.codetop.d树.遍历;

import 算法.数据结构.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author spli
 * @Date 2022/1/26 09:23
 */
public class 树中序遍历 {

    public static List<Integer> test(TreeNode treeNode) {
        List<Integer> res = new ArrayList<>();
        a(treeNode, res);
        return res;
    }

    public static void a(TreeNode treeNode, List<Integer> res) {
        if (treeNode == null) {
            return;
        }
        a(treeNode.left, res);
        res.add(treeNode.val);
        a(treeNode.right, res);
    }

    public static void main(String[] args) {
        TreeNode treeNode  = new TreeNode(1);
        TreeNode treeNode1 = new TreeNode(2);
        TreeNode treeNode2 = new TreeNode(3);
        TreeNode treeNode3 = new TreeNode(15);
        TreeNode treeNode4 = new TreeNode(7);
        treeNode.setLeft(treeNode1);
        treeNode.setRight(treeNode2);
//        treeNode2.setLeft(treeNode3);
//        treeNode2.setRight(treeNode4);
        List<Integer> result = test(treeNode);
        result.forEach(System.out::println);
    }

}
