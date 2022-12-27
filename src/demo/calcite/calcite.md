### [calcite](https://calcite.apache.org/)
`The foundation for your next high-performance database.`
1. Standard SQL: 行业通用的 SQL 解析 与 验证.
2. Query optimization: 以关系代数表示查询, 基于 RBO 和 CBO 两种规则进行优化.
3. Any data, anywhere: 连接第三方数据源, 元数据.

![img.png](img.png)

#### Parser
`Calcite 使用 javacc 做 SQL 解析.`
##### [javacc](https://javacc.github.io/javacc/)
`Java Compiler Compiler (JavaCC) is the most popular parser generator for use with Java applications.`

javacc 是一个 语法词法 解析器的生成器, 是个 **生成器**, javacc 解析过程的本质也是利用 正则.

* 语法解析: parsing
* 词法解析: 将每一个字符串解析成一个个标识符(Token),
例: c 语言解析成 token
javacc 的关键还是清楚地理解规则的定义, 以一个四则运算计算器为例

![1.jpg](1.jpg)

详见 [.jj](https://github.com/user757187977/WorkMark/blob/master/src/demo/calcite/javacc/Calculator.jj) 文件
画清楚 语法树 是帮助理清思路的重要方法. 

calcite 的 [Parser.jj](https://github.com/apache/calcite/blob/master/core/src/main/codegen/templates/Parser.jj)
#### Validate
#### Optimize
##### 关系代数
`
关系代数是关系型数据库操作的理论基础, 同样也是 calcite 优化模块的核心, 我们常说的 SQL 也仅仅是关系代数运算的一种常用的实现方式而已(并不是唯一方式);
在 calcite 中会将 SQL 转换成关系表达式, 然后通过规则匹配对关系表达式进行优化.
` 


* 基于规则的优化(Rule-Based Optimizer，RBO)
* 基于成本的优化(Cost-Based Optimizer，CBO)

优化准则:
1. 谓词下推 Predicate Pushdown
2. 常量折叠 Constant Folding
3. 列裁剪 Column Pruning
4. 其他

#### Execute


#### 数据库基本概论与关系代数

