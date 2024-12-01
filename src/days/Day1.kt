package days

import utils.execFileByLine
import kotlin.math.abs

class Day1 {
    fun solve() {

        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()
        val leftMap = mutableMapOf<Int, Int>()
        val rightMap = mutableMapOf<Int, Int>()
        var solutionOne = 0
        var solutionTwo = 0

        execFileByLine(1) {
            val lists = it.split("   ")
            val left = lists.first().toString().toInt()
            val right = lists.last().toString().toInt()

            leftList.add(left)
            rightList.add(right)

            countOccurrences(left, leftMap, right, rightMap)
        }

        leftList.sort()
        rightList.sort()
        for ((index, item) in leftList.withIndex()) {
            solutionOne += abs(item - rightList[index])
        }
        println("$solutionOne")

        for (item in leftMap) {
            if (item.key in rightMap.keys) {
                solutionTwo += item.key * rightMap[item.key]!!
            }
        }
        println("$solutionTwo")
    }

    private fun countOccurrences(
        left: Int,
        leftMap: MutableMap<Int, Int>,
        right: Int,
        rightMap: MutableMap<Int, Int>
    ) {
        if (left in leftMap.keys) {
            leftMap[left] = leftMap[left]!! + 1
        } else {
            leftMap[left] = 1
        }
        if (right in rightMap.keys) {
            rightMap[right] = rightMap[right]!! + 1
        } else {
            rightMap[right] = 1
        }
    }
}