package days


class Day17 {
    private var regA = 30118712UL
    private var regB = 0UL
    private var regC = 0UL
    private val program = listOf(2, 4, 1, 3, 7, 5, 4, 2, 0, 3, 1, 5, 5, 5, 3, 0)

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val outCode = mutableListOf<ULong>()
        runProgram(outCode)
        println(outCode)
    }


    private fun part2() {
        // 2, 4, 1, 3, 7, 5, 4, 2, 0, 3, 1, 5, 5, 5, 3, 0
        // last output defines first input of A in octal
        // https://www.reddit.com/r/adventofcode/comments/1hg38ah/comment/m2gyonb/
        // 236555995274861UL should be the solution :(
    }

    private fun Int.toCombo(): ULong {
        return when (this) {
            in listOf(0, 1, 2, 3) -> this.toULong()
            4 -> regA
            5 -> regB
            6 -> regC
            else -> throw IllegalArgumentException("Only 0 to 6 allowed")
        }
    }

    private fun runProgram(
        result: MutableList<ULong> = mutableListOf()
    ) {
        var instPtr = 0
        while (instPtr < program.lastIndex) {
            val (opcode, operand) = program[instPtr] to program[instPtr + 1]
            when (opcode) {
                0 -> regA = regA shr operand.toCombo().toInt()
                1 -> regB = regB xor operand.toULong()
                2 -> regB = operand.toCombo() % 8UL
                3 -> if (regA != 0UL) {
                    instPtr = operand
                    continue
                }
                4 -> regB = regB xor regC
                5 -> result += (operand.toCombo() % 8UL)
                6 -> regB = regA shr operand.toCombo().toInt()
                7 -> regC = regA shr operand.toCombo().toInt()
                else -> throw IllegalStateException("Unknown OP Code")
            }
            instPtr += 2
        }
    }
}