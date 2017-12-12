package funcs

import classes.Bar
import classes.Foo
import java.util.concurrent.locks.Lock
import javax.swing.tree.TreeNode

/**
 * 内联方法
 */

/**
 * higher-order functions的使用会增加运行时的负担：
 * 每个方法是一个对象，而且会维持一个闭包。
 * 内存分配（ 包括方法和类）和虚拟调用都会增加时间消耗
 */

/**
 * 但是大多数情况，都可以使用内联方法减少这种开销
 * lock()方法可以和call-sites简单内联
 */
//lock(l){foo()}
//不创建新的参数对象和调用，编译器可以忽略以下代码
//l.lock()
//try {
//    foo()
//}
//finally {
//    l.unlock()
//}
//为了避免编译器这样理解，可以通过'inline'定义
inline fun <T> lockEx(lock: Lock, body: () -> T): T {
    return body()
}

/**
 * inlining方式导致更多的额外代码，但是，如果合理的使用会降低消耗（避免内联大的方法，可以在循环内使用）
 */

/**
 * oninline
 * 如果只是将lambda表达式传递给内联函数，可以将方法参数设置为'noinline'
 */
inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {
    //...
}

/**
 * 内联lambda只能在内联方法内或当做内联参数使用，但是'oninline'可以在任何地方使用：存储在变量中，传递
 * note：如果内联方法没有内联方法参数，具体类型参数，编译器会因为这种内联是无益的而发出警告
 */

/**
 * Non-local returns
 * 在Kotlin中，可以使用'return'在常规方法和匿名方法中返回。但是在lambda中，必须使用'label'+'return'，
 * 因为无法再一个封闭的方法中返回，所以单独的return是不允许的
 * 官方例子无法通过编译？
 */

//这样的return方式（lambda内，但是在封闭方法内）称为'non-local return'
fun hasZeros(ints: List<Int>): Boolean {
    ints.forEach {
        if (it == 0) return true
    }
    return false
}

/**
 * note：一些内联方法并不会直接接受lambda作为参数，可能是从其他执行上下文（局部对象或者嵌套方法）。在这种情况下，lambda仍然不允许'non-local'控制流。
 * 这意味着lambda参数需要使用'crossinline'修饰
 * lambda中未实现break和continue，正在计划...
 */

inline fun f(crossinline body: () -> Unit) {
    val f = object : Runnable {
        override fun run() = body()
    }
}

/**
 * 具体类型参数
 * 有时，可能需要传递类型参数
 */
fun <T> TreeNode.findParentOfType(clz: Class<T>): T? {
    var p = parent
    while (p != null && !clz.isInstance(p)) {
        p = p.parent
    }
    return p as T?
}

//可以使用如下方式调用，虽然可行，但是不是很好。。
//treeNode.findParentOfType(MyTreeNode::class.java)
//使用'reified type parameters'方式改写方法
inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
    }
    return p as T?
}

/**
 * 使用'reified'修饰类型参数时，就可以在方法内部像普通的类一样使用泛型。因为方法时内联的，所以要求不能有反射，可以使用'!is','as'等常规操作符
 * 修改后的方法的调用方式为：myTree.findParentOfType<MyTreeNodeType>()
 */
//大多数情况，并不需要使用反射
inline fun <reified T> membersOf() = T::class.members

/**
 * 常规方法（不用'inline'修饰）是不可以有具体参数（reified parameters）的。
 * 不是具体参数类型，或者如'Nothing'的虚拟类型是没有运行时行为的
 */

/**
 * 内联属性
 * 1.1以后，可以为不带有'backing field'的属性添加'inline'修饰（可以修饰set/get方法或直接修饰变量）
 *
 */

val foo: Foo
    inline get() = Foo()
var bar: Bar
    get() = Bar()
    inline set(value) = Unit
//直接修饰变量相当于将set/get都定义为内联(此时需要自定义set/get方法)
inline var barBoth: Bar
    get() = Bar()
    set(value) {}

/**
 * 内联方法公共API限制
 * 当内联方法使用'public'或'protected'修饰，并且不再'private'或'internal'修饰的代码内部，认为是module的公共API，可以在其他module中调用
 *
 * 如果调用部分在内联方法模块更改后未重新编译，可能会有二进制不兼容的风险
 * 为了消除上述风险，公共API内联方法不允许在其主体内使用非公开API
 *
 * 'internal'声明可以使用'@PublishedApi'注解来允许在公共内联方法中使用。当'internal'内联方法使用'@PublishedApi'注解时，
 * 它的内部会像public一样检查内部
 *
 */
fun main(args: Array<String>) {
    println(membersOf<StringBuilder>().joinToString("\n"))
}