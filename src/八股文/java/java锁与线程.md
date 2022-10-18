### synchronized

1. synchronize 是 java 的关键字, 可以用来修改代码块, 方法
2. 通过反编译可以看到其根本原理就是monitor enter和monitor exit来控制的，monitor就是c++调用cpu的两个指令，monitor也有计数器，当为0的时候执行 monitor
   enter，不为0时候等待，进入了+1，离开-1
3. 被加载到 jvm 中的每个实例都有一个对象头, 其中对象头中包含着 32 位的存储单元, 其中就保存了 monitor 信息与锁状态.
4. synchronize 也对应有着优化:
    1.
   偏向锁：当锁对象第一次被线程A获取时，虚拟机将会把对象头中的标志位设为可偏向，是否偏向锁置为1，锁对象与线程A绑定偏向锁，当下一次A线程再参与竞争时，对象偏向被线程A锁住；就相当于尝试获取锁的第一句话就是找对象的偏向锁之后和当前线程比较，成功了把锁给线程A，失败了，取消偏向锁；
    2. 轻量级锁：就是自旋锁+CAS；CAS尝试修改对象头的markword中心的信息，
    3. 那么自旋又是什么意思呢，就是在许多场景下，需要加锁执行的代码是执行很快的，那么我只需要线程死循环一会来获取锁就可以避免阻塞了，JDK6中叫适应性自旋锁，怎么适应性呢，就是死循环的次数是由上一次线程获取锁时自旋的次数。

### wait & notify & notifyall

1. 这三个方法都是对: 对象实例的对象头中的 monitor 的操作
2. wait()方法能让当前线程阻塞，并且当前线程必须拥有此对象的monitor（即锁） notify()方法能够唤醒一个正在等待这个对象的monitor的线程，如果有多个线程都在等待这个对象的monitor，则只能唤醒其中一个线程；
   notifyAll()方法能够唤醒所有正在等待这个对象的monitor的线程；

### volatile

volatile的原理就是内存屏障，它具备两个意义：

1. 可见性：变量的值在线程之间的传递需要通过住内存完成。
2.

禁止重排序：JVM会利用cpu的特性，在不影响结果的情况下，有可能会把后面的代码先执行了，这就是重排序，用volatile修饰的变量被禁止了指令重排序；比如线程1做了两件事，初始化对象，改变一个控制线程2开关的变量，然后线程2，对这个对象做一件事情，假如开关被重排序了，先打开但是没初始化对象，那线程2就会报错；这个例子只是说明下指令重排序。
但是volatile并不能保证原子性，比如AB两个线程操作i++，A线程先操作i之后会写到主内存之前，如果B线程也操作，就不能保证原子性。

### DCL 单例

```java
public class Singleton {
    private volatile static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getSingleton() {
        if (singleton == null) {//防止线程并发，A线程判断为null交个B线程，B线程也为null，进入锁，这时候交个A线程还可能在创建一个对象，所以有了两层if（也就是进入同步代码块之后还要判断一次）
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

为什么使用volatile，为了禁止指令重排序，因为创建一个对象的过程是： 1.申请内存 2.初始化对象 3.指针指向对象 但是第二步和第三步没有依赖关系，可以先指向对象，后去初始化对象，所以这个对象需要volatile禁止一下指令重排序。

### AQS

AQS是一个抽象类，定义了线程之间获取资源的流程, 比如有线程A/B，现在尝试获取资源X， A访问X时，X空闲，将X锁定，A设置为工作线程； B访问X时，X被占用，那么就需要：

1. B被阻塞时要做的等待；
2. 等待之后B被唤醒与唤醒时锁的分配； 3.这个机制是使用队列实现的，即将暂时获取不到锁的线程加入到队列中； AQS自己本身是围绕一个state进行的，所有对state的操作都是CAS的；
   但是AQS因为是个抽象类，所以子类的实现是关注点，子类需要实现什么呢，主要是acquire（获取独占锁），这个方法的流程是获取锁（tryacquire），获取锁失败之后就需要加入到等待队列（addwaiter）

### Lock 接口

1. 如果实现的可重入:
    1. 定义一个 state,
2. 如何实现的公平 非公平
    1. 非公平好说了, 就是利用 CAS 去修改状态
    2. 公平是通过把锁操作放到一个队列中, 队列先进先出, 之后修改状态.

### CAS

比较和替换，当前值和期望值相等的时候，替换为我设置的值

### ReentrantLock

是可重入锁，也是AQS的具体实现方式 重入锁：一个线程获取锁之后任然可以反复加锁，不会出现自己阻塞自己的情况，体现在哪里呢，就是公平锁（tryacquire）和非公平锁（nonfairtryacquire）都有一个判断else
if（current ==
getExclusiveOwnerThread（）），如果是当前线程，直接给state加1，这个state加1是什么意思呢，就是tryacquire都是围绕state进行的，当然可以不使用+1的形式，比如使用ABC，那么AQS在判断状态的时候就去处理ABC，用什么都可以，就是一个队列状态的判断。
公平锁与非公平锁的区别，就是公平锁是排队的形式，如果有等待队列就排队到AQS队列中，没有队列就直接尝试获取锁 非公平锁是上来就CAS操作state。

### synchronized 与 Lock 的区别

1. synchronized 为 java 自带的关键字, Lock 是基于语言实现的一个接口.
2. 性能上, 差距并不大, 但是 Lock 毕竟是基于 CAS 实现, 极端性能上由于 synchronized.
3. Lock 的使用全程由用户手动调用, tryLock() unlock(), 包括即便是异常情况下, 也仍然需要用户手动释放.
4. synchronized 可重入, 非公平; Lock 可重入, 可公平或者非公平.
    1. 解释下什么叫可重入锁: 如果两个线程同时对同一个锁进行加锁操作, 就会产生死锁的现象, 重入锁就是为了解决这样的场景.
    2. Lock 如何实现的公平: 利用队列之后 CAS 来修改 state.
    3. Lock 是一个专门管理和操作锁的接口, 具备更多的功能.

### 线程池

1. corePoolSize：核心线程数最大值; maximumPoolSize：最大线程数大小; keepAliveTime：非核心存活时间; unit：非核心存活时间; workQueue：存放任务的阻塞队列,
   一个管理线程的工具类为什么会有队列, 是因为线程池内部维护了生产消费模型, 任务和线程分开管理; threadFactory：用于设置创建线程的工厂，可以给创建的线程设置有意义的名字，可方便排查问题;
   handler：线城池满了的拒绝策略，主要有四种类型
2. 工作流程：先去使用核心线程，核心线程都使用的时候再来任务，就放到队列中，队列满了创建非核心线程直到达到最大线程数，非核心线程可以收回。如果达到最大线程了任务也满了，执行拒绝策略。
3. 存放任务的阻塞队列：
    1. ArrayBlockingQueue：有界队列，FIFO
    2. LinkedBlockingQueue：无界队列，FIFO
4. 四种拒绝策略：
    1. AbortPolicy（抛出一个异常，默认的）
    2. DiscardPolicy（直接丢弃任务）
    3. DiscardOldestPolicy（丢弃队列里最老的任务，将当前这个任务继续提交给线程池）
    4. CallerRunsPolicy（交给线程池调用所在的线程进行处理）
5. 为什么阿里推荐使用Threadpool...
    1.
    newFixedThreadPool：corePoolSize和maximumPoolSize相同，keepAliveTime也就无意义了，所以设置为0L。这个线程池使用的是LinkedBlockingQueue（无界队列）来存corePoolSize线程无法处理的任务。那么这个线程池问题在哪里呢？最大的问题在于workQueue使用了无界队列，当任务数多到线程池处理不过来时，任务全部进入workQueue，会消耗很大的内存，甚至OOM。
    2. newSingleThreadExecutor：是
       newFixedThreadPool的一个具化版本。指定了固定的nThreads：1。所以这个线程池的问题跟newFixedThreadPool一样。当任务过多时，可能出现OOM。
    3. newCachedThreadPool：corePoolSize设置为0，也就是说默认这个线程池中不会有线程创建。 使用的workQueue是SynchronousQueue，只有被消费才能继续生产。

### CountdownLatch CyclicBarrier Semaphore

1. CountdownLatch: 定义多少个线程执行完成之后共同执行一个方法
2. CyclicBarrier:
3. Semaphore: 信号量，控制并发多少个线程的，

### ThreadLocal

1. ThreadLocal 是用于线程之间的数据隔离的场景, 每个线程内有个 ThreadLocalMap, 这个 map 以 ThreadLocal 实例作为 key, 来保存当前线程下用户需要的数据;
    1. 比如线程不安全的 SimpleDateFormat, spring mvc 也是把每次请求封装到 ThreadLocal 中.
    2. 打点 K8s 的每次请求耗时, 这个过程是利用 AOP 实现的, 切面中定义了 ThreadLocal 来保存每次调用的开始时间, 调用之后, 当前时间 - ThreadLocal get 的时间, 得到一次调用的耗时.
2. 使用起来主要 3 个方法, set get remove
3. 用于存储用户数据的实体叫: ThreadLocalMap, 这个实体是线程的一个属性.
4. ThreadLocalMap 中定义了 Entry 实体并且继承了 弱引用
5. 为什么继承弱引用?
    1. 上面的结构我们已经清晰, 整个 ThreadLocal 的运行过程中, 有线程和线程内的属性
6. ThreadLocal 的内存泄露
    1. 比如我们通过线程池创建的线程使用了 ThreadLocal, 那么核心线程复用并且处于强引用, GcROOT 不会标记, 但是如果你这些核心线程在往 ThreadLocalMap 中 set, 就会导致一直占据内存,
       最终内存泄露.
    2. 关键是几个过程: 线程复用 -> 线程强引用 -> 线程 setThreadLocal -> 内存增加 -> 强引用不 GC -> 内存泄露.
7. 为什么使用之后 remove?
    1. ThreadLocal 运行过程中包含了:线程和线程的属性, 问题的关键就是: 线程是可以复用的, 而线程的属性在线程被复用的时候是不需要被复用的, 所以每个线程使用完这个属性之后, 手动 remove 掉.