package codetop.链表.反转链表;


import 数据结构.ListNode;

/**
 * @Description 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * @LeetCodeURL https://leetcode-cn.com/problems/reverse-linked-list/
 * @Author spli
 * @Date 2021/5/11 10:24 上午
 */
public class 反转链表 {

    /**
     * 迭代
     * 遍历链表时，把当前节点的 next 指针指向前一个节点。
     * 但是，当前节点没有指向前节点的指针，所以要先事先存储一个当前节点的前指针，以便让当前节点的 next 指针指向前节点
     * 同样的，由于当前节点的 next 指向前节点，这个链表就断了，所以还要存储当前节点的下一个节点，然后下一个节点作为新的头，重新开始
     *
     * @param head 头
     * @return ListNode
     */
    public ListNode reverseList1(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        ListNode next;
        while (curr != null) {
            // 做好准备，我的链表即将断开，于是先把 next 单独存起来
            next = curr.next;
            // 现在，链表断了，curr.next = prev 了
            curr.next = prev;
            prev      = curr;
            curr      = next;
        }
        return prev;
    }

    /**
     * 递归
     * 递归稍微复杂一些，但是很锻炼递归思想，关键点在于反向工作。
     *
     * @param head 头
     * @return ListNode
     */
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList2(head.next);
        head.next.next = head;
        head.next      = null;
        return newHead;
    }

    public static void main(String[] args) {
        反转链表     a        = new 反转链表();
        ListNode listNode = new ListNode(1);
        listNode.next                = new ListNode(2);
        listNode.next.next           = new ListNode(3);
        listNode.next.next.next      = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        listNode                     = a.reverseList1(listNode);
        System.out.println(toString(listNode));
        listNode = a.reverseList2(listNode);
        System.out.println(toString(listNode));
    }

    public static String toString(ListNode head) {
        StringBuilder result = new StringBuilder();
        result.append(head.val);
        ListNode curr = head;
        while (curr.next != null) {
            curr = curr.next;
            result.append(curr.val);
        }
        return result.toString();
    }

}
