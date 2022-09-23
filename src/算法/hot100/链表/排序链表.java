package 算法.hot100.链表;

import 算法.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/sort-list/
 * @Author lishoupeng
 * @Date 2022/9/15 09:25
 * 输入：head = [4,2,1,3]
 * 输出：[1,2,3,4]
 */
public class 排序链表 {

//    public static ListNode sortList(ListNode head) {
//
//    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(4);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(1);
        ListNode listNode4 = new ListNode(3);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
//        System.out.println(sortList(listNode1));
    }
}
