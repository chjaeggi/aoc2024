package days

import utils.execFileByLine

sealed interface Gate {

    data class Xor(
        val left: String,
        val right: String,
    ) : Gate

    data class Or(
        val left: String,
        val right: String,
    ) : Gate

    data class And(
        val left: String,
        val right: String,
    ) : Gate

    data class Input(
        val value: Int,
    ) : Gate
}


class Day24 {
    fun solve() {

        var newLineFound = false
        val values = mutableMapOf<String, Int>()
        val circuit = mutableMapOf<String, Gate>()

        execFileByLine(24) {
            if (it.isEmpty()) {
                newLineFound = true
            } else {
                if (!newLineFound) {
                    val (k, v) = it.split(": ")
                    values[k] = v.toInt()
                } else {
                    val (v, k) = it.split(" -> ")
                    val (a, b, c) = v.split(" ")
                    val gate: Gate = when (b) {
                        "OR" -> Gate.Or(a, c)
                        "AND" -> Gate.And(a, c)
                        "XOR" -> Gate.Xor(a, c)
                        else -> throw IllegalArgumentException("Unknown Gate")
                    }
                    circuit[k] = gate
                }
            }

        }

        println(circuit)

    }
}