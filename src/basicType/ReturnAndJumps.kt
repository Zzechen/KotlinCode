package basicType

/**
 * 三种方式：
 * 1、return
 * 默认结束最近的外部方法（封闭）或者匿名方法
 * 2、break
 * 终止最近的循环
 * 3、continue
 * 跳过本次后续代码，进入下一次循环
 */
fun main(args: Array<String>) {
    //3中方式都可以用在更大的表达式中
    val s = "" ?: return
    //这些表达式的类型是 Nothing type

// ---------------------------------Break-----------------------------------
    //Kotlin中任何表达式都可以用'label'标记。标题使用'@'签名，包含特征的表单，例如：abc@,fooBar@都是可用的labels。
    //将label放置在表达式前面即可
    //这时，可以使用label操作break或者continue
    a@ for (i in 1..100) {
        for (j in 1..100) {
            println("i is $i;j is $j")
            if (j == 3) {
                //指定结束外层循环
                break@a
            }
        }
    }

// ---------------------------------Return-----------------------------------
    /**
     * 在Kotlin中，使用迭代器，可以实现局部方法、对象表达式、方法的嵌套。
     * 正常的return可以返回到外层方法中，最重要的使用场景是在lambda表达式中返回
     */
    var ints = arrayOf(3, 4, 45, 0, 5, 6)

    fun foo() {
        ints.forEach {
            if (it == 0) return
            println(it)
        }
    }
    foo()
    //上面的foo方法会在return最近的封闭方法结束，如果需要在lambda表达式中返回，需要使用label
    fun fooLabel() {
        ints.forEach lit@ {
            if (it == 0) return@lit
            println("forEach lit label $it")
        }
    }
    fooLabel()

    //上面的fooLabel方法中，只返回lambda表达式。更多的时候，使用含蓄label（lambda外层的方法的同名label）更为方便
    fun fooImplicits() {
        ints.forEach {
            if (it == 0) return@forEach
            println("forEach implicits label $it")
        }
    }
    fooImplicits()
    //另外，可以使用匿名方法代替lambda表达式。return声明只是作用在匿名方法
    fun fooAnonymous() {
        ints.forEach(fun(value: Int) {
            if (value == 0) return
            println("forEach anonymous fun $value")
        })
    }
    fooAnonymous()

    //当存在返回值时，可以使用如下方式，代表在返回1到label为 @a的地方
    //return@a 1
}