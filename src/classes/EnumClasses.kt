package classes

/**
 * 枚举
 * 多数的基础使用时实现类型安全枚举
 * 每个枚举常量是个对象，每个常量使用都好分隔
 */
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

/**
 * 初始化
 */
enum class Color(val rgb: Int) {
    RED(0xff0000),
    GREEN(0x00ff00),
    BLUE(0x0000ff)
}

/**
 * 匿名类
 * 如果枚举类中定义了其他的成员，需要使用分号进行分离
 */
enum class ProtocolState {
    WAITING {
        override fun signal() = WAITING
    },
    TALKING {
        override fun signal() = TALKING
    };

    abstract fun signal(): ProtocolState
}

/**
 * 枚举的使用
 * 1.1之后，可以使用'enumValues<T>()'和'enumValuesOf<T>()'
 * 每个枚举常量都有两个属性：
 * 1、val name:String  --- 名字
 * 2、val ordinal:Int  --- 声明的位置
 *
 * 枚举类实现了Comparable接口，使用声明的位置进行比较
 */
enum class RGB { RED, GREEN, BLUE }

inline fun <reified T : Enum<T>> printAllValues() {
    println(enumValues<T>().joinToString { it.name })
}

fun main(args: Array<String>) {
    ProtocolState.WAITING.signal()

    printAllValues<RGB>()
}