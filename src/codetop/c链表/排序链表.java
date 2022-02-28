package codetop.c链表;

import 数据结构.ListNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/25 09:25
 */
public class 排序链表 {

    public static ListNode test(ListNode head) {
        return null;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(4);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(1);
        ListNode l4 = new ListNode(3);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        ListNode result = test(l1);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }
}
