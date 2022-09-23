package 算法.codetop.c链表;

import 算法.数据结构.ListNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/23 21:56
 * 输入: L0 → L1 → … → Ln - 1 → Ln
 * 输出: L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
 * 输入: 1 -> 2 -> 3 -> 4 -> 5
 * 输出: 1 -> 5 -> 2 -> 4 -> 3
 */
public class 重排链表 {

    public static ListNode test(ListNode head) {
        ListNode result = null;
        if (head == null) {
            return null;
        }
        ListNode mid = middleNode(head);
        ListNode l1  = head;
        ListNode l2  = mid.next;
        mid.next = null;
        l2       = reverseList(l2);
        mergeList(l1, l2);
        return result;
    }

    public static ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev      = curr;
            curr      = nextTemp;
        }
        return prev;
    }

    public static void mergeList(ListNode l1, ListNode l2) {
        ListNode l1Tmp;
        ListNode l2Tmp;
        while (l1 != null && l2 != null) {
            l1Tmp = l1.next;
            l2Tmp = l2.next;

            l1.next = l2;
            l1      = l1Tmp;

            l2.next = l1;
            l2      = l2Tmp;
        }
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
            System.out.println(result.val);
            result = result.next;
        }
    }
}
