package days

import utils.*


class Day13 {

    fun solve() {
        var result1 = 0L
        var result2 = 0L
        execFileByLineInGroups(13, 4) { strings ->
            val coeffs = getEquationCoefficients(strings.filterNot { it.isEmpty() })
            result1 += findSolution(coeffs.first, coeffs.second)
            val manipulatedFirst = coeffs.first.copy(third = coeffs.first.third + 10000000000000L)
            val manipulatedSecond = coeffs.second.copy(third = coeffs.second.third + 10000000000000L)
            result2 += findSolution(manipulatedFirst, manipulatedSecond)
        }
        println(result1)
        println(result2)

    }

    private fun getEquationCoefficients(input: List<String>): Pair<Triple<Long, Long, Long>, Triple<Long, Long, Long>> {
        val buttonRegex = Regex("""Button [A-B]: X\+(\d+), Y\+(\d+)""")
        val prizeRegex = Regex("""Prize: X=(\d+), Y=(\d+)""")
        val buttonValues = input.take(2).map { line ->
            buttonRegex.find(line)?.destructured!!
        }
        val prizeMatch = prizeRegex.find(input.last())?.destructured!!

        val (xA, yA) = buttonValues[0]
        val (xB, yB) = buttonValues[1]
        val (xPrize, yPrize) = prizeMatch
        val tripleX = Triple(xA.toLong(), xB.toLong(), xPrize.toLong())
        val tripleY = Triple(yA.toLong(), yB.toLong(), yPrize.toLong())
        return Pair(tripleX, tripleY)
    }

    private fun findSolution(equation1: Triple<Long, Long, Long>, equation2: Triple<Long, Long, Long>): Long {
        val (a1, b1, c1) = equation1
        val (a2, b2, c2) = equation2

        val determinant = a1 * b2 - a2 * b1

        // determinant == 0 -> no solution
        if (determinant == 0L) return 0L

        // Cramer's rule
        val aNumerator = c1 * b2 - c2 * b1
        val bNumerator = a1 * c2 - a2 * c1

        if (aNumerator % determinant != 0L || bNumerator % determinant != 0L) return 0L

        return (aNumerator/determinant) * 3 + (bNumerator/determinant)
    }

}
