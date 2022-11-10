package code.hot100.c链表;

import code.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/add-two-numbers/
 * @Author lishoupeng
 * @Date 2022/9/19 09:16
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 */
public class 两数相加 {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode prev = new ListNode(0);
        int carry = 0;
        ListNode cur = prev;
        while (l1 != null || l2 != null) {
            int x = l1 != null ? l1.val : 0;
            int y = l2 != null ? l2.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode(sum);
            cur = cur.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        if (carry == 1) cur.next = new ListNode(carry);
        return prev.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(4);
        ListNode listNode3 = new ListNode(3);
        listNode1.next = listNode2;
        listNode2.next = listNode3;

        ListNode listNode4 = new ListNode(5);
        ListNode listNode5 = new ListNode(6);
        ListNode listNode6 = new ListNode(4);
        listNode4.next = listNode5;
        listNode5.next = listNode6;

        System.out.println(addTwoNumbers(listNode1, listNode4));


    }
}
