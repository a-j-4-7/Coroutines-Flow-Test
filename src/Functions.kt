fun main() {

    //Higher Order function invocation by passing functions via reference instead of lambdas
//    calculate(2.0,3.0,::sum)
//    displayUserInput(200,::printNumber)

    //Higher Order function invocation by passing lambdas


    calculateWithLambda(10,10,myLambda)

    calculateWithMultilineLamda(8,6,multiLineLambda)
    //OR
    calculateWithLambda(5,5) { a, b -> a * b }

}


 //Lambda (function with no-name)
// Here, function signature is defined inside the curly brackets, which is then assigned to a variable myLambda
val myLambda = { x:Int, y:Int -> x+y}

//Multi-line lambda
val multiLineLambda: (Int, Int) -> Unit = { x:Int, y:Int ->
    val square1 = x*x
    val square2 = y*y
    println("The square of numbers you entered are $square1 , $square2")
}

//Normal Function
fun sum(a:Double,b:Double): Double = a+b

val sumReference: (a: Double, b: Double) -> Double = ::sum

//Lambda Function
val sumLambda = { a:Double, b:Double -> a+b}

//Lambda Declaration

//1. Without type declaration
val squareLambda = {x:Int -> x*x}

//2.With Type Declartion
val newLambda : (String)->Unit = {x -> println(x)}

//3. Simplify Single Param Lambdas with 'it' keyword.Here we just need to declare the body and refer to the param with 'it'
val simplifyLambda : (Int)->Int = {it+it}




fun printNumber(a:Int) = print(a.toString())

fun calculate(a:Double,b:Double,fn : (Double,Double)->Double){
    print(fn(a,b))
}

fun calculateWithLambda(a:Int,b:Int,fn : (Int,Int)->Int){
    val result: Int = fn(a,b)
    println(result)
}

fun calculateWithMultilineLamda(a:Int,b:Int,fn:(Int,Int)->Unit){
    fn(a,b)
}

fun displayUserInput(a:Int,func : (Int) -> Unit){
   func(a)
}