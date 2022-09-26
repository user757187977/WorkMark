package 算法.hot100.链表;

import 算法.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
 * @Author lishoupeng
 * @Date 2022/9/16 08:28
 * 输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 * 解释删除倒数第二个, 也就是 4, [1,2,3,4,5] -> [1,2,3,5]
 */
public class 删除倒数第N个元素 {

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        //定义一个伪节点，用于返回结果
        ListNode dumpy = new ListNode(0);
        dumpy.next = head;
        //定义一个快指针,指向伪节点，用于遍历链表
        ListNode fast = dumpy;
        //定一个慢指针，
        ListNode tail = dumpy;
        //让快指针先移动 n 步
        while(n >0){
            fast = fast.next;
            n--;
        }

        //只要快指针不指向空，就继续循环
        while(fast.next !=null){
            //让快慢指针同时移动
            tail = tail.next;
            fast = fast.next;
        }

        //这时慢指针移动到的位置就是，要删除节点的前一个节点
        //所以只要删除当前节点的下一个节点
        tail.next = tail.next.next;


        //返回为指针指向的头节点
        return dumpy.next;

    }

    public static void main(String[] args) {

    }
}
