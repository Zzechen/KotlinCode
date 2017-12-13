package others

/**
 * Kotlin中的异常都是继承自Throwable
 * 每个异常都包括message，栈信息和可选的原因
 *
 * 抛出异常：throw
 * 捕获异常：可能有任意个catch块，finally可以忽略，但是至少要有一个catch或者finally块
 * try{
 * //代码
 * }
 * catch(e:SomeException){
 * //处理
 * }
 * finally{
 * //可选
 * }
 */

/**
 * try是个表达式，可以有返回值
 * try或者catch的返回值是块内最后一个表达式
 * finally代码块不会影响表达式的结果
 */
val a: Int? = try {
    2 / 0
} catch (e: Exception) {
    null
}

/**
 * 异常检查
 * Kotlin中没有异常检查，
 * jdk中StringBUilder类的接口实现
 * Appendable append(CharSequence csq) throws IOException;
 * 上面的代码要求，每次使用append时都要捕获IOException。Why？
 * 因为可能引发IO问题，导致需要这样
 * try{
 *  log.append(message);
 * }catch(IOException e){
 *  //必须安全
 * }
 * 但是这样并不好，
 */

/**
 * Nothing类型
 * throw是个表达式，可以在'?:'中使用
 * throw表达式返回值的类型是Nothing,该类型是无值的，用于标记不可达的代码位置。
 * 编写代码是，可以用Nothing来标记永不返回的方法
 */
val s = "" ?: throw IllegalArgumentException("null")

//调用改方法时，编译器知道执行不会超过调用的范围
fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}

/**
 * 类型推断时，Nothing有一个可能的值--null。如果使用null初始化而没有指定类型，那么会推断成Nothing?
 */
val x = null//x的类型为Nothing?
val lNothing = listOf(null)//该对象的类型为List<Nothing?>

fun main(args: Array<String>) {
    val name: String? = null
    println(x)
    println(lNothing)
    val s = name ?: fail("name required")
    //如果执行到这里，说明s已经初始化
    println(s)
}