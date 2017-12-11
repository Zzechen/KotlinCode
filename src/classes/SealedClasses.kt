package classes


/**
 * 密封类：
 * 有限制的类层级：值只能是一定集合中的类型
 * 在某种意义上，类似于枚举，但是每个枚举实例是以单例的形式存在，而密封类可能有多个实例存在
 * 使用'sealed'关键字修饰，密封类可以有子类，但是要求它们和对应的密封类在同一个文件中（在1.1之前，要求更为严格，必须嵌套在密封类中）
 */

sealed class Expr

data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()

/**
 * 密封类是抽象的，不可以被直接实例化，可以有抽象成员
 * 密封类不可以有不为private的构造器（默认为private）
 * note：间接继承自密封类的类可以写在任何地方
 */

/**
 * 在使用when表达式时，可以提现密封类的优势。
 */

fun eval(expr: Expr): Double = when (expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    NotANumber -> Double.NaN
//已经包含所有的情况，不需要提供else
}

fun main(args: Array<String>) {

}