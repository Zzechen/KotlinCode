package funcs

import com.sun.corba.se.impl.orbutil.graph.Graph
import sun.security.provider.certpath.Vertex

/**
 * 方法声明：fun关键字
 */
fun double(x: Int): Int = 2 * x

/**
 * 默认值
 * 提供默认值的属性在调用时可以忽略
 * 重写时，不允许存在默认值，必须忽略
 */
open class A {
    open fun foo(i: Int = 10) {}
}

class B : A() {
    override fun foo(i: Int) {}  // no default value allowed
}

fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size) {

}

//如果带有默认值参数位于不带的前面，只有在使用"named arguments"时有效
fun foo(bar: Int = 0, baz: Int) {
    println("bar is $bar,baz is $baz")
}

//但是，如果最后一个参数是lambda表达式，则不允许传入默认值，只能使用默认值
fun foo(bar: Int = 0, baz: Int = 0, qux: () -> Unit) {
    println("bar is $bar,baz is $baz")
}

/**
 * Named Arguments
 * 方法参数可以在调用的时候命名，这在方法有大量参数或者参数具有默认值时很方便
 */
fun reformat(str: String,
             normalizeCase: Boolean = true,
             upperCaseFirstLetter: Boolean = true,
             divideByCamelHumps: Boolean = false,
             wordSeparator: Char = ' ') {
}

/**
 * 当使用位置参数和命名参数来调用一个函数时，所有的位置参数应该放在第一个名字之前
 * 如f(1,y = 2)，但是f(x = 1,2)是不允许的
 */
/**
 * 可变参数列表：'vararg'
 */
fun foo(vararg strings: String) {
    strings.map { println(it) }
}
//note:命名参数在调用java方法时无法使用，因为java字节码不会保留参数名称

/**
 * Unit-returning:单位返回
 * 如果方法不返回任何有用的值，那么返回值类型即是'Unit'。'Unit'类型仅包含一个值--Unit，而且不需要明确的返回（return）
 * 改中返回类型，返回值类型的声明和返回都是可选的
 */
fun printHello(name: String?): Unit {
    if (name != null)
        println("hello ${name}")
    else
        println("Hi there!")
    //可选
    return Unit
}

/**
 * 单表达式方法
 * 当方法只是一个表达式的时候，可以省略大括号
 */
//下面方法的返回值类型是可以推断出来的，所以是可以省略的
fun double(x: Float) = (x * 2).toInt()

/**
 * 明确的返回值类型
 * 具有代码体的方法需要明确指出返回类型，除非是返回Unit类型（并且是可选的）。
 * Kotlin对有代码体的方法不进行返回值推断，因为可能会存在复杂的控制流，对使用者也不友好
 */

/**
 * 可变参数：vararg
 * 参数中只能有一个vararg参数
 */
/**
 * 省略形式调用
 * 以下情况，可以使用省略号调用：
 * 1、存在成员方法或者扩展方法时
 * 2、只有一个参数时
 * 3、使用'infix'关键字修饰
 */

//对Int扩展
infix fun Int.shl(x: Int) = shl(x)

/**
 * 方法调用范围
 * 在Kotlin中，可以将方法声明在文件的'top-level'中，并不想java等中需要将方法放在某个类中。
 * Kotlin的方法也可以声明为局部，成员和扩展方法
 */
/**
 * 局部方法
 * 方法内部的方法
 * 局部方法可以使用外部方法的变量，如下面的visited
 */
fun dfs(graph: Graph) {
    val visited = HashSet<Vertex>()
    fun dfs(current: Vertex) {
        if (!visited.add(current)) return
        for (v in current.neighbors)
            dfs(v)
    }
    dfs(graph.vertices[0])
}

/**
 * 成员方法
 * 定义在类或者object内部
 */

/**
 * 泛型方法
 *
 */
fun <T> singletonList(item: T): List<T> {
    return arrayListOf<T>()
}

/**
 * inline方法
 */


/**
 * 扩展方法
 */

/**
 * Higher-Order Functions and Lambdas
 */

/**
 * Tail recursive：尾递归方法 --- tailrec
 * 避免循环，不会引发栈溢出
 * note:tailrec西施的方法，最后一个操作必须调用它自己。在递归调用之后有更多的代码时，不能使用尾递归，并且不能再try/catch中使用。
 * 目前只有JVM后端支持尾递归
 */
//递归计算余弦固定点
tailrec fun findFixPoint(x: Double = 1.0): Double = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))

//循环方式为
private fun findFixPoint(): Double {
    var x = 1.0
    while (true) {
        val y = Math.cos(x)
        if (x == y) return x
        x = y
    }
}

fun main(args: Array<String>) {
    //调用具有默认参数的方法
    read(arrayOf(1, 2), off = 1)

    //调用带有默认参数，后面跟不带的情况
    foo(baz = 1)

    //baz使用默认值
    foo(1) { println("Hello") }
    //bar,baz都是用默认值
    foo { println("hello") }

    //可以方便的调用
    reformat("str")
    //如果没有默认值，调用可能如下,会很麻烦
    reformat("str", true, true, false, '_')
    //可以选择几个参数
    reformat("str", wordSeparator = '_')

    //调用可变参数列表方法
    foo(strings = *arrayOf("a", "b", "c"))

    //Unit返回类型
    printHello("Tom")

    //对扩展方法省略方法调用,等同于 1.shl(2)
    var x = 1 shl 2
    println(x)
}