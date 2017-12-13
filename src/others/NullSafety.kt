package others

import javax.xml.soap.Node

/**
 * Kotlin的类型系统旨在代码层面消除空指针
 * 在包括Java的大多数语言中，都存在空指针陷阱
 * Kotlin中造成空指针的仅可能为：
 * 1、手动'throw NullPointerException()'
 * 2、后续提到的'!!'的使用
 * 3、外部Java代码引发
 * 4、在初始化方面有一些数据不一致的地方（在某个地方使用了一个未初始化的构造函数）
 */

/**
 * Kotlin中的引用分为：可为空 - 不可为空
 * 使用'?'声明
 * 使用不可空的对象，可以保证不会引发空指针异常
 * 使用可为空对象时，必须进行null验证
 */

//---------------------------------------------可为空对象使用-----------------------------------------------
/**
 * null检查
 * 1、b != null 检查
 * 2、b?.length
 */
/**
 * 层级中的链式为空验证
 * bob?.department?.head?.name
 * 如果上面的链式中，有任何一个节点为空，都会返回空
 * 使用let操作符处理不为空的逻辑
 */

/**
 * Elvis Operator --- ?:
 * 如果有个可为空对象r，"如果r为空，使用r，否则提供其他值"
 */
val b: String? = ""
val l: Int = if (b != null) b.length else -1
//使用?:简化代码
//如果?:左边的值不为null，作为表达式值返回，否则返回右边的值
val l1 = b?.length ?: -1

/**
 * note：因为'throw'和'return'都是表达式，所有可以放在'?:'的右边，有时会很便利
 */
fun foo(node: Node): String? {
    val parent = node.parentElement ?: return null
    val name = node.nodeName ?: throw IllegalArgumentException("name expected")
    //...
    return name
}

/**
 * '!!'操作符：
 * 不为空断言操作符，将任何类型转换为不为空类型，当断言的值为空时抛空指针
 * 使用需谨慎
 */
val l2 = b!!.length

/**
 * 安全转换
 * 如果给定对象不是目标类型，会得到ClassCastException。
 * 可以使用'as?'安全转换，失败会得到null值
 */
val aInt: Int? = b as? Int

/**
 * 集合的可为空类型
 * 如果集合内部有空对象，可以使用filterNotNull()方法顾虑空对象
 */
val nullableList: List<Int?> = listOf(12, 3, null, 4)
val intList: List<Int> = nullableList.filterNotNull()

fun main(args: Array<String>) {
    var a: String = "abc"
    //a无法赋值为null
//    a = null
    var b: String? = "abc"
    //b可以赋值为null
    b = null
    //对可为空对象操作时，会提示null检查
    if (b != null) {
        val l = b.length
    }

    //let使用
    val listWithNulls: List<String?> = listOf("A", null)
    for (item in listWithNulls) {
        item?.let { println(it) }
    }
}