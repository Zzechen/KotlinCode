package basicType

/**
 * Kotlin具有两种String：
 * 1：转义字符串，包括转义后(包含换行符等)和原始字符串，使用一对"""进行分隔
 * 2：类似于Java字符串
 */
fun main(args: Array<String>) {
    //java string
    val s = "hello,world!\n"

    //raw string
    val text = """
        for(c in "foo")
            print(c)
        """

    /**
     *
     *trimMargin()，默认使用"|"分割，可以传参指定其他字符
     */
    val trimMargin = """
        |Tell me and I forget.
        |Teach me and I remember.
        |Involve me and I learn.
        |(Benjamin Franklin)
        """
    val trimMarginCustom = """
        ^Tell me and I forget.
        ^Teach me and I remember.
        ^Involve me and I learn.
        ^(Benjamin Franklin)
        """
    println(trimMargin.trimMargin())
    println(trimMarginCustom.trimMargin("^"))

    /**
     * 占位符 $
     */
    val sTemplate = 10
    println("i = $sTemplate")
    //添加{}调用对象方法
    val sLen = "hahaha"
    println("$sLen.length is ${sLen.length}")
    //$ 在转义字符串中支持，真正要使用$符号时，采用如下方式
    val price = """
        ${'$'}9.99
        """
    println(price)
}