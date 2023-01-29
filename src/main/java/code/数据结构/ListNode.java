package code.数据结构;

/**
 * @Description
 * @Author spli
 * @Date 2021/5/18 10:30 上午
 */
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public static ListNode arr2List(int[] arr) {
        ListNode head = new ListNode(arr[0]);
        ListNode pre = head;
        for (int i = 1; i < arr.length; i++) {
            ListNode cur = new ListNode(arr[i]);
            pre.next = cur;
            pre = cur;
        }
        return head;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.val);
        ListNode listNode = this;
        while (listNode.next != null) {
            stringBuilder.append(listNode.next.val);
            listNode = listNode.next;
        }
        return stringBuilder.toString();
    }
}
