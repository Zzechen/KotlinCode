package classes

/**
 * 嵌套类
 */
class Outer {
    private val bar: Int = 1

    class Nested {
        fun foo() = 2
    }
}

/**
 * 内部类:inner
 * 持有外部类的实例的引用
 */
class InnerOuter {
    private val bar: Int = 1

    inner class Inner {
        fun foo() = bar
    }
}

/**
 * 匿名内部类：使用object expression
 * 当接口为单一方法时，可以使用lambda表达式创建
 * val listener = ActionListener { println("clicked") }
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
fun main(args: Array<String>) {

    //嵌套类
    val nested = Outer.Nested().foo()

    //内部类
    val inner = InnerOuter().Inner().foo()
}