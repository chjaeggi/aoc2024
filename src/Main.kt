import days.*
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        Day10().solve()
    }
    println("-----------------")
    println("Runs in $time ms")
}