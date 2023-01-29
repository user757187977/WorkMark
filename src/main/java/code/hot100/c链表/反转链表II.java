package code.hot100.c链表;

import code.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/reverse-linked-list-ii/
 * @Author lishoupeng
 * @Date 2023/1/29 09:48
 */
public class 反转链表II {

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        //1. 定义虚拟头
        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;

        //2. 定义 4 个指针，待反转部分的：左、右、左前、右后
        ListNode reverseLeftPre = dummyNode;
        for (int i = 0; i < left - 1; i++) reverseLeftPre = reverseLeftPre.next;
        ListNode reverseRight = dummyNode;
        for (int i = 0; i < right; i++) reverseRight = reverseRight.next;
        ListNode reverseLeft = reverseLeftPre.next;
        ListNode reverseRightNext = reverseRight.next;

        //3. 断开
        reverseLeftPre.next = null;
        reverseRight.next = null;
        //4. 反转
        reverseLinkedList(reverseLeft);
        //5. 拼接回来
        reverseLeftPre.next = reverseRight;
        reverseLeft.next = reverseRightNext;
        return dummyNode.next;
    }

    private static void reverseLinkedList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
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
        ListNode result = reverseBetween(listNode1, 2, 4);
        while (result != null) {
            System.out.println(result.getVal());
            result = result.next;
        }
    }

}
