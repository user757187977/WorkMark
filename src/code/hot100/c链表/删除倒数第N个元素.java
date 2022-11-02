package code.hot100.c链表;

import code.数据结构.ListNode;

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
        ListNode virtual = new ListNode(0);
        virtual.next = head;
        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < n; i++) fast = fast.next;
        while (fast.next != null) {
            slow = slow.next; fast = fast.next;
        }
        slow.next = slow.next.next;
        return virtual.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        System.out.println(removeNthFromEnd(listNode1, 2));
    }
}
