package basicType

fun main(args: Array<String>) {
//---------------------------------if-----------------------------------
    /**
     * 在kotlin中，if是个表达式，可以返回值。因此，kotlin中没有三元表达式
     */
    val a = 1
    val b = 2
    //传统用法
    var max = a
    if (a < b) max = b
    //使用else
    var maxE: Int
    if (a > b) {
        maxE = a
    } else {
        maxE = b
    }
    //使用kotlin if表达式
    val maxI = if (a > b) a else b
    /**
     * if分支可以是代码块，并且最后的表达式是代码块的值
     */
    val maxBlock = if (a > b) {
        println("choose a")
        a
    } else {
        println("choose b")
        b
    }
    //note:如果使用if作为表达式而不是声明（如返回一个值或者为一个变量赋值），表达式必须有'else'分支

//---------------------------------when-----------------------------------
    /**
     * 使用'when'代替C-like类型语言中的switch
     */
    val x = 3
    println("x is $x")
    when (x) {
        1 -> println("x == 1")
        2 -> println("x == 2")
        else -> {
            println("x is neither 1 nor 2")
        }
    }
    /**
     * when会匹配所有的分支，直到有符合的分支；
     * when可以用作表达式和声明
     * 1. 表达式
     *    匹配的分支作为整个表达式的值
     * 2. 声明
     *    个别分支的值会被忽略（类似'if'，每个分支可以是代码块，最后的值是代码块的最后的表达式）
     * 如果没有分支匹配，'else'会被调用；如果'when'是个表达式，'else'是强制要求的，除非编译器认为分支中包括所有的情况
     */
    //1、如果不同的case匹配相同的结果，可以使用'，'分隔
    when (x) {
        0, 1 -> println("x == 0 or x == 1")
        else -> println("x != 0 and x != 1")
    }
    //2、分支条件可以是随意的表达式
    fun paseInt(string: String): Int {
        return string.toInt()
    }
    when (x) {
        paseInt("4") -> println("x == 4")
        else -> println("x != 4")
    }
    //3、可以使用'in','!in'是否在range或者数组中
    when (x) {
        in 1..10 -> ""
        in arrayOf(1, 2, 3) -> ""
        !in Array<Int>(5, { i ->
            i * i
        }) -> ""
        else -> ""
    }
    //4. 可以检查一个值是否属于某个特定类型'is'/'!is'，值得注意的是，由于智能转换，可以直接使用对应类型的方法和属性
    fun hasPrefix(x: Any) = when (x) {
        is String -> x.startsWith("prefix")
        else -> false
    }

    val prefix = "prefix..."
    println("$prefix is String and startWith prefix :" + hasPrefix(prefix))

    //5. 'when'也可以当做'if - else' 使用。当不是用参数时，分支条件是简单的boolean表达式，当条件为true时，对应的分支会执行
    when {
        x == 4 -> println("x == 4")
        x == 5 -> println("x == 5")
        else -> println("x is $x")
    }

//---------------------------------for-----------------------------------
    //for循环可以适用于所有提供迭代器的对象。类似于foreach,结构体可以是表达式或者代码块
    var arr = arrayOf(23, 45436, 346, 4)
    for (i in arr) println("$i in arr")
    for (i in arr) {
        //do something
    }
    /**
     * 像之前说的，for支持所有提供迭代器的对象
     * 1、具有成员或者对iterator()方法的扩展，
     * 2、具有方法next()
     * 3、具有方法hasNext()
     */
    //通过循环使用下标，可以使用indices变量
    for (i in arr.indices) {
        arr[i]
    }

    //note：通过range的迭代，会被编译成最佳的实现方式，不会产生额外的对象

    //另外，可以使用'withIndex'方法

//---------------------------------while-----------------------------------
    /**
     * while和do..while同java
     */
    var y = 10
    while (y > 0) {
        y--
    }

    do {
        y++
    } while (y < 10)

}