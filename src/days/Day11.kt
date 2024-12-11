package days

import utils.execFileByLine
import java.lang.Long.max
import java.lang.Long.min
import kotlin.math.*


class Day11 {

    var numberOfIterations = 0
    val calculatedStones = mutableMapOf<Pair<Long, Int>, Long>()
    fun solve() {
        execFileByLine(11) {
            val beforeBlinking = it.split(" ").map { it.toLong() }
            val afterBlinking = mutableListOf<Long>()
            applyRulesInefficient(beforeBlinking, afterBlinking, 0)
            println(afterBlinking.count())
            for (stone in beforeBlinking) {
                calculateStoneSplitting(stone, 0)
            }

            println(numberOfIterations)
        }
    }

    private fun Long.length() = when (this) {
        0L -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }

    private fun calculateStoneSplitting(
        stone: Long,
        currentRun: Int,
    ) {
        if (currentRun == 25) {
            numberOfIterations++
            return
        }
        val after = mutableListOf<Long>()
        when {
            stone == 0L -> {
                after += 1L
            }
            stone.length() % 2 == 0 -> {
                var temp = stone
                var numDigits = 0
                while (temp > 0) {
                    temp /= 10
                    numDigits++
                }

                val midIndex = numDigits / 2
                val divisor = 10.0.pow(midIndex).toLong()

                val first = stone / divisor
                val second = stone % divisor

                after += listOf(first, second)
            }

            else -> {
                after += stone * 2024L
//                calculatedStones[stone to currentRun] = after.first() // cache it
            }
        }

        for (s in after) {
//            if (calculatedStones[s to currentRun] != null) {
//
//            }
            calculateStoneSplitting(s, currentRun + 1)
        }
    }

//    private fun cache(
//        stone: Long,
//        run: Int,
//        after: Long,
//    ): Boolean {
//        if (calculatedStones[stone to run] != null) {
//            calculatedStones[stone to run] = after
//            return true
//        } else {
//            calculatedStones[stone to run] = after
//        }
//        return false
//    }

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