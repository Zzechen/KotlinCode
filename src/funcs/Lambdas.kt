package funcs

import java.util.concurrent.locks.Lock

/**
 * Higher-Order Functions
 * 以方法作为参数或者返回方法的方法称为Higher-Order方法
 * 例子：lock(),使用一个锁对象和方法，获取锁，执行方法完毕释放锁
 */
/**
 * 'body'是个方法类型：'() -> T'，所有body应该是个无参、返回值类型为T的方法。在try-catch内调用，被lock保护，结果在lock()方法中返回
 */
fun <T> lock(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}

/**
 * 另一个例子：map()
 */
fun <T, R> List<T>.map(transform: (T) -> R): List<R> {
    val result = arrayListOf<R>()
    for (item in this)
        result.add(transform(item))
    return result
}

/**
 * it : 单个参数时的隐式对象
 */

/**
 * 下划线：(1.1之后)
 * 如果lambda表达式中有不使用的参数，可以使用下划线代替变量名
 */

/**
 * lambda简介
 * 1、lambda表达式总是被大括号包裹
 * 2、参数在'->'之前定义（参数类型可以省略）
 * 3、实体跟在'->'之后
 */
/**
 * lambda表达式和匿名方法
 * lambda表达式或者是匿名方法被称为"function literal"（一个没被声明，而是通过表达式立即使用的形式）
 */
//max(strings, { a, b -> a.length < b.length })
//相当于
fun compare(a: String, b: String): Boolean = a.length < b.length

/**
 * 方法类型
 * 为了方法能够接收方法作为参数，必须使用特别的方法类型作为参数
 * 例如：
 */
fun <T> max(collection: Collection<T>, less: (T, T) -> Boolean): T? {
    var max: T? = null
    for (it in collection)
        if (max == null || less(max, it))
            max = it
    return max
}

/**
 * 参数less是个(T,T) -> Boolean的类型 -------- 两个参数都是T，并且返回值为Boolean的方法
 */

/**
 * 完整Lambda表达式
 * val sum = {x: Int,y: Int -> x + y}
 * 被大括号包裹，参数声明和可选的类型注解，代码体在 '->' 后面
 * 如果表达式的返回值不是Unit，代码体的最后一个表达式被当做返回值（可以使用return明确指出返回值--@）
 * 如果保留所有可选项，应该：
 * val sun:(Int,Int) -> Int = {x,y -> x + y}
 * 如果表达式只有一个参数，可以使用it代替
 */
//note:如果方法参数中的最后一个参数是方法，lambda表达式可以在外面的括号中传递


/**
 * 匿名方法
 * lambda表达式中唯一缺少的是返回值类型，编译器自动推测类型，如果需要明确指出返回类型，可以使用匿名方法（不能是类成员）
 * 方法的参数和返回类型和普通的方法使用一样；如果参数的类型可以通过上下文推断出来，可以省略
 */
//note:匿名方法参数多数在括号内，只有在lambda表达式中可以将参数写在括号外
/**
 * lambda表达式和匿名方法的不同还在于'non-local returns'
 * 没有标签的返回语句总是从用fun关键字声明的函数返回。 这意味着lambda表达式中的返回将从封闭函数返回，而匿名函数中的返回将从匿名函数本身返回
 */

/**
 * 闭包
 * 一个lambda表达式或者匿名方法可达自己的内部，不像Java，Kotlin可以修改闭包中获取到的外部的值
 */


/**
 * Function Literals with Receiver:方法文字和接收器
 * Kotlin提供使用指定接收器调用方法文字的能力。在方法文字体内，可以不额外增加定义调用接收器对象的方法
 * 类似于扩展方法，
 */
//todo 未完待续 https://kotlinlang.org/docs/reference/lambdas.html
//sum:Int.(other:Int) -> Int
//使用时：1.sum(2)


fun main(args: Array<String>) {
    fun toBeSync() = arrayListOf<Int>(1, 2, 3)
    val result = lock(lock, ::toBeSync)
    //通过lambda表达式
    val res = lock(lock, { arrayListOf<Int>(1, 2) })

    /**
     * Kotlin中，如果方法的最后一个参数是个方法类型，可以通过lambda表达式最为参数，可以用括号写在外面
     */
    lock(lock) {
        arrayListOf<Int>(1)
    }

    //调用map方法
    val doubled = arrayListOf<Int>().map { value -> value * 2 }

    //使用it
    arrayListOf<Int>().map { it * 2 }

    //LINQ风格
    var strings = arrayListOf<String>("dfsgd", "dfgae", "eegdc", "dd", "dsss")
    strings.filter { it.length == 5 }.sortedBy { it }.map { it.toUpperCase() }

    //下划线
    mapOf<String, String>().forEach { _, value -> println() }

    //明确指出返回值
    arrayOf(1, 2, 3).filter {
        val shouldFilter = it > 0
        return@filter shouldFilter
    }

    //匿名方法不能是类成员
    fun(x: Int, y: Int): Int = x + y
    //省略匿名方法的参数类型
    arrayOf(1, 2, 3).filter(fun(item) = item > 0)

    //闭包
    var sum = 0
    arrayOf(1, 3, 4, 5, -1, -2).filter { it > 0 }.forEach {
        sum += it
    }
    println("sum is $sum")
}