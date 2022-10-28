package code.codetop.c链表.合并;

import code.数据结构.ListNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/23 09:42
 */
public class 合并K个排序链表 {

    /**
     * 先搞定合并两个
     */
    public static ListNode merge2(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode cur       = dummyHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1       = l1.next;
            } else {
                cur.next = l2;
                l2       = l2.next;
            }
            cur = cur.next;
        }
        if (l1 == null) {
            cur.next = l2;
        } else {
            cur.next = l1;
        }
        return dummyHead.next;
    }

    public static ListNode test(ListNode[] arr) {
        ListNode result = new ListNode(0);
        for (ListNode listNode : arr) {
            result = merge2(listNode, result);
        }
        return result.next;
    }


    public static ListNode fenzhi() {
        return null;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(5);
        l1.next = l2;
        l2.next = l3;
        ListNode l4 = new ListNode(1);
        ListNode l5 = new ListNode(3);
        ListNode l6 = new ListNode(4);
        l4.next = l5;
        l5.next = l6;
        ListNode l7 = new ListNode(2);
        ListNode l8 = new ListNode(6);
        l7.next = l8;
        ListNode[] arr    = new ListNode[]{l1, l4, l7};
        ListNode   result = test(arr);
        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }
    }
}
