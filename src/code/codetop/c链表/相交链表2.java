package code.codetop.c链表;

import code.数据结构.ListNode;

/**
 * @Description
 * @Author lishoupeng
 * @Date 2022/9/5 09:21
 */
public class 相交链表2 {
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode asw = null;
        if (headA == null || headB == null) {
            return null;
        }
        ListNode l1 = headA;
        ListNode l2 = headB;
        while (l1.val != l2.val) {
            if (l1.next != null) {
                l1 = l1.next;
            } else {
                l1 = headB;
            }
            if (l2.next != null) {
                l2 = l2.next;
            } else {
                l2 = headA;
            }
        }
        return l1;
    }

    public static void main(String[] args) {
        ListNode listNode1 = ListNode.arr2List(new int[]{1, 2, 7, 8});
        ListNode listNode2 = ListNode.arr2List(new int[]{3, 4, 5, 6, 7, 8});
        ListNode listNode3 = getIntersectionNode(listNode1, listNode2);
        System.out.println(listNode3);
    }
}
