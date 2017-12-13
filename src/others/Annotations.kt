@file:JvmName("Foo")

package others

import com.sun.xml.internal.ws.api.pipe.Fiber
import kotlin.reflect.KClass

/**
 * 声明： 'annotation'
 * 注解是将元数据附加到代码上的手段
 */

annotation class Fancy

/**
 * 可以使用元注解为注解类添加新的属性
 * 1、@Target 可以添加该注解的目标（类，方法，属性，表达式）
 * 2、@Retention 存活时机，class文件和运行时（默认情况，都存在）
 * 3、@Repeatable 是否可以使用相同的注解在单独的元素上多次
 * 4、@MustBeDocumented 公共API，需要生成API文档中
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION
)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Fancy2

//使用
@Fancy2
class Foo {
    @Fancy2
    fun baz(@Fancy2 foo: Int): Int {
        return (@Fancy2 1)
    }
}

@Target(AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.SOURCE)
annotation class AnnoConstructor

//注解主构造器
class FooC @AnnoConstructor constructor(val string: String)

@Target(AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.PROPERTY_GETTER)
annotation class AnnoSetGet

//注解set/get方法
class FooSG {
    var x: String? = null
        @AnnoSetGet set(value) {
            field = value
        }
}

/**
 * 注解类构造器
 * 允许集中参数类型：
 * 1、基础类型
 * 2、字符串
 * 3、类对象（Foo::class）
 * 4、枚举
 * 5、其他注解
 * 6、上述类型的集合形式
 */
annotation class Special(val why: String)

/**
 * 注解参数不能是可为空类型，因为JVM不支持存储null的注解参数
 * 如果注解参数中包含注解，不需'@'修饰
 */
annotation class ReplaceWith(val expression: String)

@Target(AnnotationTarget.FUNCTION)
annotation class Deprecated(val message: String,
                            val replaceWith: ReplaceWith = ReplaceWith(""))

/**
 * 如果参数中需要使用类对象，需要声明为KClass类型，Kotlin编译器会自动转换成Java类对象，以便Java能够正常使用
 */
annotation class Ann(val arg1: KClass<*>, val arg2: KClass<out Any>)

@Ann(String::class, Int::class)
class MyClass

/**
 * lambda表达式添加注解：最终会添加到生成的invoke()方法上
 */
annotation class Suspendable

val f = @Suspendable { Fiber.current() }


/**
 * 由于Kotlin中的属性或是主构造器的参数可能会生成多个Java元素，因此对应的注解可能存在多个位置，为了指定明确的注解标注。
 * 也可以注解整个文件，需要写在文件的最开头
 */
@Target(AnnotationTarget.VALUE_PARAMETER,//@param
        AnnotationTarget.FIELD, //@field
        AnnotationTarget.PROPERTY_GETTER)//@get
annotation class AnnoParam

class Example(@field:AnnoParam val foo: String, //注解java属性
              @get:AnnoParam val bar: String, //注解java getter方法
              @param:AnnoParam val quux: String)//注解java构造器参数

/**
 * 可以以数组形式对同一目标添加多个注解
 */
annotation class AA

annotation class BB
class ExampleMulti {
    @set:[AA BB]
    var s: String? = null
}

/**
 * 所有的使用场景：
 * 1、file:
 * 2、property:
 * 3、field:
 * 4、get:属性getter
 * 5、set:属性setter
 * 6、receiver:方法或属性扩展的参数接收者
 * 7、param:构造器参数
 * 8、setparam:属性setter参数
 * 9、delegate:存储委托属性的委托实例的属性
 */
//receiver使用
fun @receiver:others.Fancy String.myExtension() {}
//如果没有使用上述场景声明，会使用@Target中的类型，如果存在多个合适目标，按顺序：param-->property-->field

/**
 * java注解调用：100%兼容Kotlin
 * 因为java的参数顺序没有定义，使用时需要指出变量名(参数只有一个时无需指出)
 * 如果Java注解中有数组参数，在Kotlin中会变成'vararg'
 */
@JavaAnno(intValue = 1, stringValue = "dd")
@JavaAnnoOneValue(2)
//Kotlin 1.2+
@JavaWithArray(args = arrayOf("a", "b"))
class CC

//老版本Kotlin
@JavaWithArray(args = ["a", "b", "c"])
class DD

//Java注解中值的获取
fun foo(javaAnno: JavaAnno) {
    val i = javaAnno.intValue
    val s = javaAnno.stringValue
}

@Deprecated("this function is deprecated,use === instead", ReplaceWith("this === other"))
fun main(args: Array<String>) {


}