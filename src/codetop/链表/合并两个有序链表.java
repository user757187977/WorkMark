package codetop.链表;


import 数据结构.ListNode;

/**
 * @Description 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * @LeetCodeURL https://leetcode-cn.com/problems/merge-two-sorted-lists/
 * @Author spli
 * @Date 2021/6/1 6:50 下午
 * 输入：
 * 1 -> 2 -> 4
 * 1 -> 3 -> 4
 * 输出：
 * 1 -> 1 -> 2 -> 3 -> 4 -> 4
 */
public class 合并两个有序链表 {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val < l2.val) {
            System.out.println("l1的值小，摘出l1");
            System.out.println("l1.val = " + l1.val);
            l1.next = mergeTwoLists(l1.next, l2);
            System.out.println("l1.val = " + l1.val);
            return l1;
        } else {
            System.out.println("l2的值小，摘出l2");
            System.out.println("l2.val = " + l2.val);
            l2.next = mergeTwoLists(l1, l2.next);
            System.out.println("l2.val = " + l2.val);
            return l2;
        }

    }

    public static void main(String[] args) {
        合并两个有序链表 t = new 合并两个有序链表();
        ListNode listNode1 = new ListNode(1);
        listNode1.next = new ListNode(2);
        listNode1.next.next = new ListNode(4);

        ListNode listNode2 = new ListNode(1);
        listNode2.next = new ListNode(3);
        listNode2.next.next = new ListNode(4);

        ListNode result = t.mergeTwoLists(listNode1, listNode2);
        System.out.println(result);
    }

}
