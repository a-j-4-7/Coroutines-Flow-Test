import javafx.application.Application.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

private lateinit var collectionFlow : Flow<Person>
private lateinit var flow1:Flow<Int>
private lateinit var flow2:Flow<Int>
private lateinit var lambdaFlow:Flow<String>

fun main(){

    createFlow1()
    println("---------------------------------------------")
    createFlow2()
    println("---------------------------------------------")
    createLambdaFlow()
    println("---------------------------------------------")
    createFlowFromCollection()
    println("---------------------------------------------")
}

fun getPersonList():List<Person> = listOf(
        Person("Sachet","Bkt"),
        Person("Mira","Ktm"),
        Person("Aj","Lalitpur"),
        Person("Sachet","Bkt"),
        Person("Mira","Ktm"),
        Person("Aj","Lalitpur")
)

fun createFlowFromCollection(){
    collectionFlow = getPersonList().asFlow().onEach { delay(2000) }
    collectCollectionFlow()
}

fun collectCollectionFlow()= runBlocking {
    collectionFlow.collect{
        println(it)
    }
}

fun createFlow1(){
    flow1 = flowOf(1,2,3,4,5,6,7,8,9,10)
    collectFlow1()
}

fun collectFlow1()= runBlocking { flow1.collect { println(it) } }

fun createFlow2(){
    flow2 = (1..10).asFlow()
    collectFlow2()
}

fun collectFlow2()= runBlocking { flow2.collect { println(it) } }

fun createLambdaFlow(){
    lambdaFlow = flow {
        val list = listOf("a","b","c","d","e")
        list.forEach {
            emit(it)
        }
    }

    collectLambdaFlow()
}

fun collectLambdaFlow()= runBlocking {
    lambdaFlow.collect { println(it) }
}






