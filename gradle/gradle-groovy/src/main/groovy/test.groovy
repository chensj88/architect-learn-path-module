// 介绍Groory编程语言
println("Hello Groovy!");
// Groovy 可以省略冒号
println("Hello Groovy!")
//Groovy 可以省略括号
println "Hello Groovy!"
// 定义变量
// def 是弱类型的，groovy会自动根据情况来给变量赋予对应的类型
def i = 18
println i

// 定义集合
def list = ["a","b"]
// 向集合中添加元素
list << 'c'
// 取出集合中第三个元素
println(list.get(2))


// 定义map
def map = ['k1':'aaaa','k2':'bbbb']
// 向map中添加一个k3
map.k3 = 'cccc'
// 打印k3的值
println map.get('k3')


// groovy 闭包

// 什么是闭包？ 闭包就是一段代码块。
//在gradle中，我们主要把闭包当参数来使用

// 定义一个闭包
def b1 = {
    println 'hello b1'
}

// 定义使用闭包作为参数的方法
def method1(Closure closure){
    // 执行闭包
    closure()
}


// 调用方法method
method1(b1)

// 定义一个需要传入参数的闭包
def b2 = {
    v ->println "hello ${v}"
}


// 定义使用闭包作为参数的方法
def method2(Closure closure,String a){
    // 执行闭包
    closure(a)
}

method2(b2,"aaa")
