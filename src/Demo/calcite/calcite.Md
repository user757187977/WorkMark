### [calcite](https://calcite.apache.org/)
`The foundation for your next high-performance database.`
1. Standard SQL: 行业通用的 SQL 解析 与 验证.
2. Query optimization: 以关系代数表示查询, 基于 RBO 和 CBO 两种规则进行优化.
3. Any data, anywhere: 连接第三方数据源, 元数据.

![img.png](img.png)

#### Parser
`Calcite 使用 javacc 做 SQL 解析, `
##### javacc
`javacc 是一个 语法词法 解析器的生成器, 是个生成器!`
* 语法解析: parsing
* 词法解析: 将每一个字符串解析成一个个标识符(Token), 

以一个计算器为例

![1.jpg](1.jpg)

画清楚 语法树 是帮助理清思路的重要方法.

#### Validate
#### Optimize
#### Execute
