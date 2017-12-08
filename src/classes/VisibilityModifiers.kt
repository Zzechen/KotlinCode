package classes

/**
 * 类，objects，接口，构造器，方法，属性和对应的setter（getter方法通常和属性的可见性相同）方法都可以设置可见范围
 * Kotlin中的可见性修饰符：
 * private、protected、internal、public
 * 默认的修饰符为public
 */

/**
 * "top-level":
 * 方法，属性，类，objects和接口可以声明为（同一包内不同文件的top-level认为是同一别介，所以不能同名，直接在包内）
 *
 * 1、如果没有明确使用修饰符，会默认为public，意味着在任何地方都可以使用
 * 2、如果使用private：只能在声明的文件内使用
 * 3、如果使用internal：可以在module的任意地方使用
 * 4、protected不能修饰"top-level"成员
 */
fun baz() {}

class TopBar {}

/**
 * 类和接口：
 * 类中的成员使用时：
 * 1、private ：只在类中可用（包括类的其他成员）
 * 2、protected ： 同private + 子类可见
 * 3、internal ： 同一module中的任意可使用该类的客户端
 * 4、public ： 所有可使用该类的客户端
 * note:如果重写了protected修饰的成员，并且没有明确重写后的修饰符，仍会保留protected
 */


/**
 * 构造器：
 */

/**
 * 局部声明：
 */
fun main(args: Array<String>) {

}