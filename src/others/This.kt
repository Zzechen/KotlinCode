package others

/**
 * 使用'this'表示当前的接收者
 * 1、在一个类的成员中，'this'指代类的当前对象实例
 * 2、在扩展方法中或者Function Literals with Receiver内，'this'指代左边接收参数
 * 3、如果没限定符，将指代最内层的封闭范围，若想指代其他范围，可以使用标签限定符
 */
/**
 * 为了使用外层（类，扩展方法，标签式function literal with receiver）的this，
 * 使用'this@label',@label指明了this的范围
 */
class A {
    //显示标签@A
    inner class B {
        //显示标签@B
        fun Int.foo() {//显示标签@foo
            val a = this@A // A的this
            val b = this@B // B的this

            val c = this // foo()方法的接收者，Int
            val c1 = this@foo // foo()方法的接收者，Int
            val funLit = lambda@ fun String.() {
                val d = this // funLit的接收者
            }

            val funLit2 = { s: String ->
                // foo()方法的接收者，因为封闭的lambda表达式没有任何接收者
                val d1 = this
            }
        }
    }
}

fun main(args: Array<String>) {

}