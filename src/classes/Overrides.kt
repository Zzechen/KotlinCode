package classes


//----------------------------------继承-----------------------------------------
/**
 * 在Kotlin中，任何类都显式或者隐式的继承Any类，但是Any并不同于java中的java.lang.Object，它只包含equal()、hashcode()、toString()方法；
 * 默认情况，每个类都会被修饰为final类型，需要继承时，要使用open显式修饰；
 * 当父类有主构造器时，子类需要在主构造器中调用，如果子类没有主构造器，需要在次要构造器中使用super调用
 */
/**
 * 无任何继承，默认继承Any类
 */
class Example

/**
 * 父类有主构造器，并使用open修饰
 */
open class Base(s: Int)

class Child(s: Int) : Base(s) {

}

/**
 * 父类有主构造器，子类没有
 */
open class View(context: String)

class MyView : View {
    constructor(context: String) : super(context) {}
}

//----------------------------------重写-----------------------------------------
/**
 * 重写：方法重写、属性重写、父类方法调用、规则重写
 */
/**
 * Kotlin中，可以重写的方法和变量需要使用open特别指出，并使用override声明重写；
 * 没有用open修饰的方法，在子类中不能存在同名方法；
 * 使用override修饰的方法是open的，可以使用final禁止后续子类的继承
 */
open class BaseMethod {
    open fun hah() {}
    fun no() {}
}

open class ChildMethod() : BaseMethod() {
    final override fun hah() {
    }
    //这是不合法的，无论加不加override修饰
    //fun no(){}
}

class GrandMethod() : ChildMethod() {
    //通过final再次修饰，无法继续继承，无法通过编译
    //override fun hah(){}
}

/**
 * 属性重写：和方法重写类似，但是要求具有相同的类型；
 * 每一个声明的属性都可以被另一个带有getter方法或者初始化的属性重写；
 * 可以用'var'重写'val',但是不能反过来，因为'val'实质上声明了getter方法，用'var'重写时，在子类中额外增加了setter方法
 * override可以使用在主构造器中
 */
open class Foo {
    open val x: Int
        get() = 1

    open fun f() {}
}

class Bar1 : Foo() {
    override val x: Int = 2
}

/**
 * 父类方法、属性调用：super关键字
 * 内部中，可以使用super@OuterName方式调用
 */
class Bar : Foo() {
    override fun f() {
    }

    override val x: Int = 3

    inner class Inner {
        fun y() {
            super@Bar.f()
            super@Bar.x
        }
    }
}

/**
 * 重写规则：当继承实现多个并且有相同的成员时，必须重写该成员
 */
open class A {
    open fun f() { print("A") }
    fun a() { print("a") }
}

interface B {
    fun f() { print("B") } // interface members are 'open' by default
    fun b() { print("b") }
}

class C() : A(), B {
    // The compiler requires f() to be overridden:
    override fun f() {
        super<A>.f() // call to A.f()
        super<B>.f() // call to B.f()
    }
}

fun main(args: Array<String>) {
    //重写规则
    C().f()
}
