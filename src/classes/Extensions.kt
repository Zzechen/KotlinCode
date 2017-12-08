package classes

import java.security.cert.Extension

/**
 * 扩展
 *
 * 类似C#和Gosu，Kotlin无需继承或者使用设计模式（如：装饰者）就可以使用对一个类(方法、属性)的扩展,通过称为'extensinos'的定义方式
 */

/**
 * 方法扩展：需要在名字后写明接受类型（要扩展的类型）
 * 扩展方法中的this指向需要扩展的对象实例
 */
//为MutableList<Int>添加swap方法
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    //this指向list
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

//可以通过泛型，指定所有类型
fun <T> MutableList<T>.swapT(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

/**
 * 扩展是静态解析的
 * 扩展并不会修改原有的类。
 * 通过定义扩展，并没有想类中添加新的成员，而只是使用这种变量的点符号来调用新的方法；
 * 扩展方法时静态分发的，并且和接收类型有关。这意味着扩展方法的调用由被扩展的类型决定，而不是由结果的类型决定
 * 如：
 */
open class ExtensionC

class ExtensionD : ExtensionC()

fun ExtensionC.foo() = "C"
fun ExtensionD.foo() = "D"
fun printFoo(c: ExtensionC) {
    println(c.foo())
}

/**
 * 如果一个类具有成员方法，并且具有相同接收类型，名称和合适的参数来定义的扩展方法，这种情况下会总是调用成员方法
 * 以ExtensionF为例，调用foo()时，都会打印"member"
 * 如果指定不同的方法参数，是可以被调用的
 */
class ExtensionF {
    fun foo() {
        println("member")
    }
}

//该扩展方法并不会调用
fun ExtensionF.foo() {
    println("extensions")
}

/**
 * 可为空接收者
 * 扩展是可以指定接收者的类型为空的。这样的扩展甚至可以被空对象调用，而且可以使用"this == null"来检查是否为空
 * （所以在Kotlin中，任何对象都可以调用toString()方法）
 */
fun Any?.toString(): String {
    if (this == null) return "null"
    return toString()
}


/**
 * 属性扩展
 * 由于扩展并不会向类中添加新的成员，所以对'backing field'是无效的。
 * 这也是为什么扩展属性不被允许初始化的原因
 * 并且只能明确定义setter/getter方法，不会默认生成
 */
//下述代码无法通过编译，因为扩展属性不允许初始化
//val ExtensionF.bar = 1

/**
 * Companion Object Extentsions
 * 如果一个类具有companion object，可以对给对象的方法和属性进行扩展
 */
class ExtensionCompanion {
    companion object {

    }
}

fun ExtensionCompanion.Companion.foo() {
    println("ExtensionCompanion.Companion.foo")
}

/**
 * 扩展的使用范围
 * 大多数情况，我们在'top-level'中定义扩展，如果需要在其他包中使用，通过import即可
 */

/**
 * 在类内部定义扩展成员
 * 这种情况下，会有多个receivers - objects成员在没有限定符的情况下访问扩展成员。
 * 包含扩展成员的类的实例称为'dispatch receiver'，扩展方法的'receiver type'的实例对象称为'extension receiver'
 */
class ExtensionReceiver {
    fun bar() {}
}

class DispatchReceiver {
    fun baz() {}
    fun ExtensionReceiver.foo() {
        bar() //调用 ExtensionReceiver.bar方法
        baz() //调用 ExtensionReceiver.baz方法
    }

    fun caller(extensionReceiver: ExtensionReceiver) {
        extensionReceiver.foo() //调用扩展方法
    }
}

/**
 * 在dispatch/extension receiver成员名称有冲突的情况，extension receiver具有优先级
 * 如果想要调用dispatch receiver的成员，可以使用'this@ClassName'，如下
 */
class ReceiverConflict {
    fun ExtensionReceiver.foo() {
        toString()
        this@ReceiverConflict.toString()
    }
}

/**
 * 成员的扩展可以使用'open'修饰，并且可以在子类中重写
 * 这意味着dispatch的类型是不定的，但是extension的类型是确定的
 * 即：----------
 * caller方法中的对象只会调用该类型的foo方法，而caller的调用者决定了具体调用OpenD的哪个foo方法
 */

open class OpenD{

}
class OpenD1:OpenD(){

}
open class OpenC{
    open fun OpenD.foo(){
        println("OpenD.foo in OpenC")
    }
    open fun OpenD1.foo(){
        println("OpenD1.foo in OpenC")
    }
    fun caller(d: OpenD){
        d.foo() //调用扩展方法
    }
}
class OpenC1 :OpenC(){
    override fun OpenD.foo() {
        println("OpenD.foo in OpenC1")
    }

    override fun OpenD1.foo() {
        println("OpenD1.foo in OpenC1")
    }
}

/**
 * Motivation ： 扩展目的
 * 在java中，通常命名为"*Utils"：FileUtils，StringUtils，并且大致的使用方式为：
 * Collections.swap(list, Collections.binarySearch(list, Collections.max(otherList)), Collections.max(list));
 * 使用静态导入，使用如下：
 * swap(list, binarySearch(list, max(otherList)), max(list));
 * 由于某些类不可能提供完整的方法，针对这种情况，可以体现扩展的好处：
 * list.swap(list.binarySearch(otherList.max()), list.max());
 */


fun main(args: Array<String>) {
    val l = mutableListOf(1, 2, 3)
    l.swap(0, 2)//方法中的'this'代表这里的'l'
    println(l)
    val lT = mutableListOf("a", "b", "c")
    lT.swapT(0, 1)
    println(lT)

    //会打印，因为扩展方法的调用根据参数中的c决定，对应的类型为ExtensionC
    printFoo(ExtensionD())

    ExtensionF().foo()
    var f: ExtensionF? = null
    print(f.toString())

    //调用companion object 的扩展方法
    ExtensionCompanion.foo()

    //dispatch ~ extension receiver
    DispatchReceiver().caller(ExtensionReceiver())

    //关于open和override的扩展方法
    OpenC().caller(OpenD())//OpenD.foo in OpenC
    OpenC1().caller(OpenD())//OpenD.foo in OpenC1
    OpenC().caller(OpenD1())//OpenD.foo in OpenC
}