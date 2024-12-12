package days

import utils.execFileByLine
import java.lang.Long.sum
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow


class Day11 {

    private val calculatedStones = mutableMapOf<Pair<Long, Int>, Long>()
    fun solve() {
        execFileByLine(11) {
            val beforeBlinking = it.split(" ").map { it.toLong() }
            val afterBlinking = mutableListOf<Long>()
            applyRulesInefficient(beforeBlinking, afterBlinking, 0)
            println(afterBlinking.count())

            println(beforeBlinking.sumOf { blink(it, 75) })
        }
    }

    // Thanks, Todd Ginsberg
    private fun blink(
        stone: Long,
        blinks: Int,
        key: Pair<Long, Int> = stone to blinks
    ): Long =
        when {
            blinks == 0 -> 1L
            key in calculatedStones -> calculatedStones[key]!!
            else -> {
                val result = when {
                    stone == 0L -> blink(1, blinks - 1)
                    stone.length() % 2 == 0 -> {
                        val (first, second) = stone.split()
                        sum(blink(first, blinks - 1), blink(second, blinks - 1))
                    }
                    else -> blink(stone * 2024, blinks - 1)
                }
                calculatedStones[key] = result
                result
            }
        }

    private fun Long.split(): Pair<Long, Long> {
        var temp = this
        var numDigits = 0
        while (temp > 0) {
            temp /= 10
            numDigits++
        }

        val midIndex = numDigits / 2
        val divisor = 10.0.pow(midIndex).toLong()

        val first = this / divisor
        val second = this % divisor
        return Pair(first, second)
    }

    private fun Long.length() = when (this) {
        0L -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }

    private tailrec fun applyRulesInefficient(
        beforeBlinking: List<Long>,
        afterBlinking: MutableList<Long>,
        currentBlink: Int,
    ): List<Long> {
        if (currentBlink == 25) {
            return beforeBlinking
        }
        val after = mutableListOf<Long>()
        for (number in beforeBlinking) {
            when {
                number == 0L -> {
                    after += 1L
                }

                number.toString().length % 2 == 0 -> {
                    val numberArr = number.toString().map { it.digitToInt() }
                    val first =
                        numberArr.subList(0, numberArr.size / 2).joinToString().replace(", ", "")
                            .toLong()
                    val second =
                        numberArr.subList(numberArr.size / 2, numberArr.size).joinToString()
                            .replace(", ", "").toLong()
                    after += listOf(first, second)
                }

                else -> {
                    after += number * 2024L
                }
            }
        }
        afterBlinking.clear()
        afterBlinking += after
        return applyRulesInefficient(afterBlinking, afterBlinking, currentBlink + 1)
    }
}