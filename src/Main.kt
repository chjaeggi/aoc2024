import days.*
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        Day25().solve()
    }
    println("-----------------")
    println("Runs in $time ms")
}