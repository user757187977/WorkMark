package codetop.c链表;

import 数据结构.ListNode;

/**
 * @Description 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * @LeetCodeURL https://leetcode-cn.com/problems/reverse-nodes-in-k-group/
 * @Demo_in head = [1,2,3,4,5], k = 2
 * @Demo_out [2, 1, 4, 3, 5]
 * @Author spli
 * @Date 2021/5/18 10:19 上午
 */
public class K个一组翻转链表 {

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        ListNode prev = dummy;
        ListNode curr = head;
        ListNode next;
        dummy.next = head;
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        for (int i = 0; i < length / k; i++) {
            for (int j = 0; j < k - 1; j++) {
                next = curr.next;
                curr.next = next.next;
                next.next = prev.next;
                prev.next = next;
            }
            prev = curr;
            curr = prev.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        K个一组翻转链表 t = new K个一组翻转链表();
        listNode = t.reverseKGroup(listNode, 2);
        System.out.println("11");
    }
}
