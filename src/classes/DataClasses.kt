package classes

/**
 * 通常情况下，创建类的主要目的是包裹数据。这种情况下，会需要很多标准的机械式方法。
 * 在Kotlin中，这种称为'data class'，使用'data'声明
 */

data class User(val name: String, val age: Int)

/**
 * 编译器会对主构造器中所有的属性自动生成如下内容的成员
 * 1、equals()/hashCode()
 * 2、toString() -- 以User为例，为 User(name=John,age=42)
 * 3、依据声明的顺序生成 componentN()方法
 * 4、copy()方法
 */
/**
 * 为了保证行为的一致性和容易理解，data类必须包含下面内容：
 * 1、主构造器必须包括至少一个参数
 * 2、所有主构造器中的参数必须用'val'或者'var'声明
 * 3、data类不能是抽象的、open、sealed和inner
 * 4、（1.1前）data类只可能实现接口
 */

/**
 * 另外，生成的成员遵循如下规则：
 * 1、如果在类内部或是父类中用final实现的equals(),hashCode()或者是toString()方法，那么这些方法将不会生成，而是使用这些以实现的内容
 * 2、如果在父类中有componentN（）方法，并且是open，具有合适的返回类型，相应的方法仍会生成，并且会重写父类的；如果父类中的对应方法不能重写，将会报错
 * 3、在Kotlin1.2中，不推荐使用具有copy()方法的data类，并在1.3中已经禁止使用
 * 4、不允许明确提供componentN()和copy()方法
 */
//note:从1.1开始，data类允许继承其他类
//note:在JVM中，如果生成的类需要有无参构造器，需要提供默认值
data class Stu(val name: String = "", val age: Int = 10)

/**
 * 复制：Copying
 * 通常会需要复制并改变对象的属性，又要保持原有对象不变 --- copy()
 */

/**
 * 标准data类
 * 标准库提供Pair和Triple。在大多数情况，命名对于data类很重要，能够让code有更好的可读性
 */

fun main(args: Array<String>) {
    //复制User，保持原有对象不变
    val jack = User(name = "Jack", age = 1)
    val olderJack = jack.copy(age = 2)
    println(jack)//User(name=Jack, age=1)
    println(olderJack)//User(name=Jack, age=2)

    /**
     * data类和解构声明
     */
    val jane = User("Jane", 10)
    val (name, age) = jane
    println("$name,$age years of age")
}