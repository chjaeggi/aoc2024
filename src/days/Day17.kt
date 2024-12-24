package days


class Day17 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val regA = 30118712L
        val program = listOf(2L, 4L, 1L, 3L, 7L, 5L, 4L, 2L, 0L, 3L, 1L, 5L, 5L, 5L, 3L, 0L)
        val out = runProgram(program, regA)
        println(out)
    }


    private fun part2() {
        // 2, 4, 1, 3, 7, 5, 4, 2, 0, 3, 1, 5, 5, 5, 3, 0
        // last output defines first input of A in octal
        // https://www.reddit.com/r/adventofcode/comments/1hg38ah/comment/m2gyonb/
        // https://pastebin.com/raw/7DC2mf9g
        val program = listOf(2L, 4L, 1L, 3L, 7L, 5L, 4L, 2L, 0L, 3L, 1L, 5L, 5L, 5L, 3L, 0L)
        println(findAMatchingOutput(program, program))
    }

    private fun findAMatchingOutput(program: List<Long>, target: List<Long>): Long {
        var aStart = if (target.size == 1) {
            0
        } else {
            8 * findAMatchingOutput(program, target.subList(1, target.size))
        }

        while(true) {
            val out = runProgram(program, aStart)
            if (out == target) {
                println(out)
                break
            }
            aStart++
        }

        return aStart
    }

    private fun Long.toCombo(a: Long, b: Long, c: Long): Long {
        return when (this) {
            in 0L..3L -> this
            4L -> a
            5L -> b
            6L -> c
            else -> throw IllegalArgumentException("Only 0 to 6 allowed")
        }
    }

    private fun runProgram(program: List<Long>, regA: Long): List<Long> {
        var instPtr = 0
        var a = regA
        var b = 0L
        var c = 0L
        val out = mutableListOf<Long>()
        while (instPtr < program.lastIndex) {
            val (opcode, operand) = program[instPtr] to program[instPtr + 1]
            when (opcode) {
                0L -> a = a shr operand.toCombo(a, b, c).toInt()
                1L -> b = b xor operand
                2L -> b = operand.toCombo(a, b, c) % 8L
                3L -> if (a != 0L) {
                    instPtr = operand.toInt()
                    continue
                }
                4L -> b = b xor c
                5L -> out += operand.toCombo(a, b, c) % 8L
                6L -> b = a shr operand.toCombo(a, b, c).toInt()
                7L -> c = a shr operand.toCombo(a, b, c).toInt()
                else -> throw IllegalStateException("Unknown OP Code")
            }
            instPtr += 2
        }
        return out
    }
}