package code.codetop.c链表;

import code.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/linked-list-cycle-ii/solution/linked-list-cycle-ii-kuai-man-zhi-zhen-shuang-zhi-/
 * @Author lishoupeng
 * @Date 2022/9/13 09:08
 * 要求返回环形链表的环形开始点
 */
public class 环形链表升级 {

    public static ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = slow.next.next;
        while (true) {
            if (fast == null || fast.next == null) return null;
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        fast = head;
        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    public static void main(String[] args) {
        ListNode listNode0 = new ListNode(0);
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        listNode0.next = listNode1;
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode1;
        System.out.println(detectCycle(listNode0));

    }
}
