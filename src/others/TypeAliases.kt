package others

import java.io.File

/**
 *
 */

//对于泛型很方便
typealias FileTable<K> = MutableMap<K, MutableList<File>>

//方法类型
typealias MyHandler = (Int, String, Any) -> Unit

typealias Predicate<T> = (T) -> Boolean

//内部类或者嵌套类
class AliasA {
    inner class Inner
}
typealias AInner = AliasA.Inner

fun main(args: Array<String>) {

}