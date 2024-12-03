package days

import utils.execFileByLine

class Day3 {
    fun solve() {
        var res1 = 0
        var res2 = 0
        execFileByLine(3) {
            val bounds = mutableListOf(0)
            val instructionPattern = Regex("(do\\(\\))|(don't\\(\\))")
            var isOpen = true
            instructionPattern.findAll(it).forEach {
                if (isOpen) {
                    if (it.value == "don't()") {
                        isOpen = false
                        bounds.addLast(it.range.last)
                    }
                } else {
                    if (it.value == "do()") {
                        isOpen = true
                        bounds.addLast(it.range.last)
                    }
                }
            }
            if (bounds.size %2 != 0) bounds.addLast(it.length)

            val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
            val matches = regex.findAll(it)
            matches.forEach { match ->
                val product = match.groups[1]?.value?.toInt()!! * match.groups[2]?.value?.toInt()!!
                res1 += product
                bounds.chunked(2).forEach {
                    if (match.range.first in it.first()..it.last()) res2 += product
                }
            }
        }
        println(res1)
        println(res2)
    }
}