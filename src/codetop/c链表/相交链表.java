package codetop.c链表;


import 数据结构.ListNode;

/**
 * @Description 编写一个程序，找到两个单链表相交的起始节点。
 * @LeetCodeURL https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 * @Author spli
 * @Date 2021/5/28 10:44 上午
 * ----------------------------------------
 * 输入:
 *       a1 -> a2 -> c1 -> c2 -> c3
 * b1 -> b2 -> b3 -> c1 -> c2 -> c3
 * 输出: c1 -> c2 -> c3
 * 原因: 在节点 c1 相交
 * ----------------------------------------
 * 思路:
 * a1 -> a2 -> c1 -> c2 -> c3 -> b1 -> b2 -> b3 -> c1 -> c2 -> c3
 * b1 -> b2 -> b3 -> c1 -> c2 -> c3 -> a1 -> a2 -> c1 -> c2 -> c3
 */
public class 相交链表 {

    public static ListNode test(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != pB) {
            if (pA == null) {
                pA = headB;
            } else {
                pA = pA.next;
            }
            if (pB == null) {
                pB = headA;
            } else {
                pB = pB.next;
            }
        }
        return pA;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        ListNode l6 = new ListNode(6);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        ListNode l7 = new ListNode(2);
        ListNode l8 = new ListNode(3);
        l7.next = l8;
        l8.next = l4;
        ListNode result = test(l1, l7);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }

    }
}
