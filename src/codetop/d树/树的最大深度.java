package codetop.d树;

import 数据结构.TreeNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/3/3 09:53
 */
public class 树的最大深度 {

    public static int test(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return Math.max(test(root.left), test(root.right)) + 1;
        }
    }

}
