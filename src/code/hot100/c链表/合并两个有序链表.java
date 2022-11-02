package code.hot100.c链表;

import code.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/merge-two-sorted-lists/
 * @Author lishoupeng
 * @Date 2022/9/19 09:14
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 */
public class 合并两个有序链表 {

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if (l1 == null) cur.next = l2;
        if (l2 == null) cur.next = l1;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode3;
        listNode3.next = listNode5;
        ListNode listNode2 = new ListNode(2);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode6 = new ListNode(6);
        ListNode listNode7 = new ListNode(7);
        listNode2.next = listNode4;
        listNode4.next = listNode6;
        listNode6.next = listNode7;
        ListNode listNode = mergeTwoLists(listNode1, listNode2);
        System.out.println(listNode);
    }

}
