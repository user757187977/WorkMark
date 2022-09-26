package 算法.hot100.链表;

import 算法.数据结构.ListNode;

/**
 * @Description https://leetcode.cn/problems/add-two-numbers/
 * @Author lishoupeng
 * @Date 2022/9/19 09:16
 */
public class 两数相加 {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode prev = new ListNode(0); //定义一个新联表伪指针，用来指向头指针，返回结果
        int carry = 0; //定义一个进位数的指针，用来存储当两数之和大于10的时候，
        ListNode cur = prev; //定义一个可移动的指针，用来用来指向存储两个数之和的位置指向存储两个数之和的位置
        while (l1 != null || l2 != null) { //当l1 不等于null或l2 不等于空时，就进入循环
            int x = l1 != null ? l1.val : 0; //如果l1 不等于null时，就取他的值，等于null时，就赋值0，保持两个链表具有相同的位数
            int y = l2 != null ? l2.val : 0; //如果l1 不等于null时，就取他的值，等于null时，就赋值0，保持两个链表具有相同的位数
            int sum = x + y + carry; //将两个链表的值，进行相加，并加上进位数
            carry = sum / 10; //计算进位数
            sum = sum % 10; //计算两个数的和，此时排除超过10的请况（大于10，取余数）
            cur.next = new ListNode(sum); //将求和数赋值给新链表的节点，
            cur = cur.next; //将新链表的节点后移
            if (l1 != null) { //当链表l1不等于null的时候，将l1 的节点后移
                l1 = l1.next;
            }
            if (l2 != null) { //当链表l2 不等于null的时候，将l2的节点后移
                l2 = l2.next;
            }
        }
        if (carry == 1) { //两数相加最多小于20，所以的的值最大只能时1
            cur.next = new ListNode(carry);
        }

        return prev.next; //返回链表的头节点

    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(4);
        ListNode listNode3 = new ListNode(3);
        listNode1.next = listNode2;
        listNode2.next = listNode3;

        ListNode listNode4 = new ListNode(5);
        ListNode listNode5 = new ListNode(6);
        ListNode listNode6 = new ListNode(4);
        listNode4.next = listNode5;
        listNode5.next = listNode6;

        System.out.println(addTwoNumbers(listNode1, listNode4));


    }
}
