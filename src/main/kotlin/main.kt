import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

// Function to find the best way to cut a rod of length `n`
// where the rod of length `i` has a cost `price[i-1]`
fun rodCutSimple(price: IntArray, n: Int): Int {
    // base case
    if (n == 0) {
        return 0
    }
    var maxValue = Int.MIN_VALUE

    // one by one, partition the given rod of length `n` into two parts of
    // length (1, n-1), (2, n-2), (3, n-3), … ,(n-1, 1), (n, 0) and
    // take maximum
    for (i in 1..n) {
        // rod of length `i` has a cost `price[i-1]`
        val cost = price[i - 1] + rodCutSimple(price, n - i)
        if (cost > maxValue) {
            maxValue = cost
        }
    }
    return maxValue
}


suspend fun rodCutCouroutine(price: IntArray, n: Int): Int {
    // base case
    if (n == 0) {
        return 0
    }
    var maxValue = Int.MIN_VALUE

    // one by one, partition the given rod of length `n` into two parts of
    // length (1, n-1), (2, n-2), (3, n-3), … ,(n-1, 1), (n, 0) and
    // take maximum
    for (i in 1..n) {
        // rod of length `i` has a cost `price[i-1]`
        val cost = price[i - 1] + rodCutCouroutine(price, n - i)
        if (cost > maxValue) {
            maxValue = cost
        }
    }
    return maxValue
}



fun main()= runBlocking {
    val price = (1..70).map { Random.nextInt(1,100) }.toIntArray()

    // rod length
    val n = 30
    val simple = measureTimeMillis {
        println("Profit is " + rodCutSimple(price, n))
    }
    println("Time for simple = $simple")

    val couroutine = measureTimeMillis {
        println("Profit is " + rodCutCouroutine(price, n))
    }
    println("Time for couroutine = $couroutine")

    if (simple < couroutine) {
        println("Simple is faster")
    } else {
        println("Couroutine is faster")
    }
}

/*
Profit is 2730
Time for simple = 2564
Profit is 2730
Time for couroutine = 11806
Simple is faster
 */