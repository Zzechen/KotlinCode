#### 包结构和导入
##### 包结构
一个资源文件可能以下面内容开始：
```
package foo.bar

fun baz(){}

class Goo{}
```
包声明包括资源文件所有的内容（如类，方法）。所以，在上面的代码中，baz()的全称是foo.bar.baz,Goo的全称是foo.bar.Goo

##### 默认导入
###### 1、平台通用
每个Kotlin文件都默认导入了下列的包：
* kotlin.*              --基础代码和类型，在所有支持的平台上可用
* kotlin.annotation.*   --注解支持包，包括Retention、Target等元注解
* kotlin.collections.*  --集合类型，包括：迭代器、Collection、List、Set、Map和对应的基础/扩展方法
* kotlin.comparisons.*  --创建比较方法的帮助类，自1.1之后默认导入
* kotlin.io.*           --io，文件和流
* kotlin.ranges.*       --范围、级别，包括IntRange、LongProgression等
* kotlin.sequences.*    --
* kotln.text.*          --文本和正则表达式

###### 2、分平台
1. JVM
    * java.lang.*
    * kotlin.jvm.*
2. JS
    * kotlin.js.*

##### Import
和java不同，Kotlin中没有'import static'用法，所有的声明都是按照'import'规则导入
1. 指定名称
`import foo.Bar`
2. 指定包
`import foo.*`
3. 指定别名
```
import foo.Bar
import bar.Bar as bBar
```
4. 不止局限于类，可以导入其他声明
    * 顶级方法和属性
    * 声明在object declarations 中的方法和属性
    * 枚举常量

##### 顶级方法
如果顶级方法被修饰为private，它就只限定在当前文件中使用