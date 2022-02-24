package codetop.c链表;

import 数据结构.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/21 22:56
 */
public class 环形链表 {

    /**
     * 借助 set.
     */
    public static boolean test(ListNode head) {
        Set<ListNode> check = new HashSet<>();
        while (head != null) {
            if (check.contains(head)) {
                return true;
            } else {
                check.add(head);
                head = head.next;
            }
        }
        return false;
    }

    /**
     * 快慢指针.
     */
    public static boolean test2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        ListNode l6 = new ListNode(6);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        l6.next = l2;
        System.out.println(test(l1));
        System.out.println(test2(l1));
    }
}
