package others

import java.io.File

/**
 * is/!is
 * 可以在运行时检查给定对象的类型判断
 */

/**
 * 智能转换
 * 大多数情况，Kotlin中不需要使用明确的类型转换操作,因为编译器会追踪is-check和显示强制转换以获得不可变的值，并且在需要时自动插入强制转换
 * 智能转换可以放在when和while中
 */
fun demo(x: Any) {
    if (x is String) {
        //x会被自动转换为String
        println(x.length)
    }
}

//在when中使用
fun whenCheck(x: Any) {
    when (x) {
        is Int -> println(x + 1)
        is String -> println(x.length)
        is IntArray -> println(x.sum())
    }
}

/**
 * note:当编译器无法保证变量在检查和使用过程中的变化时，智能转换不会起作用
 * 更多规则：
 * 1、'val'局部变量 -- 总是可以
 * 2、'val'属性 -- 支持属性是private或者internal检查行为和声明的位置在相同的module时；当属性是open或者具有自定义getter时无效
 * 3、'var'局部变量 -- 如果变量不是在使用和检查之间声明的，并且不是声明在lambda中时可以
 * 4、'var'属性 -- 从不never（因为该属性可能会在任一时刻被修改）
 */

/**
 * 不安全的转换：as
 * 通常，转换失败会抛异常，所以称之为不安全
 * note：null转换时需要声明为可空类型,否则会抛出异常
 * val x:String? = y as String?
 */
/**
 * 安全的转换：as?
 * 为了避免抛出异常，在失败时会返回null
 * val x:String? = y as? String
 * note：尽管右边的类型是非空，但左边是可空，返回null
 */

/**
 * 类型擦除和泛型检查
 * Kotlin保证运行时涉及泛型的操作是安全的，然而，运行时的泛型对象并没有真实类型参数信息，如List<Foo>擦除后为List<*>。
 * 通常情况下，在运行时无法检查泛型类型
 * 由于运行时类型擦除，编译器无法验证is-check，例如：'ints is List<Int>'或者'list is T'
 * 但是可以对象使用'*'级别类型保护策略
 */
//if (something is List<*>) {
//    something.forEach { println(it) } // The items are typed as `Any?`
//}
/**
 * 相似的，在编译时可以检查（或者转换）带有类型参数的对象时
 * 注意：此时尖括号可以忽略
 */
fun handleStrings(list: List<String>) {
    if (list is ArrayList) {
        //list会只能转换成ArrayList<String>
    }
}

/**
 * 对于内联方法中的'reified'类型参数：
 * 对于每个调用，都会有明确的类型，确保了'arg is T'的类型检查，但是若'arg'是泛型类型本身，类型同样会被擦除
 * 如：
 */
inline fun <reified A, reified B> Pair<*, *>.asPairOf(): Pair<A, B>? {
    if (first !is A || second !is B) return null
    return first as A to second as B
}

//使用
val somePair: Pair<Any?, Any?> = "item" to listOf(1, 2, 3)
val stringToSomething = somePair.asPairOf<String, Any>()
val stringToInt = somePair.asPairOf<String, Int>()
val stringToList = somePair.asPairOf<String, List<*>>()
val stringToStringList = somePair.asPairOf<String, List<String>>()//打破了类型安全

/**
 * 不检查转换
 * 之前说到，由于运行时的类型擦除使得检查泛型的实际参数类型变得不可能，并且代码中的泛型可能和其他联系不紧密，使得编译器无法保证类型安全
 * 即便如此，也有高级的程序逻辑来代替类型安全，如：
 */
fun readDictionary(file: File): Map<String, *> = file.inputStream().use {
    TODO("Read a mapping of strings to arbitrary elements.")
}

val intsFile = File("ints.dictionary")
//此处编译器会抛出未检查⚠️
val intsDictionary: Map<String, Int> = readDictionary(intsFile) as Map<String, Int>

/**
 * 为了避免上述的编译器警告
 * 可以重新设计程序结构：
 * 可以声明类型安全的接口DictionaryReader<T>和DictionaryWriter<T>，然后提供不同的实现
 * 通过合理的抽象，依据调用的实现细节避免不检查的转换
 */

/**
 * 对于泛型方法，使用'reified'类型参数使检查后转换(arg as T),除非arg的类型内有被擦除的类型参数
 */
//注解取消编译器警告
inline fun <reified T> List<*>.asListOfType(): List<T>? =
        if (all { it is T })
            @Suppress("UNCHECKED_CAST")
            this as List<T> else
            null

fun main(args: Array<String>) {
    //类型判断,符合时可以直接使用对应类型成员
    var obj = "obj"
    if (obj is String) println(obj.length)

    //当反向检查失败return后，编译器会将对象转换为对应类型
    var x = "dddd"
    if (x !is String) return
    println(x.length)

    //可以直接在类型判断的右边加对应类型的判断(&&,||)
    if (x !is String || x.length == 4) println(x.length)

    //内联方法中'reified'类型
    println("stringToSomething = " + stringToSomething)
    println("stringToInt = " + stringToInt)
    println("stringToList = " + stringToList)
    println("stringToStringList = " + stringToStringList)
}