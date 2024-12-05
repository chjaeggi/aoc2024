package days

import utils.execFileByLine

class Day5 {

    fun solve() {
        var res1 = 0
        var res2 = 0

        val pageOrdering = mutableMapOf<Int, List<Int>>()

        execFileByLine(51) {
            val (page, pageAfter) = it.split("|").map { it.toInt() }
            if (page in pageOrdering.keys) {
                pageOrdering[page] = pageOrdering[page]!! + pageAfter
            } else {
                pageOrdering[page] = listOf(pageAfter)
            }
        }

        execFileByLine(52) {
            val update = it.split(",").map { it.toInt() }
            if (isValid(update, pageOrdering)) {
                res1 += update[update.size / 2]
            } else {
                val reordered = reorder(update, pageOrdering)
                res2 += reordered[reordered.size / 2]
            }
        }

        println(res1)
        println(res2)
    }

    private fun reorder(update: List<Int>, pageOrdering: Map<Int, List<Int>>): List<Int> {
        return update.map { pageOrdering[it]?.intersect(update.toSet())?.size to it }
            .sortedByDescending { it.first }.map { it.second }
    }

    private fun isValid(update: List<Int>, pageOrdering: Map<Int, List<Int>>): Boolean {
        for ((index, value) in update.withIndex()) {
            if (index == 0) continue
            val pagesBefore = update.subList(0, index)
            if (pageOrdering[value]?.any { it in pagesBefore } == true) return false
        }
        return true
    }
}