package classes

import jdk.nashorn.internal.ir.annotations.Ignore

/**
 * Kotlin内的类具有属性，'var'修饰可变，'val'修饰不可变，像java一样使用名称引用即可
 *
 */
class Address {
    var name: String = ""
    var street: String = ""
    var city: String = ""
    var state: String = ""
    var zip: String = ""
}

fun copyAddress(address: Address) {
    val result = Address()
    result.name = address.name
    result.street = address.street
    result.city = address.city
    result.state = address.state
    result.zip = address.zip
}

/**
 * Kotlin中的属性的完整定义为：
 * var <propertyName>[:<propertyType>] [= <property_initializer>]
 *     [<getter>]
 *     [<setter>]
 * 其中：
 * setter/getter是可选的，如果属性的类型可以通过initializer推断出来，类型也是可选的
 */
class Sample {
    var size: Int = 1
    //错误，需要明确的初始化
    //var allByDefault:Int?
    //Int类型，默认实现setter/getter
    var initialized = 1
    /**
     * 只读属性完整定义和可变的有个不同点：不允许setter方法
     *
     */
    //Int类型，有getter实现，需要在构造器中初始化
    //val simple:Int?
    //Int类型，有getter实现
    val inferredType = 1
    /**
     * 自定义的setter/getter，类似字典方法，并将方法声明在属性的右边
     */
    //getter
    val isEmpty: Boolean
        get() = this.size == 0
    //setter
    var other: String = ""
    var stringRepresentation: String
        get() = this.toString()
        set(value) {//为了方便，参数为'value'，可以任意修改
            this.other = value
        }
    //可以不修改原有set/get方法，而改变作用域或加注解
    var setterVisibility: String = "abc"
        private set
    var setterWithAnnotation: Any? = null
        @Ignore set
}

/**
 * Kotlin中没有字段（Filed），为了一些必要场景的使用，Kotlin提供了可以使用filed访问的 automatic backing field
 * 个人理解L:在setter中，防止为对应属性赋值时引发的无限递归问题（赋值会调用setter方法）
 */
class BackingFiled {
    var size = 1
    //直接给backing filed赋值,'field'只能在属性赋值方法使用
    var counter = 0
        set(value) {
            if (value > 0) {
                //如果使用属性直接赋值，会进入无限递归中
                //counter = value
                field = value
                println(value)
            }
        }
    //如果属性使用
}

/**
 * 编译时常量：const
 * 要求：
 * 1. object的顶级或者成员
 * 2. 使用基础类型或者String初始化（指定类型）
 * 3. 无自定义getter方法
 */
//在注解中使用
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"

@Deprecated(SUBSYSTEM_DEPRECATED)
fun foo() {
}

/**
 * 懒加载属性和变量
 * 通常情况，声明类型的变量必须在构造器中初始化，但是在大多数情况，这种格式不方便的。对于注入或者单元测试，无法在构造器中初始化
 * 对于上述情况，可以使用'lateinit'关键字定义属性
 */


public class MyTest {
    //因为未依赖相关库，无法编译、、、
    lateinit var subject: BackingFiled

//    @SetUp fun setup() {
//        subject = TestSubject()
//    }

//    @Test fun test() {
//        subject.method()  // dereference directly
//    }
    /**
     * 此方式可以使用在用'var'声明在class内部的属性（不可以声明在主构造器中，并且不可以自定义setter/getter）；
     * Kotlin1.2后，可以修饰顶级属性和局部变量；
     * 属性或变量的类型必须是non-null，并且不能是基础类型
     */
}

fun main(args: Array<String>) {
    var backingField = BackingFiled()
    println(backingField.counter)

    foo()
    //子Kotlin1.2后，可以使用'.isInitialized'检查是否初始化
    var test = MyTest()
//    if(test::subject.isInitialized){
//        println(test.subject)
//    }
}