package code.hot100.链表;

import code.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/linked-list-cycle-ii/
 * 输出相遇点!!!
 * @Author lishoupeng
 * @Date 2022/9/19 09:15
 */
public class 环形链表2 {

    /**
     * 1.f=2s （快指针每次2步，路程刚好2倍）
     * 2.f = s + nb (相遇时，刚好多走了n圈）
     * 推出：s = nb
     * 从head结点走到入环点需要走 ： a + nb， 而slow已经走了nb，那么slow再走a步就是入环点了。
     * 如何知道slow刚好走了a步？ 从head开始，和slow指针一起走，相遇时刚好就是a步
     */
    public static ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode2;
        System.out.println(detectCycle(listNode1));
    }
}
