package codetop.c链表.合并;

import 数据结构.ListNode;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/9/2 09:24
 */
public class 合并两个有序链表2 {

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        //创建虚拟头节点
        ListNode head = new ListNode(-1);
        ListNode p = head;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                p.next = new ListNode(l2.val);
                l2 = l2.next;
            } else {
                p.next = new ListNode(l1.val);
                l1 = l1.next;
            }
            p = p.next;
        }
        if (l1 == null) {
            p.next = l2;
        }
        if (l2 == null) {
            p.next = l1;
        }
        return head.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = ListNode.arr2List(new int[]{1, 3, 4});
        ListNode listNode2 = ListNode.arr2List(new int[]{1, 2, 3});
        ListNode result = mergeTwoLists(listNode1, listNode2);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }
}
