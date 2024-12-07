package days

import utils.execFileByLine

class Day7 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val operators = listOf('*', '+')

        var res = 0L
        execFileByLine(7) {
            val target = it.split(":").first().toLong()
            val numbers = it.split(": ").last().split(" ").map { it.toLong() }
            val permutations = generatePermutations(operators, numbers.size - 1)

            for (operations in permutations) {
                var result = numbers[0]
                for ((i, op) in operations.withIndex()) {
                    when (op) {
                        '+' -> result += numbers[i + 1]
                        '*' -> result *= numbers[i + 1]
                    }
                }
                if (target == result) {
                    res += target
                    break
                }
            }
        }
        println(res)
    }

    private fun part2() {
        val operators = listOf('*', '+', '|')

        var res = 0L
        execFileByLine(7) {
            val target = it.split(":").first().toLong()
            val numbers = it.split(": ").last().split(" ").map { it.toLong() }
            val permutations = generatePermutations(operators, numbers.size - 1)

            for (operations in permutations) {
                var result = numbers[0]
                for ((i, op) in operations.withIndex()) {
                    when (op) {
                        '+' -> result += numbers[i + 1]
                        '*' -> result *= numbers[i + 1]
                        '|' -> result = "${result}${numbers[i + 1]}".toLong()
                    }
                }
                if (target == result) {
                    res += target
                    break
                }
            }
        }
        println(res)
    }

    // Thanks, ChatGPT
    private fun generatePermutations(operators: List<Char>, length: Int): List<List<Char>> {
        if (length == 1) return operators.map { listOf(it) }
        return generatePermutations(operators, length - 1).flatMap { prefix ->
            operators.map { operator -> prefix + operator }
        }
    }
}