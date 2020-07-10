import com.sun.xml.internal.ws.addressing.EndpointReferenceUtil.transform
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.system.measureTimeMillis

@ExperimentalCoroutinesApi
fun main() {

//    flowWithSyncMap()
//    println("------------------------------------------------")
//    flowWithAsyncMap()
//    println("------------------------------------------------")
//    flowWithFilterSync()
//    println("------------------------------------------------")
//    flowWithFilterASync()
//    println("------------------------------------------------")
//    flowWithTake()
//    println("------------------------------------------------")
//    flowWithTakeWhile()
//    println("------------------------------------------------")
//    flowWith3Ops()
//    println("------------------------------------------------")
//    flowWithEmpty()
//    println("------------------------------------------------")
//    flowWithError()
//    println("------------------------------------------------")
    flowWithZipNormal()
    println("------------------------------------------------")
    flowWithZipDelayed()
    println("------------------------------------------------")
    flowWithCombine()
    println("------------------------------------------------")





}


//Map Synchronous

fun flowWithSyncMap() = runBlocking {
    (1..5).asFlow()
            .map { num -> transform(num) }
            .collect {
                println(it)
            }
}

fun transform(num: Int): String = "This is number $num"


//Map Async

fun flowWithAsyncMap() = runBlocking {
    (1..10).asFlow()
            .map { num -> transformAsync(num) }
            .collect {
                println(it)
            }
}

suspend fun transformAsync(num: Int): Boolean {
    delay(500)
    return num % 2 == 0
}

//Filter Synchronous

fun flowWithFilterSync() {
    runBlocking {
        val foodsList = listOf<String>("Momo", "Chowmein", "Pizza", "Sausages", "Dal Bhat", "Burger")
        foodsList.asFlow()
                .filter { s -> s.equals("Momo") }
                .collect { println(it) }
    }
}


//Filter Async

fun flowWithFilterASync() {
    runBlocking {
        val foodsList = listOf<String>("Momo", "Chowmein", "Pizza", "Sausages", "Dal Bhat", "Burger","Chopsuey","Syabaley")
        foodsList.asFlow()
                .filter { food ->
                    delay(1000)
                    food.startsWith("s",ignoreCase = true)
                }
                .collect { println(it) }
    }
}


//Take

fun flowWithTake()= runBlocking {
    (1..50).asFlow().take(25).collect{println(it)}
}

//Take While


fun flowWithTakeWhile()= runBlocking {
    val startTime = System.currentTimeMillis()
    (1..1000).asFlow().takeWhile { System.currentTimeMillis() - startTime < 10 }.collect{println(it)}
}


//DoOnStart     //DoOnComplete     //onEach

@ExperimentalCoroutinesApi
fun flowWith3Ops(){
    runBlocking {
        flow {
            (0..50).forEach {
                emit(it)
            }
        }.onStart { println("Start of flowWith30ps") }
                .onEach {
                    delay(100)
                    println("Emitted value ==> $it")
                }
                .onCompletion { println("End of flowWith30ps")  }
                .collect { println(it) }
    }
}

//onEmpty
@ExperimentalCoroutinesApi
fun flowWithEmpty()= runBlocking {
    val list = Collections.emptyList<String>()
    list.asFlow().onEmpty { println("Empty List")}.collect { println(it) }
}

//onError
fun flowWithError(){
    runBlocking {
        //METHOD ONE
        flowOf(1,2,3,4,5).map { it / 0 }.catch {println(it) }.collect{println(it)}

        println("---------------------------")

        //METHOD TWO
        try {
            flowOf(1,2,3,4,5).map { it / 0  }.collect{println(it)}
        } catch (e: Exception) {
            println(e)
        }


    }
}

//Zip

fun flowWithZipNormal()= runBlocking {
    val flow1 = (1..5).asFlow()
    val flow2 = flowOf("Marc","James","Andre","Jay","Zac")
    flow1.zip(flow2) { a,b -> "$a ===> $b" }.collect { println(it) }
}

fun flowWithZipDelayed()= runBlocking {
    val flow1 = (1..5).asFlow().onEach { delay(100) }
    val flow2 = flowOf("Marc","James","Andre","Jay","Zac").onEach { delay(350) }
    flow1.zip(flow2) { a,b -> "$a ===> $b" }.collect { println(it) }

    //In this case, even when there is difference in emission time
    // zip will wait for the 2nd flow of item to complete before
    //emitting the item from first flow
}


//Combine
fun flowWithCombine()= runBlocking {
    val flow1 = (1..5).asFlow().onEach { delay(100) }
    val flow2 = flowOf("Marc","James","Andre","Jay","Zac").onEach { delay(350) }
    flow1.combine(flow2) { a,b -> "$a ===> $b" }.collect { println(it) }

    //Combine operator combines the most recent value emitted from both the flows
    //Also emits data when there is change in any one of the flows above

}


