package others

import kotlin.reflect.KClass
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

/**
 * 反射：
 */
class ReflectionClass
//---------------------------------------------Kotlin反射-----------------------------------------------
/**
 * 类引用 -- ::class
 */
val c: KClass<ReflectionClass> = ReflectionClass::class
//note：Kotlin中的类引用和java中的不同，获取java的类引用，可以使用'.java'属性
val j = c.java

/**
 * 方法引用 -- ::
 */
fun isOdd(x: Int) = x % 2 != 0
//::isOdd是个'() -> Boolean'方法类型的值

/**
 * 方法组合
 */
fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { x -> f(g(x)) }
}

/**
 * 属性引用
 * 1、first-class objects ----- ::
 * 2、
 */
val propertyX = 1
var propertyY = 1

class PropertyA(val p: Int)

//扩展属性
val String.lastChar: Char
    get() = this[length - 1]

//构造器引用
class Constru

fun function(factory: () -> Constru) {
    val x: Constru = factory()
}
//---------------------------------------------与Java互通-----------------------------------------------
/**
 * Java平台，
 */
fun getKClass(o: Any): KClass<Any> = o.javaClass.kotlin

fun main(args: Array<String>) {
    //方法引用
    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd))// 输出 [1,3]

    //方法组合
    fun length(s: String) = s.length

    val oddLen = compose(::isOdd, ::length)
    val strings = listOf("a", "ab", "abc")
    println(strings.filter(oddLen))//输出 "[a,abc]"

    //属性引用
    val x = ::propertyX //KProperty<Int>
    val y = ::propertyY //KMutableProperty<Int>
    y.set(2)
    println("y is $propertyY")
    println(x)
    println(x.get())
    println(x.name)

    println(strings.map(String::length))//输出[1,2,3]

    //获取成员属性
    val p = PropertyA::p
    println(p.get(PropertyA(1)))
    //获取扩展属性
    println(String::lastChar.get("abc"))//输出'c'

    //调用java属性与方法
    println(PropertyA::p.javaGetter)//输出"public final int A.getP()"
    println(PropertyA::p.javaField)//输出"private final int A.p"

    //调用构造器
    function(::Constru)
}