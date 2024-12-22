package days

import utils.execFileByLine

data class PriceChange(
    val a: Int,
    val b: Int,
    val c: Int,
    val d: Int,
)

class Day22 {
    fun solve() {
        val secrets = mutableListOf<Long>()
        execFileByLine(22) {
            secrets += it.toLong()
        }
        var result = 0L
        secrets.forEach {
            var init = it
            for (i in 0..1999) {
                init = init.nextSecret()
                if (i == 1999) result += init
            }
        }
        println(result)

        val pricesPerSecret = mutableMapOf<Long, MutableList<Int>>()
        val pricesChangesPerSecret = mutableMapOf<Long, MutableMap<PriceChange, Int>>()
        secrets.forEach {
            pricesPerSecret[it] = mutableListOf((it % 10).toInt())
            var init = it
            for (i in 0..1999) {
                init = init.nextSecret()
                pricesPerSecret[it]!!.add((init % 10).toInt())
            }
        }
        pricesPerSecret.forEach { (key, value) ->
            value.windowed(5).forEach {
                val change = PriceChange(it[1] - it[0], it[2] - it[1], it[3] - it[2], it[4] - it[3])
                if (key !in pricesChangesPerSecret.keys) {
                    pricesChangesPerSecret[key] =
                        mutableMapOf(change to it[4])
                } else {
                    val alreadyCheckedChanges = change in pricesChangesPerSecret[key]!!
                    if (!alreadyCheckedChanges) {
                        pricesChangesPerSecret[key]!! += change to it[4]
                    }
                }
            }
            pricesChangesPerSecret[key]!!.remove(pricesChangesPerSecret[key]!!.keys.first())
        }
        val bananaMaxMap = mutableMapOf<PriceChange, Long>()
        var i = 1
        for (entry in pricesChangesPerSecret.values) {
            println("Run number: $i of ${pricesChangesPerSecret.size}")
            entry.forEach {
                bananaMaxMap[it.key] = countBananasForSequence(pricesChangesPerSecret, it.key)
            }
            i++
        }

        println(bananaMaxMap.toList()
            .sortedBy { (_, value) -> value }
            .toMap())
    }

    private fun countBananasForSequence(
        pricesChangesPerSecret: Map<Long, Map<PriceChange, Int>>,
        sequence: PriceChange
    ): Long {
        return pricesChangesPerSecret.values.filter { sequence in it.keys }.sumOf { it[sequence]!! }
            .toLong()
    }

    private fun Long.nextSecret(): Long {
        val first = prune(mix(this * 64, this))
        val second = prune(mix((first / 32).toInt().toLong(), first))
        val third = prune(mix(second * 2048, second))
        return third
    }

    private fun mix(value: Long, secret: Long): Long {
        return value xor secret
    }

    private fun prune(secret: Long): Long {
        return secret % 16777216L
    }
}