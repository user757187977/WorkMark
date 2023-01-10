package code.hot100.d树;

import code.数据结构.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 * @Author lishoupeng
 * @Date 2022/11/16 08:44
 */
public class 前中后序转树 {

    private static final Map<Integer, Integer> inorderIndexMap = new HashMap<>();

    public static TreeNode preInBuildTree(int[] preorder, int[] inorder) {
        int n = inorder.length;
        for (int i = 0; i < n; i++) inorderIndexMap.put(inorder[i], i);
        return preInBuildTree(preorder, 0, n - 1, 0, n - 1);
    }

    public static TreeNode preInBuildTree(int[] preorder, int preorderLeft, int preorderRight, int inorderLeft, int inorderRight) {
        if (preorderLeft > preorderRight) return null;
        int rootVal = preorder[preorderLeft];
        TreeNode root = new TreeNode(rootVal);
        int rootIndex = inorderIndexMap.get(rootVal);
        int sub = rootIndex - inorderLeft;
        root.left = preInBuildTree(preorder, preorderLeft + 1, preorderLeft + sub, inorderLeft, inorderRight - 1);
        root.right = preInBuildTree(preorder, preorderLeft + 1 + sub, preorderRight, rootIndex + 1, inorderRight);
        return root;
    }


    private static final Map<Integer, Integer> inorderIndexMap2 = new HashMap<>();

    public static TreeNode postInBuildTree(int[] postorder, int[] inorder) {
        int n = inorder.length;
        for (int i = 0; i < n; ++i) inorderIndexMap2.put(inorder[i], i);
        return postInBuildTree(postorder, 0, n - 1, 0, n - 1);
    }

    public static TreeNode postInBuildTree(int[] postorder, int postorderLeft, int postorderRight, int inorderLeft, int inorderRight) {
        if (inorderLeft > inorderRight) return null;
        int rootVal = postorder[postorderRight];
        TreeNode root = new TreeNode(rootVal);
        int rootIndex = inorderIndexMap2.get(rootVal);
        int sub = rootIndex - inorderLeft;
        root.left = postInBuildTree(postorder, postorderLeft, postorderLeft + sub - 1, inorderLeft, rootIndex - 1);
        root.right = postInBuildTree(postorder, postorderLeft + sub, postorderRight - 1, rootIndex + 1, inorderRight);
        return root;
    }

    public static void main(String[] args) {
        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        TreeNode treeNode1 = preInBuildTree(preorder, inorder);
        System.out.println(treeNode1);
        int[] postorder = new int[]{9, 15, 7, 20, 3};
        TreeNode treeNode2 = postInBuildTree(postorder, inorder);
        System.out.println(treeNode2);
    }
}
