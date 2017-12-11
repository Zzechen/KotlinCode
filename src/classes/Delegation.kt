package classes

/**
 * 委托：
 * 委托模式是实现继承的很好的方式。
 * Kotlin中原生支持委托模式，可以0代码实现
 * 'Derived'类可以继承自接口'Base'，并且委托所有的公共方法给特定的对象
 */
interface DelegationBase {
    fun print()
}

class DelegationBaseImpl(val x: Int) : DelegationBase {
    override fun print() {
        print(x)
    }
}

/**
 * Derived会将by后的对象存储在内部，编译器将生成转发给该对象从Base中继承的方法
 */
class Derived(b: DelegationBase) : DelegationBase by b {
    override fun print() {
        //编译器将使用重写的方法
        println("abc")
    }
}

fun main(args: Array<String>) {

    val b = DelegationBaseImpl(10)
    Derived(b).print()

}