package codetop.d树.遍历;

import 数据结构.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/9/6 08:28
 */
public class 树中序遍历2 {

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> asw = new ArrayList<>();
        recursion(root, asw);
        return asw;
    }

    public static void recursion(TreeNode root, List<Integer> asw) {
        if (root == null) {
            return;
        }
        recursion(root.left, asw);
        asw.add(root.val);
        recursion(root.right, asw);
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        TreeNode treeNode1 = new TreeNode(2);
        TreeNode treeNode2 = new TreeNode(3);
        treeNode.left = treeNode1;
        treeNode.right = treeNode2;
        // 2 1 3
        System.out.println(inorderTraversal(treeNode));
    }

}
