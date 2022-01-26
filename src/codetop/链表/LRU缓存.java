package codetop.链表;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 写一个 LRU 缓存机制
 * 输入：["LRU缓存", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * 输出：[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * 解释：
 * LRU缓存 a = new LRU缓存(2);
 * a.put(1, 1); // 缓存是 {1=1}
 * a.put(2, 2); // 缓存是 {1=1, 2=2}
 * a.get(1);    // 返回 1
 * a.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
 * a.get(2);    // 返回 -1 (未找到)
 * a.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
 * a.get(1);    // 返回 -1 (未找到)
 * a.get(3);    // 返回 3
 * a.get(4);    // 返回 4
 * @Author spli
 * @LeetCodeURL https://leetcode-cn.com/problems/lru-cache/
 * @Date 2021/5/13 7:51 下午
 */
public class LRU缓存 {

    static class DLinkedNode {
        int         key;
        int         value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode() {
        }

        public DLinkedNode(int _key, int _value) {
            key   = _key;
            value = _value;
        }
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
    private int                       size;
    private int                       capacity;
    private DLinkedNode               head;
    private DLinkedNode               tail;

    /**
     * 以正整数作为容量 capacity 初始化 LRU 缓存
     *
     * @param capacity 容量
     */
    public LRU缓存(int capacity) {
        this.size     = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head      = new DLinkedNode();
        tail      = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1。
     *
     * @param key key
     * @return value
     */
    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        moveToHead(node);
        return node.value;
    }

    /**
     * 如果 K 存在，则变更 V
     * 如果 K 不存在，则插入该组「K-V」
     * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
     *
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 添加至双向链表的头部
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                // 如果超出容量，删除双向链表的尾部节点
                DLinkedNode tail = removeTail();
                // 删除哈希表中对应的项
                cache.remove(tail.key);
                --size;
            }
        } else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }

    private void addToHead(DLinkedNode node) {
        node.prev      = head;
        node.next      = head.next;
        head.next.prev = node;
        head.next      = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }


    public static void main(String[] args) {
        LRU缓存 lRUCache = new LRU缓存(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        lRUCache.get(1);    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        lRUCache.get(2);    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        lRUCache.get(1);    // 返回 -1 (未找到)
        lRUCache.get(3);    // 返回 3
        lRUCache.get(4);    // 返回 4
    }
}
