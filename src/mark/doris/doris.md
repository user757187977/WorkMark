# [Apache Doris](https://doris.apache.org/zh-CN/)
`简单易用、高性能和统一的分析数据库`
## [学习路径](https://doris.apache.org/zh-CN/learning)
* 快速开始
  * [Apache Doris 总体介绍](doris.md#apache-doris-总体介绍)
* [数据表设计](doris.md#数据表设计)
  * [数据模型](doris.md#数据模型)
  * [数据划分](doris.md#数据划分)
  * [建表指南](doris.md#建表指南)
  * [Rollup 与 查询](doris.md#Rollup-与查询)
  * [建表实践](doris.md#建表实践)
  * [索引](doris.md#索引)
* 数据导入
  * 导入总览
  * 导入本地数据
  * 导入外部存储数据
  * 订阅 kafka 日志
  * 通过外部表同步数据
* 数据导出
  * 导出数据
  * 导出查询结果集
  * 导出表结构或数据
  * 数据备份
* 数据更新及删除
  * 数据更新
  * 删除操作
  * 批量删除
  * Sequence 列
* 进阶使用
  * 表结构变更
  * 动态分区
  * 数据缓存
  * Join 优化
  * 物化视图
  * BITMAP 精确去重
  * HLL 近似去重
  * 变量
  * 时区
  * 文件管理器
* 生态拓展
* SQL 手册
  * SQL 函数
  * DDL
  * DML
  * 数据类型
  * 辅助命令
* 集群管理
## Apache Doris 总体介绍
### Doris 介绍
1. Apache Doris 是一个基于 MPP 架构的高性能、实时分析数据库，极速、易用，支持高并发点查询，也支持高吞吐量的复杂场景。
2. Apache Doris 前身是百度广告报表的 Palo 项目，2017 开源，2018 捐赠给 Apache，2022 毕业，成为 TLP（Top-Level Project）。
### 使用场景
1. 报表
2. adhoc 查询
3. 统一数仓构建
4. 数据湖联邦查询
### 技术概述
1. 只有两类进程 ![img.png](images/img.png)
   1. Frontend(FE)，主要负责用户请求的接入、查询解析规划、元数据管理、节点管理。
   2. Backend(BE)，主要负责数据存储，查询计划的执行。
2. 在使用接口方面：Doris 采用 MySQL 协议，高度兼容 MySQL 语法，支持标准 SQL。
3. 在存储引擎方面，Doris 采用列式存储。
4. 在索引结构上，支持：
   1. Sorted Compound Key Index，可以最多指定三个列组成复合排序键，该索引可以有效进行数据裁剪更好地支持并发的报表场景。
   2. Z-order Index：该索引可以高效对数据模型中的任意字段组合进行范围查询。
   3. Bloom Filter：对高基数列的等值过滤裁剪非常有效。
   4. Invert Index：对任意字段实现快速检索。
5. 在存储模型上，Doris 支持多种存储模型，并分别对场景优化：
   1. Aggregate Key 模型：相同 Key 的 Value 列合并，通过提前聚合大幅提升性能。
   2. Unique Key 模型：相同 key 的数据覆盖，实现行级数据更新。
   3. Duplicate Key 模型：明细数据模型，满足事实表的明细存储。
6. Doris 也支持强一致的物化视图，物化视图的更新和选择都在系统内自动进行，不需要用户手动选择。
7. 在查询引擎方面，Doris 采用 MPP 模型，节点之间可以并行执行，也支持多个大表分布式 Shuffle Join。![img_1.png](images/img_1.png)
8. 向量化查询引擎，所有的内存结构能够按照列式布局，可以大幅减少虚函数调用、提升 Cache 命中率。
9. 采用 Adaptive Query Execution 技术，可以根据 Runtime Statistics 来动态调整执行计划。
10. 在优化器方面，将 CBO 和 RBO 结合；目前 CBO 还在优化中。
### QA
1. 什么是 MPP 架构？大规模并行处理，首先将数据分散在不同节点，每个节点有自己的资源（CPU、内存、IO），当一个任务到来时，拆分成多个任务后并行的分散到多服务器的节点上，每个节点计算之后汇总到一起。
2. 什么是向量化查询引擎？依赖硬件或者软件中的指定类库与包，将大量重复计算的操作并行执行，以提高计算效率。向量化的执行过程通常是：对不同的数据执行同样的执行，或者说把指令应用于同一个数组/向量。
3. 什么是物化视图？是真正将结果集存储起来的视图，好处就是减少对数据库的访问次数提高性能，物化视图可以指定刷新方式，可以手动刷新，可以定期自动刷新（基于时间或者事件或者数据变更）。

## 数据表设计
### 数据模型
首先要了解 Row Column Key Value；行列很好理解，Column 可以分为两大类：Key 和 Value。从业务角度看，Key 和 Value 可以分别对应维度列和指标列。 分为三类：
#### Aggregate：聚合模型
以这样一个建表为例：
```mysql-sql
CREATE TABLE IF NOT EXISTS example_db.example_tbl
(
    `user_id` LARGEINT NOT NULL COMMENT "用户id",
    `date` DATE NOT NULL COMMENT "数据灌入日期时间",
    `city` VARCHAR(20) COMMENT "用户所在城市",
    `age` SMALLINT COMMENT "用户年龄",
    `sex` TINYINT COMMENT "用户性别",
    `last_visit_date` DATETIME REPLACE DEFAULT "1970-01-01 00:00:00" COMMENT "用户最后一次访问时间",
    `cost` BIGINT SUM DEFAULT "0" COMMENT "用户总消费",
    `max_dwell_time` INT MAX DEFAULT "0" COMMENT "用户最大停留时间",
    `min_dwell_time` INT MIN DEFAULT "99999" COMMENT "用户最小停留时间"
)
AGGREGATE KEY(`user_id`, `date`, `city`, `age`, `sex`)
DISTRIBUTED BY HASH(`user_id`) BUCKETS 1
PROPERTIES (
"replication_allocation" = "tag.location.default: 1"
);
```

这些字段中，没有设置 AggregationType 的我们称为 Key；设置了 AggregationType 的我们称为 Value。 
也就是 Key(user_id，date，city，age，sex)；Value(last_visit_date，cost，max_dwell_time，min_dwell_time)。

那么在导入数据时，如果是这样的源数据：

| user_id | date       | city | age | sex | last_visit_date     | cost | max_dwell_time | min_dwell_time |
|---------|------------|------|-----|-----|---------------------|------|----------------|----------------|
| 10000   | 2017-10-01 | 北京   | 20  | 0   | 2017-10-01 06:00:00 | 20   | 10             | 10             |
| 10000   | 2017-10-01 | 北京   | 20  | 0   | 2017-10-01 07:00:00 | 15   | 2              | 2              |
| 10001   | 2017-10-01 | 北京   | 30  | 1   | 2017-10-01 17:05:45 | 2    | 22             | 22             |
| 10002   | 2017-10-02 | 上海   | 20  | 1   | 2017-10-02 12:59:12 | 200  | 5              | 5              |

导入之后就会呈现这样的结果：

| user_id | date       | city | age | sex | last_visit_date     | cost | max_dwell_time | min_dwell_time |
|---------|------------|------|-----|-----|---------------------|------|----------------|----------------|
| 10000   | 2017-10-01 | 北京   | 20  | 0   | 2017-10-01 07:00:00 | 35   | 10             | 2              |
| 10001   | 2017-10-01 | 北京   | 30  | 1   | 2017-10-01 17:05:45 | 2    | 22             | 22             |
| 10002   | 2017-10-02 | 上海   | 20  | 1   | 2017-10-02 12:59:12 | 200  | 5              | 5              |

可以看到 user_id：10000，date：2017-10-01，city：北京，age：20，sex：0 这两份数据被聚合成了一份数据；其中：
* last_visit_date：聚合方式为 REPLACE，所以 2017-10-01 07:00:00 替换了 2017-10-01 06:00:00。
  * `注意：只能保证后一批次数据会覆盖前一批次`
* cost：聚合方式为 SUM，20 + 15 = 35。
* max_dwell_time：聚合方式为 MAX，10 2 取最大值，所以为 10。
* min_dwell_time：聚合方式为 MIN，10 2 取最小值，所以为 2。
#### Unique：唯一模型
在某些场景下，用户更关注如何保证 Key 的唯一性，因此引入 Unique 模型。

先提出一个问题，唯一模型是不是也是一种特殊的聚合模型？思考下如何用聚合模型来实现唯一模型？

回答：确实，在 1.2 版本之前，唯一模型本质上就是聚合模型的一个特例。这里特意提到了 1.2 版本，那么我们直接对比下这个版本前后两种方式创建出来的表：

* 1.2 版本之前，使用聚合模型的特例来实现的唯一模型：

| ColumnName | 	Type       | 	AggregationType | 	Comment |
|------------|-------------|------------------|----------|
| user_id    | BIGINT      |                  | 用户id     |
| username   | VARCHAR(50) |                  | 用户昵称     |
| city       | VARCHAR(20) | _REPLACE_        | 用户所在城市   |
| age        | SMALLINT    | _REPLACE_        | 用户年龄     |

* 1.2 版本之后，使用唯一模型：

| ColumnName | 	Type       | 	AggregationType | 	Comment |
|------------|-------------|------------------|----------|
| user_id    | BIGINT      |                  | 用户id     |
| username   | VARCHAR(50) |                  | 用户昵称     |
| city       | VARCHAR(20) | **NONE**         | 用户所在城市   |
| age        | SMALLINT    | **NONE**         | 用户年龄     |

那既然在 1.2 版本之前可以用聚合模型实现唯一模型，为什么要特意找了一个新的方案来实现唯一模型？
是因为：聚合模型的实现方式是读时合并，而唯一模型的目标是用写时合并来替换以前的读时合并。

在开启了写时合并选项的Unique表上，数据在导入阶段就会去将被覆盖和被更新的数据进行标记删除，同时将新的数据写入新的文件。
在查询的时候，所有被标记删除的数据都会在文件级别被过滤掉，读取出来的数据就都是最新的数据，消除掉了读时合并中的数据聚合过程，并且能够在很多情况下支持多种谓词的下推。
因此在许多场景都能带来比较大的性能提升，尤其是在有聚合查询的情况下。
#### Duplicate：模型
在某些场景，数据既没有主键也没有聚合需求，那么就用此模型。数据完全按照导入文件中的数据进行存储。

举例：
```mysql-sql
CREATE TABLE IF NOT EXISTS example_db.example_tbl
(
    `timestamp` DATETIME NOT NULL COMMENT "日志时间",
    `type` INT NOT NULL COMMENT "日志类型",
    `error_code` INT COMMENT "错误码",
    `error_msg` VARCHAR(1024) COMMENT "错误详细信息",
    `op_id` BIGINT COMMENT "负责人id",
    `op_time` DATETIME COMMENT "处理时间"
)
DUPLICATE KEY(`timestamp`, `type`, `error_code`)
DISTRIBUTED BY HASH(`type`) BUCKETS 1
PROPERTIES (
"replication_allocation" = "tag.location.default: 1"
);
```

得到的表：

| ColumnName | 	Type	         | SortKey | 	Comment |
|------------|----------------|---------|----------|
| timestamp  | 	DATETIME      | 	Yes    | 	日志时间    |
| type       | 	INT           | 	Yes    | 	日志类型    |
| error_code | 	INT           | 	Yes    | 	错误码     |
| error_msg  | 	VARCHAR(1024) | 	No     | 	错误详细信息  |
| op_id      | 	BIGINT        | 	No     | 	负责人id   |
| op_time    | 	DATETIME      | 	No     | 	处理时间    |

这种数据模型区别于上面两种模型，数据完全按照导入文件中的数据进行存储，不会有任何聚合。而在建表时的 DUPLICATE KEY 其实只是 Sorted Column 的意思，指定按照哪些列排序。

#### 聚合模型的局限性


### 数据划分
### 建表指南
### Rollup 与查询
### 最佳实践
### 动态 schema 表
### 索引
#### 索引概述
#### 倒排索引
#### BloomFilter 索引
#### NGramBloomFilter 索引
#### Bitmap 索引