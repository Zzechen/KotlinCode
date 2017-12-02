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



fun main(args: Array<String>) {
    //使用主构造器直接赋值成员变量
    val personC = PersonC("firstName", "secondName")
    println("personC'name is ${personC.firstName}·${personC.secondeName}")
}
