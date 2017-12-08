package classes

/**
 * Kotlin中的接口类似于Java 8：可以包括抽象方法和实体方法
 * 和抽象类的不同点在于：接口无法保存状态
 * 可以包括属性：抽象的或者是提供实现者(必修'val'修饰)
 */
interface MyInterface {
    var prop: String//abstract
    val propertyWithImpl: String
        get() = "propertyWithImpl"

    fun bar()
    fun foo() {
        //可选
    }
}

class InterfaceChild : MyInterface {
    //改抽象属性必须重写
    override var prop: String = ""

    //必须重写
    override fun bar() {
        println("bar")
    }

}

/**
 * 解决继承冲突：
 * 子类可能会实现多个接口，当存在相同的方法时，需要具体实现
 */
interface IA {
    fun foo() {
        println("IA:foo")
    }

    fun bar()
}

interface IB {
    fun foo() {
        println("IB:foo")
    }

    fun bar() {
        println("IB:bar")
    }
}

/**
 * 在IA和IB中都声明了foo()和bar()方法，并且都实现了foo()，但是只有IB实现了bar()
 * 此时，CC实现了IA和IB，要求必须实现单实现方法bar()和多实现方法foo()
 */
class CC : IA, IB {
    override fun foo() {
        super<IB>.foo()
        super<IA>.foo()
    }

    override fun bar() {
        super<IB>.bar()
    }
}

/**
 * 构造器：四种修饰符都可以使用
 * note：当修改主构造器时，需要明确写出'constructor'
 */

class PrivateC private constructor(a: Int)

/**
 * 局部变量、方法、类：不可以使用
 */

/**
 * Modules ： 模块
 * 'internal'意味着同一模块的成员是可见的。
 * 一个模块是一起编译一系列Kotlin文件：
 * 1、一个IntelliJ Idea module
 * 2、一个maven项目
 * 3、一个Gradle源集合
 * 4、一个Ant任务调用编译的一系列文件
 */

fun main(args: Array<String>) {
    var cc = CC()
    cc.bar()
    cc.foo()
}