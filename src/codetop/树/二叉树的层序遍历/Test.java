package codetop.树.二叉树的层序遍历;

import 数据结构.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 输入: root=[3,9,20,null,null,15,7]
// 输出: [[3],[9,20],[15,7]]
public class Test {

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int curLevelSize = queue.size();
            for(int i  = 0 ;  i <= curLevelSize;i++){
                TreeNode poll = queue.poll();
                level.add(poll.getVal());
                if(poll.getLeft() != null){
                    queue.offer(poll.getLeft());
                }
                if(poll.getRight() != null){
                    queue.offer(poll.getRight());
                }
            }
            result.add(level);
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(3);

        Test test = new Test();
    }

}
