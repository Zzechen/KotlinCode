package others

/**
 * 集合：Lis、Set、Map
 * 不像其他语言，Kotlin中区分了不变和可变的集合（list、set、map）。
 * 精确控制集合的可变性有助于减少bug和更好的api设计
 *
 * 了解可变集合的只读视图（read-only view）和实际不可变集合之间的区别是非常重要的。两者的创建都很容易，但类型系统并没有
 * 差异，所以需要开发者跟踪
 */

/**
 * Kotlin中'List<out T>'类型是个值提供只读操作的接口（size，get等方法）
 * 类似Java，List继承自Collection<T>，Collection<T>继承自Iterable<T>
 * 更改list的方法是在MutableList<T>接口中添加的。
 * 上述模式同样应用在Set<out T>/MutableSet<T>和Map<K,out V>/MutableMap<K,V>上
 */

/**
 * Kotlin中没有专用的符号来创建list和set。
 * 使用标准库提供的方法：listOf(),mutableListOf(),setOf(),mutableSetOf()
 * map可以使用mapOf(a to b,c to d)方式创建
 */

/**
 * note：readOnlyView变量指向相同的list，会跟随原list的变化。
 * 如果对list的引用是只读的变量，可以认为该集合是完全不可变的
 * 可采用如下方式创建
 */
val items = listOf(1, 2, 3)
//目前，listOf()方法时使用一个数组列表实现的，但是将来会在知道不可变的事实的情况下返回有更多内存有效的集合类型
/**
 * note：只读集合是协变的。
 * 在Rectangle继承自Shape的情况下，可以使用List<Rectangle>来接收List<Shape>
 * 上述操作在可变数组时不允许，因为可能引发运行时异常
 */

//有时，调用者在特别的时间需要集合的快照，可
class Controller {
    private val _items = mutableListOf<String>()
    val items: List<String> get() = _items.toList()
}
//toList这个扩展方法只是复制集合中的元素，因此返回的列表能够保证不再改变

fun main(args: Array<String>) {
    //list的基础使用
    val numbers: MutableList<Int> = mutableListOf(1, 2, 3)
    val readOnlyView: List<Int> = numbers
    println(numbers)//print "[1,2,3]"
    numbers.add(4)
    println(readOnlyView) // print "[1,2,3,4]"

    val strings = hashSetOf("a", "b", "c", "d")
    assert(strings.size == 3)

    //下面列举一些list、set的常用的扩展方法;除此还包括排序，打包，折叠，削减。。。
    val items = listOf(1, 2, 3, 4)
    items.first() == 1
    items.last() == 4
    items.filter { it % 2 == 0 } // returns [2,4]
    val rwList = mutableListOf(1, 2, 3)
    rwList.requireNoNulls() //returns [1,2,3]
    if (rwList.none { it > 6 }) println("No items above 6") // 会打印
    val item = rwList.firstOrNull()

    //map使用相同的模式
    val readWriteMap = hashMapOf("foo" to 1, "bar" to 2)
    println(readWriteMap["foo"])//1
    val snapshot = HashMap(readWriteMap)

}