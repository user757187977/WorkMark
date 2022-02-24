package codetop.c链表;

import 数据结构.ListNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/21 22:25
 */
public class 合并两个有序链表 {

    public static ListNode test(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode cur    = result;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                cur      = cur.next;
                l1       = l1.next;
            } else {
                cur.next = l2;
                cur      = cur.next;
                l2       = l2.next;
            }
        }
        if (l1 == null) {
            cur.next = l2;
        } else {
            cur.next = l1;
        }
        return result.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        listNode1.next = listNode2;
        listNode2.next = new ListNode(4);
        ListNode listNode4 = new ListNode(1);
        ListNode listNode5 = new ListNode(3);
        listNode4.next = listNode5;
        listNode5.next = new ListNode(4);
        ListNode result = test(listNode1, listNode4);
        while (result != null) {
            System.out.println(result.getVal());
            result = result.next;
        }
    }
}
