package others

/**
 * 解构
 * 一个解构声明会同时创建多个变量
 */
class Person(var name: String, var age: Int) {
    operator fun component1(): String = name

    operator fun component2(): Int = age
}

/**
 * componentN()方法：
 * 需要使用operator修饰，来允许在解构时调用
 */
/**
 * 在for循环中使用解构
 * a -- component1()返回值
 * b -- component2()返回值
 */
//for ((a,b) in collection}){...}

/**
 * 返回两个参数的方法：通过返回data类实例，接收时解构
 * note:data类自动声明componentsN()方法
 * note：也可以使用保准类Pair并返回Pair<Int,Int>，但是从命名上data类更好
 */
data class Result(val result: Int, val status: Int)

fun function(): Result {
    return Result(1, 2)
}

/**
 * 解构Map:
 * 为了保证map的解构，需要保证：
 * 1、通过iterator()函数呈现一些列值
 * 2、通过component1()和component2()方法提供一对值
 */
//标准库提供了如下方法
//operator fun <K, V> Map<K, V>.iterator(): Iterator<Map.Entry<K, V>> = entrySet().iterator()
//operator fun <K, V> Map.Entry<K, V>.component1() = getKey()
//operator fun <K, V> Map.Entry<K, V>.component2() = getValue()

/**
 * 下划线：
 * 可以使用下划线代替无用的属性
 */

/**
 * 在lambda中使用解构（1.1之后）
 * 如果lambda中有Pair类型（或者Map.Entry，或者其他具有componentN方法的类型）
 */
//note:声明两个参数和一个pair参数的不同是
//{ a -> ... } // one parameter
//{ a, b -> ... } // two parameters
//{ (a, b) -> ... } // a destructured pair
//{ (a, b), c -> ... } // a destructured pair and another parameter

//如果解构参数的component没有使用，可以用下划线替代，而不用声明名称
//
fun main(args: Array<String>) {
    val (name, age) = others.Person("dd", 12)
    println("name is $name,age is $age")

    //使用解构将对象解析成两个值
    val (result, status) = function()

    //遍历map的最好方式可能是
    val map = mutableMapOf<String, String>()
    map.put("key1", "value1")
    map.put("key2", "value2")

    for ((key, value) in map) {
        println("$key --- $value")
    }

    //下划线使用
    val (_, status2) = function()

    //lambda中的解构
    var map2 = map.mapValues { entry -> "${entry.value}!" }
    var map3 = map.mapValues { (key, value) -> "$value!" }

    //可以写出参与解构的类型
    map3.mapValues { (_, value: String) -> "$value" }
    map3.mapValues { (_, value2): Map.Entry<String, String> -> "$value2!" }

}