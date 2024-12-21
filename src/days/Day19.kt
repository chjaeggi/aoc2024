package days

import utils.execFileByLine

class Day19 {
    private var towels = listOf<String>()
    private val desired = mutableListOf<String>()

    fun solve() {
        var newLineFound = false

        execFileByLine(19) {
            if (!newLineFound) {
                towels = it.split(", ").map { it.trim() }
                newLineFound = true
            } else {
                if (it.isNotEmpty()) {
                    desired += it
                }
            }
        }

        var sum = 0
        var sum2 = 0L
        for (d in desired) {
            val res = constructArrangement(d)
            if (res > 0) sum++
            sum2 += res
        }
        println("sum: $sum")
        println("sum: $sum2")

    }

    // Thanks, Todd Ginsberg
//    private fun makeDesign(design: String, cache: MutableMap<String, Long> = mutableMapOf()): Long =
//        if (design.isEmpty()) 1
//        else cache.getOrPut(design) {
//            towels.filter { design.startsWith(it) }.sumOf {
//                makeDesign(design.removePrefix(it), cache)
//            }
//        }

    private fun constructArrangement(
        target: String,
        cache: MutableMap<String, Long> = mutableMapOf(),
        combos: Long = 0L,
    ): Long {
        if (target.isEmpty()) {
            return 1
        }
        var c = combos
        if (target in cache) return cache[target]!!
        val filtered = towels.filter { target.startsWith(it) }
        filtered.forEach {
            val next = target.removePrefix(it)
            c += constructArrangement(next, cache, combos)
        }
        cache[target] = c
        return c
    }
}