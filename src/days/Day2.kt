package days

import utils.execFileByLine
import kotlin.math.abs

class Day2 {
    fun solve() {
        var res1 = 0
        var res2 = 0

        execFileByLine(2) {
            val line = it.split(" ").map { it.toInt() }
            val errorAt = errorAt(line)

            if (errorAt != -1) {
                // dampened
                if (errorAt(line.filterIndexed { index, _ -> index != errorAt }) == -1 ||
                    errorAt(line.filterIndexed { index, _ -> index != (errorAt - 1) }) == -1) res2++
            } else {
                // non-dampened
                res1++
            }
        }
        println(res1)
        println(res1 + res2)
    }

    private fun errorAt(line: List<Int>): Int {
        val isIncreasing = line.last() > line.first()
        var errorAt = -1
        for ((index, number) in line.withIndex()) {
            if (index == 0) continue
            if (isIncreasing) {
                if (number <= line[index - 1] || abs(number - line[index - 1]) > 3) {
                    errorAt = index
                    break
                }
            } else {
                if (number >= line[index - 1] || abs(number - line[index - 1]) > 3) {
                    errorAt = index
                    break
                }
            }
        }
        return errorAt
    }
}