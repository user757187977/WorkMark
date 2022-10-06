#核心问题: Redis 为什么快?
`KV 内存 单线程? 别忘了数据结构`
`单机 10w QPS`
## 基于内存
![img.png](img.png)
## 数据结构
`5 种数据类型, String List Set Hash SortedSet`
不同的数据类型都有多种数据结构的支持, 就是为了快. 注意: 数据类型与数据结构不一样!!! 别混了.
![img_2.png](img_2.png)

1. SDS 简单动态字符串
   1. ![img_1.png](img_1.png)
   2. 除了字符串本身, 还保存了额外信息, 比如 len, free
   3. 空间预分配, SDS 被修改后, 除了分配必须空间, 还会分配额外空间
   4. 惰性空间释放, 对 SDS 缩短, 并不会回收多余的内存, 而是使用 free 来保存这些空间不释放, 之后如果 append 则直接使用 free 中的空间
2. zipList
   1. 是 list hash sortedSet 类型的底层实现结构之一.
   2. 数据量较小, 或者单条数据长度较小 时使用.
3. quickList
   1. ![img_3.png](img_3.png)
   2. 是 zipList + linkedList 的合体
4. skipList 跳表
   1. ![img_4.png](img_4.png)
   2. 有序 层级的数据结构, 增加多层级索引
## 单线程模型
1. 单线程**仅仅**是指 KV 读写的过程是单线程的.
2. 越是读写快(利用内存)的场景, I/O 越快, 多线程模型越是不利.
##I/O 多路复用
epoll 模型是基于事件驱动, 利用多路复用的特性, 减少在等待 I/O 处理时的时间.
##Redis 的 KV 模型
![img_5.png](img_5.png)
##持久化
`RDB AOF`
1. RDB是快照的形式，完成数据的备份据保存到*.rdb文件中，这个文件是个二进制文件。
2. AOF是把所有对Redis的修改的命令都存到一个文件里，当持久化的时候读取的时候这个文件。
