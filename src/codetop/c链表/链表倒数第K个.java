package codetop.c链表;

import 数据结构.ListNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/24 09:10
 * 输入: 1 -> 2 -> 3 -> 4 -> 5, 2
 * 输出: 4 -> 5
 * 解释: 原链表的倒数第二个 就是 4 -> 5
 */
public class 链表倒数第K个 {

    public static ListNode test(ListNode head, int k) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && k > 0) {
            fast = fast.next;
            k--;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
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
        ListNode result = test(listNode1, 2);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }

}
