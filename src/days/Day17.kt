package days

import kotlin.math.pow
import kotlin.math.truncate

class Day17 {

    // 111 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 ==> 7 shl << 45
    // 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 111 ==> 7
    // (4 shl 12).or(7 shl 9).or( 6 shl 6).or( 6 shl 3).or( 1)
    // top most 3 bits determine last program number
    private var regA = 30118712L
    private var regB = 0L
    private var regC = 0L
    private var instructionPtr = 0
    private val program = listOf(2, 4, 1, 3, 7, 5, 4, 2, 0, 3, 1, 5, 5, 5, 3, 0)

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val outCode = mutableListOf<Int>()
        while (instructionPtr < program.lastIndex) {
            val (opcode, operand) = program.next()
            execute(opcode, operand, outCode)
        }
        println(outCode)
    }

    private fun part2() {
        var regARunning = 0L
        val reversedProg = program.reversed()
        var pointer = 0
        var foundSolution = false
        for (number in reversedProg) {
            while (!foundSolution) {
                instructionPtr = 0
                regA = regARunning
                regB = 0L
                regC = 0L
                val outCode = mutableListOf<Int>()
                while (instructionPtr < program.lastIndex) {
                    val (opcode, operand) = program.next()
                    execute(opcode, operand, outCode)
                }
                if (outCode.reversed() == reversedProg.subList(0, pointer+1)) {
//                    println("found $outCode")
                    if (outCode.reversed() == reversedProg) foundSolution = true
                    regARunning = regARunning shl 3
                    pointer++
                } else {
                    regARunning += 1L
                }
            }
        }
    }

    private fun List<Int>.next(): Pair<Int, Int> {
        return this[instructionPtr] to this[instructionPtr + 1]
    }

    private fun Int.toCombo(): Int {
        return when (this) {
            in listOf(0, 1, 2, 3) -> this
            4 -> regA.toInt()
            5 -> regB.toInt()
            6 -> regC.toInt()
            else -> throw IllegalArgumentException("Only 0 to 6 allowed")
        }
    }

    private fun execute(opCode: Int, literalOperand: Int, result: MutableList<Int> = mutableListOf()) {
        when (opCode) {
            0 -> {
                regA = (regA / (2.0.pow(literalOperand.toCombo().toDouble()))).toInt().toLong()
                instructionPtr += 2
            }

            1 -> {
                regB = regB.xor(literalOperand.toLong())
                instructionPtr += 2
            }

            2 -> {
                regB = literalOperand.toCombo().toLong() % 8
                instructionPtr += 2
            }

            3 -> {
                if (regA.toInt() == 0) {
                    instructionPtr += 2
                } else {
                    instructionPtr = literalOperand
                }
            }

            4 -> {
                regB = regB.xor(regC)
                instructionPtr += 2
            }

            5 -> {
//                print("${literalOperand.toCombo() % 8},")
                instructionPtr += 2
                result += literalOperand.toCombo() % 8
//                println(result)
            }

            6 -> {
                regB = (regA / (2.0.pow(literalOperand.toCombo().toDouble()))).toInt().toLong()
                instructionPtr += 2
            }

            7 -> {
                regC = (regA / (2.0.pow(literalOperand.toCombo().toDouble()))).toInt().toLong()
                instructionPtr += 2
            }

            else -> {
                throw IllegalStateException("Unknown OP Code")
            }
        }
    }
}