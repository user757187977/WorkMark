package code.codetop.c链表.反转;

import code.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/reverse-linked-list/
 * @Author spli
 * @Date 2022/2/3 10:26
 */
public class 反转链表 {

    public static ListNode test(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre      = cur;
            cur      = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode4.setNext(listNode5);
        listNode3.setNext(listNode4);
        listNode2.setNext(listNode3);
        listNode1.setNext(listNode2);
        ListNode result = test(listNode1);
        while (result != null) {
            System.out.println(result.getVal());
            result = result.next;
        }
    }
}
