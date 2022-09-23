package 算法.codetop.c链表.反转;

import 算法.数据结构.ListNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/21 09:20
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[2,1,4,3,5]
 */
public class K个一组反转链表 {

    public static ListNode test(ListNode head, int k) {
        ListNode hair = new ListNode(0);
        ListNode pre  = hair;
        ListNode cur  = head;
        ListNode next = null;
        hair.next = head;
        int lenth = 0;
        while (head != null) {
            lenth++;
            head = head.next;
        }
        for (int i = 0; i < lenth / k; i++) {
            for (int j = 0; j < k - 1; j++) {
                next      = cur.next;
                cur.next  = next.next;
                next.next = pre.next;
                pre.next  = next;
            }
            pre = cur;
            cur = pre.next;
        }
        return hair.next;
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
            System.out.println(result.getVal());
            result = result.next;
        }
    }

}
