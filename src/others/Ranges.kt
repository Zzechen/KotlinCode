package others

/**
 * 范围：Ranges
 * 范围表达式使用rangeTo，当和'in/!in'连用时可以使用'..'
 * 针对所有定义了可比较类型，但是对应基础类型，在实现上有优化
 */
/**
 * 积分类型范围（IntRange，LongRange，CharRange）有一个额外的功能：可以迭代
 * 编译器负责将类似的转换为Java的for循环索引，而不会产生额外的开销
 */

/**
 * 如何实现
 * Ranges实现了库中一个公共接口：ClosedRange<T>
 * ClosedRange<T>表示数学意义上的闭合区间，用于可比类型的定义。两个端点：start，endInclusive
 * 主要操作是'contains'，通常在'in/!in'的形式中使用
 */

/**
 * 积分类型级数（IntProgression，LongProgression，CharProgression）表示一个算法级别。
 * 级别由第一个元素，最后一个元素和不为0的步数定义。
 * 前一个元素加上步数为下一个元素；最后的元素只有在整个级数为空不会命中
 */

/**
 * Progression是Iterable<N>的子集，N代表Int、Long和Char，
 * 所以可以在for循环或者是类似map，filter的方法中使用
 * 在Java、JS中，for循环是使用索引实现
 */
//for (int i = first;i != last;i += step){
//
//}

/**
 * 实用方法
 */
/**
 * rangeTo()
 * 简单调用XxxRange的构造器
 * 浮点型（Double、Float）没有定义rangeTo()操作，标准库只提供了通用的Comparable类型，这种方式没有迭代器
 */

/**
 * downTo()
 * 该扩展方法针对所有的积分类型
 */
fun Long.downTo(other: Int): LongProgression {
    return LongProgression.fromClosedRange(this, other.toLong(), -1L)
}

fun Byte.downTo(other: Int): IntProgression {
    return IntProgression.fromClosedRange(this.toInt(), other, -1)
}


/**
 * reversed()
 * 该方法针对所有的XxxProgression类，返回逆序
 */
/**
 * step()
 * 该扩展方法针对所有的XxxProgression类
 * 'step' -- 该值要求大于0，因此该方法不会改变迭代器的方向
 */
fun main(args: Array<String>) {
    val i = 1
    if (i in 1..10) {
        //...
    }

    for (j in 1..4) print(j)//print "1234"
    println()
    for (j in 4..1) print(j)//print nothing

    //数字反向迭代
    for (i in 4 downTo 1) print(i) //print "4321"
    println()
    //为每次循环设置步数
    for (i in 1..4 step 2) print(i)//print "13"
    //不包括最后元素
    for (i in 1 until 3) print(i)//i in [1,3)
    println()
    val floatRange = 0.2f.rangeTo(that = 3.0f)
    println("float range's start is ${floatRange.start},and the end is ${floatRange.endInclusive}")
    if (2 in floatRange) println("2 is in range of $floatRange")

    //note:因为last取决于(last - first) % step == 0，所以输入的last可能和结果不一样
    println((1..12 step 2).last)
    println((1..12 step 3).last)
    println((1..12 step 4).last)

}