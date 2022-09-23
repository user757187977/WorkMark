package 算法.codetop.c链表.合并;

import 算法.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/merge-two-sorted-lists/
 * @Author spli
 * @Date 2022/2/21 22:25
 * 两个升序链表合成一个升序链表
 */
public class 合并两个有序链表 {

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        //创建虚拟头节点
        ListNode head = new ListNode(-1);
        ListNode p = head;
        //双指针
        ListNode l1 = list1;
        ListNode l2 = list2;
        //只要一个链表没有遍历完，就继续遍历
        while (l1 != null && l2 != null) {
            //插值只有两种情况，
            if (l1.val <= l2.val) {//插入第一个链表的值
                p.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {//插入第二个链表的值
                p.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            p = p.next;
        }
        //如果有链表不为空，直接插在末尾
        if (l1 != null) p.next = l1;
        if (l2 != null) p.next = l2;
        //返回虚拟头节点后面的节点
        return head.next;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        listNode1.next = listNode2;
        listNode2.next = new ListNode(4);
        ListNode listNode4 = new ListNode(1);
        ListNode listNode5 = new ListNode(3);
        listNode4.next = listNode5;
        listNode5.next = new ListNode(4);
        ListNode result = mergeTwoLists(listNode1, listNode4);
        while (result != null) {
            System.out.println(result.getVal());
            result = result.next;
        }
    }
}
