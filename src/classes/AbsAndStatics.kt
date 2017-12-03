package classes

/**
 * 使用'abstract'修饰，abstract即为open状态
 */
abstract class AbsA {
    abstract fun abs()
    abstract var x: Int
}

class ExactA(override var x: Int) : AbsA() {
    override fun abs() {
    }
}

/**
 * Companion Objects：
 * 在Kotlin中，没有静态方法，大多数情况，使用包级别方法替代；
 * 如果需要脱离对象调用的方法，可以将方法以object declaration的形式写在类中:用object修饰
 */
//以单例模式为例
object DataManager {
    fun registerData(provider: String) {
        // ...
    }

//    val allData: Collection<String>
}

fun main(args: Array<String>) {
    DataManager.registerData("")
}