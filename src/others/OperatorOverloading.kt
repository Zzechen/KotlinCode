package others

/**
 * 操作符重载
 * Kotlin允许开发者对标准库中的操作符进行重定义。
 * 这些符号有固定的表示和优先级
 * 可以通过成员方法和扩展方法使用固定的名称关联对应的符号
 * 左侧部分用于二元操作，参数用于一元操作
 * 需要使用'operator'修饰
 */
//---------------------------------------------一元操作-----------------------------------------------
/**
 * 一元操作：
 * 表达式    转换
 *  +a     a.unaryPlus()
 *  -a     a.unaryMinus()
 *  !a     a.not()
 * 以'+a'为例，执行步骤为：
 * 1、确定a的类型，让其为T
 * 2、找到通过'operator'定义的接收者为T的无参的unaryPlus()方法（可以是成员方法，扩展方法）
 * 3、如果没有找到相应的方法，会抛出编译异常
 * 4、如果找到，并且返回值为R，那么'+a'的类型是R
 *
 * note：这些操作符对基本类型都做了优化，不会引起额外开销
 */
//一元减法
data class Point(val x: Int, val y: Int)

operator fun Point.unaryMinus() = Point(-x, -y)


/**
 * 自增、自减
 *  表达式    转换
 *   a++    a.inc() + see below
 *   a--    a.dec() + see below
 *
 * 'inc()/dec()'方法必须有返回值，并将改值赋给'++/--'的调用者（必须是可变对象）
 * 以'a++'为例，执行步骤为：
 * 1、确定类型，称为T
 * 2、寻找'operator'修饰的无参inc()方法
 * 3、检查inc()方法的返回类型是否为T的子类
 *
 * 真正的计算过程：
 * 1、在内存中拷贝一份a的值为a0
 * 2、将a.inc()的返回值付给a
 * 3、将a0作为表达式的结果返回
 *
 * 两种情况的效果是一样的：
 * 1、将a.inc()的值赋给a
 * 2、使用a的新的值作为表达式的结果作为返回
 */

//---------------------------------------------二元操作-----------------------------------------------
/**
 * 计算操作符
 *  表达式       转换
 *  a + b     a.plus(b)
 *  a - b     a.minus(b)
 *  a * b     a.times(b)
 *  a / b     a.div(b)
 *  a % b     a.rem(b)(1.1),a.mod(b)(过时方法)
 *  a..b      a.rangeTo(b)
 *
 */
data class Counter(val dayIndex: Int) {
    //为Counter类赋予'+'操作
    operator fun plus(increment: Int): Counter {
        return Counter(increment + dayIndex)
    }
}

/**
 * 'in'操作符
 *  表达式      转换
 *  a in b    b.contains(a)
 *  a !in b   !b.contains(a)
 */

/**
 * 索引操作
 *  表达式                转换
 *  a[i]                 a.get(i)
 *  a[i,j]               a.get(i,j)
 *  a[i_1,...,i_n]       a.get(i_1,...,i_n)
 *  a[i] = b             a.set(i,b)
 *  a[i,j] = b           a.set(i,j,b)
 *  a[i_1,...,i_n]       a.set(i_1,...,i_n,b)
 */

/**
 * invoke操作符
 *  表达式          转换
 *  a()            a.invoke()
 *  a(i)           a.invoke(i)
 *  a(i,j)         a.invoke(i,j)
 *  a(i_1,...,i_n) a.invoke(i_1,...,i_n)
 */

/**
 * 操作赋值
 *  表达式       转换
 *  a += b      a.plusAssign(b)
 *  a -= b      a.minusAssign(b)
 *  a *= b      a.timesAssign(b)
 *  a /= b      a.divAssign(b)
 *  a %= b      a.remAssign(b)（1.1以后）,a.modAssign(b)(以过时)
 * 编译器执行过程（a += b为例）：
 * 1、如果plushAssign方法存在：
 *    1、如果关联的plus()方法存在，报错？？！
 *    2、确保返回值类型为Unit，否则报错
 *    3、为a.plusAssign(b)生成代码
 * 2、否则，尝试生成a = a + b的代码（包括类型检查，'a + b'必须是a的子类）
 *
 * note：在Kotlin中，赋值不是表达式
 */

/**
 * 等于操作
 *  表达式      转换
 *  a == b     a?.equals(b) ?:(b === null)
 *  a != b     !a.equals(b) ?:(b === null)
 *
 *'=='操作比较特殊：会被转换成复杂的表达式。 null == null总是true，x == null对不为空的x总是false并且不会调用x.equals()
 *
 * note:'===/!=='无法重载
 */

/**
 * 比较操作符
 *  表达式     转换
 *  a > b     a.compareTo(b) > 0
 *  a < b     a.compareTo(b) < 0
 *  a >= b    a.compareTo(b) >= 0
 *  a <= b    a.compareTo(b) <= 0
 * 所有的比较会调用compareTo()方法，并返回Int类型
 */

/**
 * 友情链接：属性代理，infix关键字
 */

fun main(args: Array<String>) {
    //自定义类的一元减法
    val point = Point(10, 20)
    println(-point)

}