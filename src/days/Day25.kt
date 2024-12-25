package days

import utils.execFileByLine
import utils.execFileByLineInGroups
import kotlin.math.abs


class Day25{
    fun solve() {
        val keys = mutableSetOf<IntArray>()
        val locks = mutableSetOf<IntArray>()

        execFileByLineInGroups(25, 8) {
            val array = it.filterNot { it.isEmpty() }
            val lock = mutableMapOf<Int, Int>()
            val key = mutableMapOf<Int, Int>()

            if (array.first().contains("#")) {
                // locks
                for (i in 0 .. array.first().lastIndex) {
                    var len = 0
                    for (j in 1..<array.lastIndex) {
                        if (array[j][i] == '#') len++
                    }
                    lock[i] = len
                }
                locks += lock.values.toIntArray()
            } else {
                // keys
                for (i in 0 .. array.first().lastIndex) {
                    var len = 0
                    for (j in 1..<array.lastIndex) {
                        if (array[j][i] == '#') len++
                    }
                    key[i] = len
                }
                keys += key.values.toIntArray()
            }
        }

        var res = 0
        for (lock in locks) {
            for (key in keys) {
                var toTest = 0
                for ((idx, _) in key.withIndex()) {
                    if (key[idx] + lock[idx] <= 5) toTest++
                }
                if (toTest == 5) res++
            }
        }
        println(res)
    }
}