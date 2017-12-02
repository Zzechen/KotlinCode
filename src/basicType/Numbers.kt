package basicType

/**
 * type and bit width
 * Double : 64
 * Float: 32
 * Long : 64
 * Int : 32
 * Short : 16
 * Byte : 8
 */

fun main(args: Array<String>) {
//-------------------文字常量-----------------------------------------
    //note 不支持8进制
    // 二进制
    val binary = 0b010101
    //十六进制
    val hex = 0x0f
    // 小数
    val deciamls = 123
    //Long 类型
    val L = 123L
    //double 两种表示方法
    val double1 = 123.5
    val double2 = 123.5e10
    //float
    val float1 = 2.3f
    val float2 = 2.3F

    //note 1.1以后支持下划线表示
    val oneMillion = 1_000_000
    val creditCardNumber = 1234_5678_9012_3456L
    val socialSecurityNumber = 99_99_9999L
    val hexBytes = 0xFF_EC_DE_5E
    val bytes = 0b11010010_01101001_10010100_10010010

//-------------------包装-----------------------------------------
    /**
     * 在java中，数字被jvm存储为原始类型，除非我们需要一个可为空的数字引用，或者设计反省的时候。
     * 在下面的情况中，数字会被装箱
     */
    // 1. 使用'==='时，和java的包装类效果相当
    val a: Int = 10000
    println("a ==== a ? " + (a === a)) //will print 'true'
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    println("boxed obj compare use '===' ? " + (boxedA === anotherBoxedA)) // will print 'false'

    //2.使用'=='时，和java的'=='效果相当
    val b: Int = 10000
    val boxedB: Int? = a
    val anotherBoxedB: Int? = a
    println("boxed obj compare use '==' ? " + (boxedB === anotherBoxedB)) // will print 'true'

    //3. 显示转换
    /**
     * 由于不同的表示方式，小类型并不是大类型的子类。因此：下面的代码无法编译（不同类型无法'=='，并且不同类型，无法转化）
     * val i: Int? = 1
     * val l: Long? = a
     * println(i == l)
     *
     * 可以采用如下方变换
     * val i: Int = 1
     * val l: Long = i.toLong()
     *
     * 并且每个类型都支持如下方法：
     * -- toByte()
     * -- toShort()
     * -- toInt()
     * -- toLong()
     * -- toFloat()
     * -- toDouble()
     * -- toChar
     */
    // 4. 运算符会引起自动转换
    val l = 1L + 3 // Long + Int => Long

//-------------------操作符-----------------------------------------
    val x = (1 shl 2) and 0x00ff000
    println("(1 shl 2) and 0x00ff000 is " + x)
    //1. 所有的位运算
    /**
     * -- shl(bits)   - 相当于java的'<<'
     * -- shr(bits)   - 相当于java的'>>'
     * -- ushr（bits)  - 相当于java的'>>>'
     * -- and(bits)   - 相当于java '&'
     * -- or(bits)    - 相当于java '|'
     * -- xor(bits)   - 相当于java '^',按位异或
     */
    println("1 shl 2 is " + (1 shl 2))
    println("4 shr 1 is " + (4 shr 1))
    println("8 ushr 3 is " + (8 ushr 3))
    println("1 and 2 is " + (1 and 2))
    println("1 or 2 is " + (1 or 2))
    println("1 xor 2 is " + (1 xor 2))

    //2. 浮点型数字比较
    /**
     * 当a和b已知为Float，Double或者是他们的包装类时，下述方法均成立，并使用 IEEE 754标准；
     * 当a和b不确定类型时，使用equals和compareTo方法进行比较，并不按照上述标准，会有以下情况：
     * 1. NaN -认为等于自身
     * 2. NaN -认为大于所有其他元素，包括POSITIVE_INFINITY(正无穷)
     * 3. -0.0 认为小于0.0
     */
    //2.1 等于 a == b 和 a != b
    //2.2 比较 a < b,a > b,a <= b, a >= b
    //2.3 范围 a..b,x in a..b,x !in a..b

//-------------------字符 Char-----------------------------------------
    //不可以直接当做数字使用
    /**
     * 不通过编译
     * fun check(c:Char){
     *    if (c == 1){}
     * }
     */
    //明确的转化char为int
    fun decimal(c: Char): Int {
        if (c !in '0'..'9') {
            throw IllegalArgumentException("out of range")
        }
        return c.toInt() - '0'.toInt()//明确转换为数字
    }
    println("decimal char to int :" + decimal('8'))

//-------------------数组 Array-----------------------------------------
    /**
     * Kotlin中的数组使用Array类，包括get/set方法和size属性，和一些其他有用的方法：iterator()......
     */
    // 创建方法：
    // 1. 指定元素：
    val arr1 = arrayOf(1, 2, 3)
    arr1.get(0)
    arr1[0]
    // 2. 指定大小：arrayOfNulls()
    val arr2 = arrayOfNulls<Int>(3)
    // 3. Array构造器：
    val arr3 = Array(5, { i -> (i * i).toString() })

    /**
     * note:和java不同，Kotlin的数组是不可变的，以为着Kotlin不允许定义Array<String> to Array<Any>,会触发运行时异常（可以使用Array<Out Any>)）
     * Kotlin有基本类型的数组，如ByteArray，ShortArray，IntArray...。这些类和Array没有继承关系，但是同样具有同样的方法和属性，并且都有相应的工厂方法
     */
    val intArr: IntArray = intArrayOf(1, 2, 3)
    intArr[0] = intArr[1] + intArr[2]


}