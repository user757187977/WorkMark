package code.hot100.d树;

import code.数据结构.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description https://leetcode.cn/problems/sum-root-to-leaf-numbers/
 * 根到叶所有路径
 * @Author lishoupeng
 * @Date 2022/11/13 21:41
 */
public class 根到叶所有路径 {

    static List<List<Integer>> asw = new ArrayList<>();

    public static List<List<Integer>> getAllPath(TreeNode root) {
        List<Integer> aswTemp = new ArrayList<>();
        dfs(root, aswTemp);
        return asw;
    }

    public static void dfs(TreeNode node, List<Integer> aswTemp) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            aswTemp.add(node.val);
            asw.add(new ArrayList<>(aswTemp));
            aswTemp.remove(aswTemp.size() - 1);
            return;
        }
        aswTemp.add(node.val);
        dfs(node.left, aswTemp);
        dfs(node.right, aswTemp);
        aswTemp.remove(aswTemp.size() - 1);
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
        List<List<Integer>> asw = getAllPath(treeNode1);
        System.out.println(asw);
    }
}
