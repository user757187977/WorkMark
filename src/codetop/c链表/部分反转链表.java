package codetop.c链表;

import 数据结构.ListNode;

/**
 * @Description
 * @Author spli
 * @Date 2022/2/22 22:17
 * 输入：head = [1,2,3,4,5], left = 2, right = 4
 * 输出：[1,4,3,2,5]
 */
public class 部分反转链表 {

    public static ListNode test(ListNode head, int left, int right) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        // 初始化指针
        ListNode m = dummyHead;
        ListNode n = dummyHead.next;

        // 将指针移到相应的位置
        for(int step = 0; step < left - 1; step++) {
            m = m.next;
            n = n.next;
        }

        // 头插法插入节点
        for (int i = 0; i < right - left; i++) {
            ListNode removed = n.next;
            n.next = n.next.next;
            removed.next = m.next;
            m.next = removed;
        }

        return dummyHead.next;

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
        ListNode result = test(listNode1, 2, 4);
        while (result != null) {
            System.out.println(result.getVal());
            result = result.next;
        }
    }

}
