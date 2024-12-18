package days

import kotlin.math.pow
import kotlin.math.truncate

class Day17 {

    private var regA = 30118712
    private var regB = 0
    private var regC = 0
    private var instructionPtr = 0
    private val program = listOf(2, 4, 1, 3, 7, 5, 4, 2, 0, 3, 1, 5, 5, 5, 3, 0)
    private var targetPointer = 0
    private var solutionFound = false

    fun solve() {
        part1()
        part2()
        println()
    }

    private fun part1(breakEarly: Boolean = false) {
        while (instructionPtr < program.lastIndex) {
            val (opcode, operand) = program.next()
            execute(opcode, operand, breakEarly)
        }
    }

    private fun part2() {
        var regAToTest = 0
        while (!solutionFound) {
            regA = ++regAToTest
            regB = 0
            regC = 0
            instructionPtr = 0
            targetPointer = 0
            part1(true)
        }
        println(regAToTest)
    }

    private fun List<Int>.next(): Pair<Int, Int> {
        return this[instructionPtr] to this[instructionPtr + 1]
    }

    private fun Int.toCombo(): Int {
        return when (this) {
            in listOf(0, 1, 2, 3) -> this
            4 -> regA
            5 -> regB
            6 -> regC
            else -> throw IllegalArgumentException("Only 0 to 6 allowed")
        }
    }

    private fun execute(opCode: Int, literalOperand: Int, breakEarly: Boolean = false) {
        when (opCode) {
            0 -> {
                regA /= (2.0.pow(literalOperand.toCombo().toDouble())).toInt()
                instructionPtr += 2
            }

            1 -> {
                regB = regB.xor(literalOperand)
                instructionPtr += 2
            }

            2 -> {
                regB = literalOperand.toCombo() % 8
                instructionPtr += 2
            }

            3 -> {
                if (regA == 0) {
                    instructionPtr += 2
                    return
                }
                instructionPtr = literalOperand
            }

            4 -> {
                regB = regB.xor(regC)
                instructionPtr += 2
            }

            5 -> {
//                print("${literalOperand.toCombo() % 8},")
                if (breakEarly) {
                    val number = literalOperand.toCombo() % 8
                    if (number == program[targetPointer]) {
                        targetPointer++
                        if (targetPointer > program.lastIndex) {
                            solutionFound = true
                            instructionPtr = Int.MAX_VALUE
                            return
                        }
                    } else {
                        instructionPtr = Int.MAX_VALUE
                        return
                    }
                }
                instructionPtr += 2
            }

            6 -> {
                regB =
                    truncate(
                        regA / (Math.pow(
                            2.0,
                            literalOperand.toCombo().toDouble()
                        ))
                    ).toInt()
                instructionPtr += 2
            }

            7 -> {
                regC =
                    truncate(
                        regA / (Math.pow(
                            2.0,
                            literalOperand.toCombo().toDouble()
                        ))
                    ).toInt()
                instructionPtr += 2
            }

            else -> {

            }
        }
    }
}