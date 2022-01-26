package codetop.链表.相交链表;


import 数据结构.ListNode;

/**
 * @Description 编写一个程序，找到两个单链表相交的起始节点。
 * @LeetCodeURL https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 * @Author spli
 * @Date 2021/5/28 10:44 上午
 * 输入：
 * a1 -> a2 -> c1 -> c2 -> c3
 * b1 -> b2 -> b3 -> c1 -> c2 -> c3
 * 在节点 c1 相交
 */
public class 相交链表 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 特判
        if (headA == null || headB == null) {
            return null;
        }

        ListNode head1 = headA;
        ListNode head2 = headB;

        while (head1 != head2) {
            if (head1 != null) {
                head1 = head1.next;
            } else {
                head1 = headB;
            }

            if (head2 != null) {
                head2 = head2.next;
            } else {
                head2 = headA;
            }
        }
        return head1;
    }


    public static void main(String[] args) {
        相交链表 t = new 相交链表();
        ListNode listNode1 = new ListNode(1);
        listNode1.next = new ListNode(2);
        listNode1.next.next = new ListNode(3);
        listNode1.next.next.next = new ListNode(4);
        listNode1.next.next.next.next = new ListNode(5);

        ListNode listNode2 = new ListNode(10);
        listNode2.next = new ListNode(11);
        listNode2.next.next = new ListNode(12);
        listNode2.next.next.next = new ListNode(3);
        listNode2.next.next.next.next = new ListNode(4);
        listNode2.next.next.next.next.next = new ListNode(5);

        ListNode listNode3 = t.getIntersectionNode(listNode1, listNode2);
        System.out.println(listNode3);
    }
}
