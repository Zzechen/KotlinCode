package classes

/**
 * 泛型：同java一样，Kotlin中可以有类型参数
 */

class Box<T>(t: T) {
    var value = t
}

//通常情况下，需要提供类型参数创建实例
val box: Box<Int> = Box<Int>(1)

//如果参数的类型可以推断出来，可以忽略
val box1 = Box(1)

/**
 * 变化：Variance
 * java中比较棘手的部分是通配符类型，单Kotlin中不存在，相反有两个优势：
 * 1、declaration-site variance
 * 2、类型映射
 */

/**
 * 首先，需要知道java为什么需要这些神秘的通配符：
 * 在Effective java中：使用有界的通配符提升API的灵活性，
 * 在java中泛型类型是不变的，意味着List<String>不是List<Object>的子类，因为下面的情况会存在异常，所以集合不如数组更好
 *  Java
 *  List<String> strs = new ArrayList<String>();
 *  List<Object> objs = strs; // !!! The cause of the upcoming problem sits here. Java prohibits this!
 *  objs.add(1); // Here we put an Integer into a list of Strings
 *  String s = strs.get(0); // !!! ClassCastException: Cannot cast Integer to String
 *  Java禁止了上述情况来保证运行时安全。但是还会存在其他的问题，考虑Collection接口中的allAll()方法，正常的参数为Collection<? extend E>
 *  通配符? extend E意味着参数可以接收E或其子类的集合。因此，可以安全的读取E，单由于不知道E的具体类型，并不能去写。
 *  通过这种限制：Collection<String>是Collection<? extend Object>，换句话说，继承绑定的通配符是协变的
 *
 *  理解这种该方式并不简单的关键是：如果只是从集合中读取，无论使用String或是Object都是可以的；如果只是将元素放入集合中，那么创建Object集合，并放入String是
 *  可以的：在Java中，List<? super String>是List<Object>的子类
 */
/**
 * contravariance ：逆变
 * 只有在List<? super String>中取出的元素可以调用String的方法，如果是List<T>中获取到T，那么就只是Object
 */
/**
 * 泛型唯一保证的问题是类型安全
 */

/**
 * Declaration-site variance
 * Java
 * interface Source<T>{
 *      T nextT();
 * }
 * 如上述接口，将Source<String>的对象存储在Source<Object>中是完全安全的---因为没有包含T的方法参数。但是Java并不知道这个，同样禁止下述代码：
 * void demo(Source<String> strs){
 *      Source<Object> objects = strs;//在java中不允许
 * }
 * 为了解决这个问题，必须声明Source<? extend Object>对象，但是毫无意义的，因为我们可以像以前一样调用所有的方法，但是编译器不知道。
 */

/**
 * 在Kotlin中，可以向编译器解释这种情况。
 * declaration-site variance：
 * 可以对Source中的泛型T注解，保证只是returned(produced)，从不会consumed。这里使用'out'标出
 */
abstract class Source<out T> {
    abstract fun nextT(): T
}

fun demo(strs: Source<String>) {
    val objects: Source<Any> = strs//因为T是out的，所以是可以的
}

/**
 * 生成的规则是：
 * 如果C类的类型参数T声明为了'out'，那它只能出现在C内成员的输出部分，但是在返回C<Base>中可以是其子类C<Derived>
 * 话句话，C类在参数T中是协变的，或者T是个协变类型参数。可以认为C是T的producer
 *
 * 'out'被称作可变注解（variance annotation），由于使用在类型参数定义部分，我们成为declaration-site variance。
 */

/**
 * 与之相对的，Kotlin中提供了'in'（contravariant），可以被消费但不可以被生产，比较好的例子是Comparable类：
 */
abstract class Comparable<in T> {
    abstract fun compareTo(other: T): Int
}

fun demo(x: Comparable<Number>) {
    x.compareTo(1.0) // 1.0 has type Double, which is a subtype of Number
    // Thus, we can assign x to a variable of type Comparable<Double>
    val y: Comparable<Double> = x // OK!
}

/**
 * 类型预测Use-site variance: Type projections
 * 在使用时，将T设置为out是很方便的，并且避免了子类中的麻烦，但是有的类不能仅限于返回T，例如：
 * class Array<T>(val size: Int) {
 *   fun get(index: Int): T { /* ... */ }
 *   fun set(index: Int, value: T) { /* ... */ }
 * }
 * 上面的类中的T既不能是协变，也不能是逆变。会加入不灵活性，考虑下面的方法：
 * 因为在Array<T>中T是不可变的，Array<Int>和Array<Any>都不是互相的子类，但是在copy中可能做了类型不安全的操作，为了向编译器保证，可以在from中使用'out'
 * 我们说from不是简单的array，是受限的：只能调用返回类型参数为T的方法，上述情况中，只能调用get()方法。相对Java实现更轻量
 * 同样可以使用'in'
 */
fun copy(from: Array<out Any>, to: Array<Any>) {
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}
/**
 * Kotlin:'out' --- Java:'? extend Object'
 * Kotlin:'in'  --- Java:'? super Xxxx'
 */

/**
 * Star-projections
 * 有时：并不知道参数的声明，单仍想安全的使用。
 *
 * 1、对于Foo<out T>,T是个协变参数类型，绑定与TUpper，Foo<*> 和Foo<out TUpper>是同等的，这样虽然T是未知的，但是可以安全的从Foo<*>中获取为TUpper，并安全的使用
 * 2、对于Foo<in T>,T是个逆变的参数类型，Foo<*>等同于Foo<in Nothing>,当T为未知类型时，无法安全的写入Foo<*>中
 * 3、对于Foo<T>,T是个不可变的参数类型时，Foo<*>等同于Foo<out TUpper>，获取时Foo<out TUpper>，写入Foo<in Nothing>
 */
/**
 * 如果包含多个泛型时，每个之间是独立存在，例如：
 * interface Function<in T,out U>,那么
 * 1、Function<*,String> ---> Function<in Nothing,String>
 * 2、Function<Int,*>    ---> Function<Int,out Any?>
 * 3、Function<*,*>      ---> Function<in Nothing,out Any?>
 */


/**
 * 泛型方法
 */
fun <T> singletonList(item: T): List<T> {
    return emptyList()
}

//扩展方法
fun <T> T.basicToString(): String {
    return "basicToString"
}

//调用方式
val l = singletonList<Int>(1)

/**
 * 泛型限制
 * 更多的使用是'upper bound',对应Java的'extends'
 * 如：只有Comparable<T>的子类可以替代T
 *
 * 默认的'upper bound'是'Any?',需要多个'upper bound'类型时，可以使用'where'
 */
fun <T : Comparable<T>> sort(list: List<T>) {
    //.....
}
//todo 版本低，无法编译。。。。。
//fun <T> cloneWhenGreater(list: List<T>, threshold: T): List<T>
//        where T : Comparable<T>,
//              T : Cloneable {
//    return list.filter { it > threshold }.map { it.clone() }
//}


fun main(args: Array<String>) {
    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
    copy(ints, any)


    //泛型限制，
//    sort(listOf(1, 2, 3)) //编译不通过。。。。。。。。。。。。
//    sort(listOf(HashMap<Int,String>))//正常无法通过


}














