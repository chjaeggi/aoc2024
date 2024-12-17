import days.*
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        Day12().solve()
    }
    println("-----------------")
    println("Runs in $time ms")
}