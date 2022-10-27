### synchronized

1. synchronize 是 java 的关键字, 可以用来加锁: 代码块, 方法, 实例
2. 通过反编译可以看到其根本原理就是 monitor enter 和 monitor exit 来控制的, monitor 就是 cpu 的两个指令, monitor 也是通过计数器实现的, 当为 0 的时候执行 monitor
   enter, 不为 0 时候等待, 进入了 +1, 离开 -1.
3. 被加载到 jvm 中的每个实例都有一个对象头, 其中对象头中包含着 32 位的存储单元, 其中就保存了 monitor 信息与锁状态.
4. synchronize 也对应有着优化:
    1.
   偏向锁: 当锁对象第一次被线程A获取时, 对象头中的标志位被修改为可偏向, 是否偏向锁置为1, 锁对象与线程A绑定偏向锁, 当下一次A线程再参与竞争时, 对象偏向被线程A锁住;
   就相当于尝试获取锁的第一句话就是找对象的偏向锁之后和当前线程比较, 成功了把锁给线程A, 失败了, 取消偏向锁；
    2. 轻量级锁: 自旋锁 + CAS; CAS 尝试修改对象头的 markWord 中心的信息.
    3. 那么自旋又是什么意思呢, 就是在许多场景下, 需要加锁执行的代码是执行很快的, 那么我只需要线程死循环一会来获取锁就可以避免阻塞了, JDK6中叫适应性自旋锁, 怎么适应性呢,
       就是死循环的次数是由上一次线程获取锁时自旋的次数.

### wait & notify & notifyAll

`这三个方法都是对: 对象实例的对象头中的 monitor 的操作`

1. wait() 让当前线程阻塞, 并且当前线程必须拥有此对象的 monitor(锁)
2. notify() 唤醒一个正在等待这个对象的 monitor 的线程, 如果有多个线程都在等待这个对象的 monitor, 则只能唤醒其中一个线程;
3. notifyAll() 方法能够唤醒所有正在等待这个对象的 monitor 的线程;

### volatile

`《深入理解JAVA虚拟机》中有如下描述: 加入 volatile 生成的汇编代码会多出一个 lock 前缀指令`

这个 lock 前缀指令我们就称为: **内存屏障**, 内存屏障提供 2 个作用:

1. 可见性:
    1. 它会强制将对缓存的修改操作立即写入主存
    2. 如果是写操作, 它会导致其他 CPU 中对应的缓存行无效
2. 禁止指令重排序
    1. JMM 的设计分为两部分, 一部分是向用户提供的: happens-before 规则, 定义了一个强内存模型, 我们只要理解 happens-before 规则, 就可以实现线程安全.
    2. 另一部分是针对 JVM 实现的, 是硬件层面的性能优化.
    3. volatile 就是禁止了上面的底层重排序的场景.

### DCL 单例

```java
public class Singleton {
    private volatile static Singleton singleton; //volatile是防止指令重排

    private Singleton() {
    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

`
为什么两层 if? 如果 AB 两个线程同时到达第一层 if, 都满足他们的判断结果, A 先抢占锁 B 等待, A 线程创建除了实例, 把锁交给 B, 如果 B 没有这第二层的判断, 同样也会创建出来一个实例
`

### AQS

AQS是一个抽象类, 定义了线程之间获取资源的流程, 比如有线程 A/B, 现在尝试获取资源X, A访问X时, X空闲, 将X锁定, A设置为工作线程； B访问X时, X被占用, 那么就需要:

1. B被阻塞时要做的等待
2. 等待之后B被唤醒与唤醒时锁的分配
3. 这个机制是使用队列实现的, 即将暂时获取不到锁的线程加入到队列中

AQS自己本身是围绕一个 state 进行的, 所有对state的操作都是CAS的； 但是AQS因为是个抽象类, 所以子类的实现是关注点, 子类需要实现什么呢, 主要是acquire(获取独占锁), 这个方法的流程是获取锁(
tryacquire), 获取锁失败之后就需要加入到等待队列(addwaiter)

### Lock 接口

1. 如果实现的可重入:
    1. 定义一个 state,
2. 如何实现的公平 非公平
    1. 非公平好说了, 就是利用 CAS 去修改状态
    2. 公平是通过把锁操作放到一个队列中, 队列先进先出, 之后修改状态.

### CAS

比较和替换, 当前值和期望值相等的时候, 替换为我设置的值

### ReentrantLock

是可重入锁, 也是AQS的具体实现方式 if(current == getExclusiveOwnerThread()), 如果是当前线程, 直接给state加1, 这个state加1是什么意思呢,
就是tryacquire都是围绕state进行的, 当然可以不使用+1的形式, 比如使用ABC, 那么AQS在判断状态的时候就去处理ABC, 用什么都可以, 就是一个队列状态的判断. 公平锁与非公平锁的区别, 就是公平锁是排队的形式,
如果有等待队列就排队到AQS队列中, 没有队列就直接尝试获取锁 非公平锁是上来就CAS操作state.

### synchronized 与 Lock 的区别

1. synchronized 为 java 自带的关键字, Lock 是基于语言实现的一个接口.
2. 性能上, 差距并不大, 但是 Lock 毕竟是基于 CAS 实现, 极端性能上由于 synchronized.
3. Lock 的使用全程由用户手动调用, tryLock() unlock(), 包括即便是异常情况下, 也仍然需要用户手动释放.
4. synchronized 可重入, 非公平; Lock 可重入, 可公平或者非公平.
    1. 解释下什么叫可重入锁: 当一个线程获取对象锁之后, 这个线程可以再次获取本对象上的锁, 而其他的线程是不可以的. 防止死锁的情况.
    2. Lock 如何实现的公平: 利用队列之后 CAS 来修改 state.
    3. Lock 是一个专门管理和操作锁的接口, 具备更多的功能.

### 线程池

1. 参数:
    - corePoolSize: 核心线程数最大值
    - maximumPoolSize: 最大线程数大小
    - keepAliveTime: 非核心存活时间; unit: 非核心存活时间单位
    - workQueue: 存放任务的阻塞队列, 一个管理线程的工具类为什么会有队列? 是因为线程池内部维护了生产消费模型, 任务和线程分开管理
    - threadFactory: 用于设置创建线程的工厂, 可以给创建的线程设置有意义的名字, 可方便排查问题
    - handler: 线城池满了的拒绝策略
2. 工作流程: 先去使用核心线程, 核心线程都使用的时候再来任务, 就放到队列中, 队列满了创建非核心线程直到达到最大线程数, 非核心线程可以收回. 如果达到最大线程了任务也满了, 执行拒绝策略.
3. 存放任务的阻塞队列:
    1. ArrayBlockingQueue: 有界队列, FIFO
    2. LinkedBlockingQueue: 无界队列, FIFO
4. 四种拒绝策略:
    1. AbortPolicy(抛出一个异常, 默认的)
    2. DiscardPolicy(直接丢弃任务)
    3. DiscardOldestPolicy(丢弃队列里最老的任务, 将当前这个任务继续提交给线程池)
    4. CallerRunsPolicy(交给线程池调用所在的线程进行处理)
5. 为什么阿里推荐使用 Threadpool...
    1.
   newFixedThreadPool: corePoolSize和maximumPoolSize相同, keepAliveTime也就无意义了, 所以设置为0L. 这个线程池使用的是LinkedBlockingQueue(无界队列)
   来存corePoolSize线程无法处理的任务. 那么这个线程池问题在哪里呢？最大的问题在于workQueue使用了无界队列, 当任务数多到线程池处理不过来时, 任务全部进入workQueue, 会消耗很大的内存, 甚至OOM.
    2. newSingleThreadExecutor: 是 newFixedThreadPool的一个具化版本. 指定了固定的nThreads: 1. 所以这个线程池的问题跟newFixedThreadPool一样. 当任务过多时,
       可能出现OOM.
    3. newCachedThreadPool: corePoolSize设置为0, 也就是说默认这个线程池中不会有线程创建. 使用的workQueue是SynchronousQueue, 只有被消费才能继续生产.

### CountdownLatch CyclicBarrier Semaphore

1. CountdownLatch: 是一个递减的计数器.
2. CyclicBarrier: 可循环的屏障, 是一个增加的过程, 线程完成逐渐到达屏障. 之后执行某个方法.
3. Semaphore: 信号量, 控制并发多少个线程的.

### ThreadLocal

1. ThreadLocal 是用于线程之间的数据隔离的场景, 每个线程都有 ThreadLocalMap 属性, 这个属性是通过 Entry(KV 结构) 来实现的, K: ThreadLocal 实例, V: 业务数据;
    1. spring mvc 也是把每次请求封装到 ThreadLocal 中.
    2. 比如线程不安全的 SimpleDateFormat.
    3. 打点 K8s 的每次请求耗时, 这个过程是利用 AOP 实现的, 切面中定义了 ThreadLocal 来保存每次调用的开始时间, 调用之后, 当前时间 - ThreadLocal get 的时间, 得到一次调用的耗时.
2. 使用起来主要 3 个方法, set get remove
3. Entry 中的 K 为什么继承弱引用? 弱引用是为了解决内存泄露的场景, 先看下面的内存泄露.
4. ThreadLocal 的内存泄露
    1. 比如线程池场景下, 主要目标是为了复用核心线程, 如何被复用的核心线程一直强引用着 ThreadLocal, 那么 Entry 中的 value 就一直得不到回收, 发生内存泄露.
    2. 怎么解决呢? 使用时要自己 remove 以及源码中一些惰性 remove 的代码, 同时 Entry 中的 K 继承了弱引用. 最好的 V 也继承弱引用, 但是目前 java 还是单继承的现状.

### 线程的状态

1. 创建: new 之后, 仅仅是得到内存而已
2. 就绪: Thread.start() 线程开始等待调度
3. 运行: 如果被调度起来, 则是运行状态
4. 阻塞: 线程放弃 CPU 时间片
5. 死亡: 正常结束, 抛出异常

其中阻塞:

1. 等待阻塞: 某个线程获得锁之后调用 wait,
2. 同步阻塞: 等待获得锁
3. 其他阻塞:

线程的方法:

1. sleep: 线程进入阻塞状态
2. wait: 当前获得锁的线程进入等待, 直到其他线程调用次对象的 notify notifyAll 方法.
3. yield: 暂停当前正在执行的线程对象, 把执行机会让给更高优先级的线程.
4. join: 在当前线程中调用 join, 则当前线程进入阻塞, 直到另一个线程运行结束, 当前线程重新为就绪状态
5. notify/All: 唤醒等待当前对象锁的 一个/全部 线程



