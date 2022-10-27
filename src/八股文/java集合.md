### HashMap

1. 数组 + 链表/红黑树, 初始化长度16, 链表长度达到8转换红黑树, 6的时候红黑树转换链表;
    1. 与负载因子一起通过泊松分布计算得到的, 在负载因子默认为0.75的时候, 单个hash槽内元素个数为8的概率小于百万分之一, 基本不可能发生.
2. hash碰撞的方法: 拉链法, 发生冲突的时候, 放到链表的尾部;
3. put方法分析: put一个Object, 你要把他放到数组里, 那么一定要是数组的index, 这是个int值, 并且这个int值要在table的长度范围内, 还有尽可能的分散, 那么这个过程:
    1. int h = key.hashCode(); //取hashCode;
    2. h ^ (h >>> 16);//这个就叫做扰动函数; h右移16位, 高位补0, 然后 异或 操作;
        1. 先说右移, 这样可以让h的高位也参与到运算中;
        2. 再说异或, 异或的特点一位发生变化结果一定发生变化;
    3. (n-1) & h;//取模运算, 保证要放入的位置的下标在table范围内, HashMap采用的是位运算, 一个意思
4. 为什么n是2的n次方:
    1. 只有在长度n为2的n次方时候, 比如16, n-1=15才能保证后面的二进制的后几位是1111, 16-1=15: 0000 1111, 最后得到的结果, 二进制最小值一定是0000, 最大值一定是1111, 即十进制:
       0-15, 保证范围;
5. 扩容过程分析: 扩容机制是新长度是原长度的二倍; 遍历老的Hash桶, 这个桶下面有可能是一个链表, 那么就用4个指针: 高低首尾4个指针, 把一个链表拆成两个, 下面这个链表放到当前index+扩容长度对应的hash桶中,
   比如说这个链表现在的index是=5; hash桶长度16, 扩容后是32, 那么这个链表后半部分就在5+16的位置;
6. 不安全体现在哪?
    1. 如何两个线程同时 put 并且 两个 K 发生哈希碰撞, 按道理说这个时候是需要加锁的.
7. HashTable 如何实现线程安全?
    1. synchronized的HashMap; put、get加上synchronized, 自然就线程安全了.
8. ConcurrentHashMap
    1. JDK1.7
        1. 初始化 ConcurrentHashMap 的时候, 会初始化一个 Segment 数组, 容量为 16.
        2. Segment 都继承了 ReentrantLock, 每个 Segment 内部又有一个 table 数组. 而每个 table 数组里的索引数据呢, 又对应着一个 Node 链表.
        3. 说白了就是在原来 HashMap 的 数组+链表 这个结构之外, 又套了一层锁.
        4. 当我们使用 put 方法的时候, 是对我们的 key 进行 hash 拿到一个整型, 然后将整型对 16 取模, 拿到对应的 Segment, 之后调用 Segment 的 put 方法, 然后上锁.
    2. JDK1.8: CAS + Synchronized
    3. 1.8(CAS+Synchronized) 如何取代 1.7(Segment + ReentrantLock)?
        1. 锁细化: 源码中 Synchronized 上锁的对象是 node. 是对 java 语言最底层的存储结构加了锁
        2. 那么问题来了, ReentrantLock 也可以将锁细化成这样, 为什么选择 Synchronized?
            1. 锁已经足够细了, 基本不可能发生
            2. Synchronized 优化也足够好了

### java 常见的集合与数据结构

* List 接口:
    * ArrayList: 动态数组
    * LinkedList: 双向链表
    * Vector: 与 ArrayList 类似, 线程安全
    * Stack: 栈, 先进后出
* Set 接口: 不可重复
    * HashSet: 以 HashCode 实现的无重复数据
    * TreeSet: 基于 TreeMap, 生成的是一个总是排序的 set, 可以自己指定排序实现.
* Map 接口:
    * HashMap: 数组+链表
    * TreeMap: 内部红黑树
    * HashTable: 线程安全
* Queue:
    * 阻塞队列
    * 双端队列