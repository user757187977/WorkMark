package codetop.c链表;

import 数据结构.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @LeetCodeURL https://leetcode-cn.com/problems/linked-list-cycle/
 * @Author spli
 * @Date 2021/6/8 10:33 上午
 */
public class 环形链表 {

    /**
     * 可以借助 set 的情况
     *
     * @param head
     * @return
     */
    public static boolean test(ListNode head) {
        boolean       result = false;
        Set<ListNode> set    = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return result;
    }

    /**
     * 不让用 set 的情况, 快慢指针
     * 慢指针从头开始每次移动两个, 慢指针从第二个开始每次移动一个,
     *
     * @param head
     * @return
     */
    public static boolean test2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(3);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(0);
        ListNode listNode4 = new ListNode(-4);
//        listNode4.setNext(listNode2);
        listNode3.setNext(listNode4);
        listNode2.setNext(listNode3);
        listNode1.setNext(listNode2);
        System.out.println(test(listNode1));

        System.out.println(test2(listNode1));
    }
}
