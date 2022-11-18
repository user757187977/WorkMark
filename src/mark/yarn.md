`Yarn 除了是一个调度系统, 还是一个计算框架, 包含 ResourceManager NodeManager ApplicationMaster`

* ResourceManager 负责所有资源的监控 分配 管理
* ApplicationMaster 属于每个调度程序的入口, 用于和 RM 协商获取资源, 还有 NM 的监控.
* NodeManager 负责每个节点的维护
* 对于所有的 Applications RM 拥有绝对的资源分配权, 而 AM 则会和 RM 协商资源, 同时和 NM 通信来执行和监控 task.

## ResourceManager

1. RM 负责整个集群的资源管理和分配, 是一个全局的资源管理器.
2. NM 以心跳的方式向 RM 汇报使用情况(CPU/内存), RM 只接受 NM 的资源回报信息, 对于具体的资源处理交给 NM 去完成.
3. Yarn Scheduler 根据 application 的请求为其分配资源, 不负责 application job 的监控 追踪 启动等.

## NodeManager

1. NM 是每个节点的资源和任务管理器, 是这台机器的代理, 负责该节点的程序运行, 以及该节点的监控, Yarn 集群每个节点都运行着一个 NM
2. NM 定时向 RM 汇报本节点(CPU/内存) 的使用情况和 Container的运行状态, 当 RM 宕机时 NM 自动链接 RM 备节点.
3. NM 接收并处理来自 AM 的 Container 的启动停止请求

## ApplicationMaster

1. 用户每次提交的应用程序都包含一个 AM, 它可以运行在除了 RM 之外的机器上.
2. 负责与 RM 调度器协商以获取资源(用 Container 表示).
3. 将得到的任务进一步分配给内部的任务(资源的二次分配).
4. 与 NM 通信以停止/启动 任务.
5. 监控所有任务运行状态, 并在运行失败时重新为任务申请资源之后重启任务.

## Yarn 运行流程

1. client 向 RM 提交应用程序, 其中包含启动该应用的 AM 的必须信息, 例如: AM 程序, 启动 AM 的命令, 用户程序等.
2. RM 启动一个 Container 用于运行 AM.
3. 启动中的 AM 负责向 RM 注册自己, 成功后与 RM 保持心跳.
4. AM 向 RM 请求, 申请对应数目的 Container
5. RM 返回给 AM 申请的 Container 信息, 申请成功的 Container 由 AM 完成初始化, container 启动后, AM 与对应的 NM 通信, 要求启动对应的 Container, AM 与 NM 保持心跳,
   从而对 NM 上运行的任务进行监控和管理
6. contain 运行期间, AM 对 container 进行监控, container 向 AM 汇报自己的进度和状态信息.
7. 应用运行期间, client 与 AM通信获取应用的状态, 进度更新.
8. 应用运行结束后, AM 向 RM 注销自己