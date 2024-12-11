package days

import utils.execFileByLine


class Day11 {

    fun solve() {
        execFileByLine(11) {
            val beforeBlinking = it.split(" ").map { it.toLong() }
            val afterBlinking = mutableListOf<Long>()
            applyRules(beforeBlinking, afterBlinking, 0)
            println(afterBlinking.count())
        }
    }

    private tailrec fun applyRules(
        beforeBlinking: List<Long>,
        afterBlinking: MutableList<Long>,
        currentBlink: Int,
    ): List<Long> {
        if (currentBlink == 75) {
            return beforeBlinking
        }
        println(currentBlink)
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
        return applyRules(afterBlinking, afterBlinking, currentBlink + 1)
    }
}