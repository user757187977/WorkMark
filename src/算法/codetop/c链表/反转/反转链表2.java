package 算法.codetop.c链表.反转;

import 算法.数据结构.ListNode;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/9/1 09:20
 */
public class 反转链表2 {

    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        ListNode listNode = ListNode.arr2List(arr);
        reverseList(listNode);
    }
}
