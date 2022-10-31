package code.hot100.c链表;

import code.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/intersection-of-two-linked-lists/
 * @Author lishoupeng
 * @Date 2022/9/16 08:29
 */
public class 相交链表 {

    /**
     * 思路: 我走过你走过的路
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

    public static void main(String[] args) {
        ListNode a1 = new ListNode(1);
        ListNode a2 = new ListNode(2);
        ListNode b1 = new ListNode(1);
        ListNode b2 = new ListNode(2);
        ListNode b3 = new ListNode(3);
        ListNode c1 = new ListNode(1);
        ListNode c2 = new ListNode(2);
        ListNode c3 = new ListNode(3);
        c1.next = c2;
        c2.next = c3;
        a1.next = a2;
        a2.next = c1;
        b1.next = b2;
        b2.next = b3;
        b3.next = c1;
        System.out.println(getIntersectionNode(a1, b1));
    }
}
