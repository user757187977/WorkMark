package code.hot100.f设计;

import java.util.Stack;

/**
 * @Description 栈实现队列, 入队 出队
 * 栈: 后进先出
 * 队列: 先进先出
 * 入的时候放在栈的最底下  OR 出的时候从最底下的出
 * @Author lishoupeng
 * @Date 2022/11/1 10:46
 */
public class 栈实现队列 {
    public static Stack<Integer> stack = new Stack<>();

    // 出队列
    public static Integer pop() {
        Integer asw = null;
        int size = stack.size();
        // a b c
        // c b a
        // b c
        Stack<Integer> stackTemp = new Stack<>();
        Stack<Integer> stackTemp2 = new Stack<>();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                asw = stack.pop();
            } else {
                stackTemp.push(stack.pop());
            }
        }
        for (int i = 0; i < stackTemp.size(); i++) {
            stackTemp2.push(stackTemp.pop());
        }
        stack = stackTemp2;
        return asw;
    }

    // 入队列
    public static void push(Integer i) {
        stack.push(i);
    }
}
