package code.codetop.d树.数组转树;

import code.数据结构.TreeNode;

import java.util.Arrays;

/**
 * @Description
 * @Author spli
 * @Date 2022/3/14 09:53
 * 输入: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * 输出: [3,9,20,null,null,15,7]
 * 对于任意一颗树而言，
 * 前序遍历的形式总是: [ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
 * 中序遍历的形式总是: [ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]
 * 然后递归 [左子树的前序遍历结果] [左子树的中序遍历结果] 又分别是 前序 和 中序的遍历结果
 */
public class 前序中序转树 {

    public static TreeNode buildTree(int[] qianxu, int[] zhongxu) {
        if (qianxu.length == 0 || zhongxu.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(qianxu[0]);
        for (int i = 0; i < qianxu.length; i++) {
            if (qianxu[0] == zhongxu[i]) {
                root.left  = buildTree(
                        Arrays.copyOfRange(qianxu, 1, i + 1),
                        Arrays.copyOfRange(zhongxu, 0, i)
                );
                root.right = buildTree(
                        Arrays.copyOfRange(qianxu, i + 1, qianxu.length),
                        Arrays.copyOfRange(zhongxu, i + 1, zhongxu.length)
                );
                break;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode treeNode = buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        System.out.println(treeNode);
    }

}
