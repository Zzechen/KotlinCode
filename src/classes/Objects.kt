package classes

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent

/**
 * Java匿名内部类 ---- Kotlin object expressions and object declarations
 */

/**
 * 对象表达式
 * 创建继承自其它类的匿名类：
 */
//window.addMouseListener(object : MouseAdapter() {
//    override fun mouseClicked(e: MouseEvent) {
//        // ...
//    }
//
//    override fun mouseEntered(e: MouseEvent) {
//        // ...
//    }
//})
//当构造器为有参数时
open class ObjA(x: Int) {
    open val y: Int = x
}

interface ObjB {}

val ab: ObjA = object : ObjA(1), ObjB {
    override val y = 15
}

//如果只是需要对象，而不需要子类，可以更简单
fun objFoo() {
    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }
    println(adHoc.x + adHoc.y)
}

/**
 * note:匿名对象只可以使用在局部和私有声明中
 * 如果将匿名对象作为公共方法的返回值或者属性，那么真正的返回值或者属性类型僵尸匿名对象的子类，如果为定义任何子类，将是'Any'
 * 在匿名对象中添加的成员将是无法访问的
 */
class ObjC {
    //私有方法，所以返回类型是匿名对象的类型
    private fun foo() = object {
        val x: String = "x"
    }

    //公共方法，所以返回类型是'Any'
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x
        //error，无法解析x的引用
//        val x2 = publicFoo().x
    }
}

//类似于Java，匿名对象可以使用外部的变量，但不需要final
fun countClicks(window: JComponent) {
    var clickCount = 0
    var enterCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }

        override fun mouseEntered(e: MouseEvent) {
            enterCount++
        }
    })
    // ...
}

/**
 * 对象声明
 * 使用'object'关键字。就像变量声明一样，对象声明不是表达式，无法使用在右侧的声明中
 */
object DataProviderManager {
    val list: List<String> = listOf()
    fun registerDataProvider(provider: String) {
    }

    val allDataProviders: Collection<String>
        get() = list
}

//可以拥有子类
object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent?) {

    }

    override fun mouseEntered(e: MouseEvent?) {

    }
}
//note:对象声明不可以是局部的（方法内部），但是可以嵌套在其他的对象声明中，或者不是'inner'的类中


/**
 * 关联对象：Companion Objects
 * 对象声明在类内部时，可以使用'companion'关键字修饰
 */
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

//关联对象的名字可以忽略
class MyClassOmitted {
    companion object {

    }
}

//可以通过'Companion'获取对象
val x = MyClassOmitted.Companion

//note:即使关联对象的成员的使用类似其他语言中的静态成员，但是在运行时，他们仍然是真实的对象的成员，例如
interface Factory<T> {
    fun create(): T
}

class MyClassFac {
    companion object : Factory<MyClassFac> {
        override fun create(): MyClassFac = MyClassFac()
    }
}
//在JVM中，使用'@JvmStatic'是可以将这些成员真正生成静态方法和属性的

/**
 * 对象声明和对象表达式之间的语义差别
 * 1、对象表达式是在被使用的地方立即执行（初始化）的
 * 2、对象声明是懒加载的，直到第一次使用的时候
 * 3、关联对象是在相应的类被加载的时候被初始化的，类似于Java的静态代码块
 */
fun main(args: Array<String>) {
    println(ab.y)

    objFoo()

    //通过名称直接使用对象声明中的对象
    DataProviderManager.registerDataProvider("")

    //关联对象的成员可以直接使用外部类调用
    val instance = MyClass.create()
}