package classes

/**
 * Kotlin使用class关键字声明类
 * 类由类名、头部（类型参数和主构造器）和内容组成
 * 头部和内容可以为空，当内容为空时，可以忽略类的大括号
 */
//空内容类
class EmptyBody

//Kotlin类可以有一个主构造器和任意个次构造器,并且主构造器跟随类名后
class Person constructor(firstName: String) {
}

//---------------------------------主构造器------------------------------------------
//如果不包括注解和其他定义，可以忽略constructor关键字
class PersonA(firstName: String) {

}

//主构造器中不可以包含代码，但是可以将逻辑写在初始化块中，init定义的方法中
//主构造器中的参数可以在init方法和类内部使用
class PersonB(firstName: String) {
    var name = firstName

    init {
        println("firstName is $firstName")
    }
}

//可以在主构造器中为变量赋值，可以为可变和final
class PersonC(var firstName: String, val secondeName: String) {

}
//----------------------------------次构造器-----------------------------------------
/**
 * 1、次要构造器必须直接或间接的实现主构造器，可以使用'this'关键字声明调用哪个构造器；
 * 2、当不声明任何构造器时，默认提供无参的public构造器，可以使用'private'修饰，限制调用范围；
 * 3、在JVM中，当主构造器的参数包含默认值时，编译器会生成额外的无参构造器，并为变量提供默认值（Jackson）
 *
 */
//次要构造器
class StuA {
    constructor(name: String) {

    }
}

//调用主构造器的次要构造器
class StuB(name: String) {
    constructor(name: String, age: Int) : this(name) {

    }
}

//限定外界调用
class StuC private constructor(name: String) {

}

//提供默认值
class StuD(val name: String = "Tom") {

}

//----------------------------------创建实例-----------------------------------------
/**
 * 在Kotlin中没有'new'关键字，可以像方法一样调用构造器
 * 另外，在Kotlin中，有嵌套类、内部类和匿名内部类
 */
/**
 * 普通,
 */
class Create

var create = Create()

/**
 * 嵌套类
 */
class CreateOuter {
    var number: Int = 1

    class Nested {
        fun nested() = 1
    }
}

/**
 * 内部类，使用inner关键字声明，可以使用外部类成员变量，并对外部类持有引用
 */
class InnerClass {
    var number: Int = 1

    inner class Inner {
        fun outNumber(): Int {
            return number
        }
    }
}

/**
 * 匿名内部类：object expression
 * window.addMouseListener(object: MouseAdapter() {
 *   override fun mouseClicked(e: MouseEvent) {
 *   }
 *   override fun mouseEntered(e: MouseEvent) {
 *   }
 * })
 * 如果object是个java的功能性接口，可以使用lambda表达式
 * var listener = ActionListener{println("clicked")}
 */


fun main(args: Array<String>) {
    //使用主构造器直接赋值成员变量
    val personC = PersonC("firstName", "secondName")
    println("personC'name is ${personC.firstName}·${personC.secondeName}")

    //嵌套类实例化，方法调用
    var nested = CreateOuter.Nested().nested()
    println("create nested class and call it's method ->> $nested")

    //内部类使用外部类成员变量
    var number = InnerClass().Inner().outNumber()
    println("inner class access to outer class number is $number")
}