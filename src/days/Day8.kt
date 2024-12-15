package days

import utils.*

class Day8 {

    private val antennaRoom =
        Array(numberOfLinesPerFile(8)) { CharArray(numberOfCharsPerLine(8)) }

    fun solve() {
        val antiNodes = mutableListOf<Point2D>()
        val resonantAntiNodes = mutableListOf<Point2D>()
        val antennas = mutableMapOf<Char, List<Point2D>>()
        execFileByLineIndexed(8) { y, line ->
            line.forEachIndexed { x, v ->
                antennaRoom[y][x] = v
                if (v != '.') {
                    if (v !in antennas.keys) {
                        antennas[v] = listOf(Point2D(x, y))
                    } else {
                        antennas[v] = antennas[v]!! + Point2D(x, y)
                    }
                }
            }
        }
        antennas.forEach { antenna ->
            combinations(antenna.value, 2).forEach {
                var a = it[0]
                var b = it[1]
                val vectorAB = it[0] - it[1]
                val vectorBA = it[1] - it[0]

                if (antennaRoom.inBounds(a + vectorBA)) antiNodes += a + vectorBA
                if (antennaRoom.inBounds(b + vectorAB)) antiNodes += b + vectorAB

                resonantAntiNodes += listOf(a, b)
                while (antennaRoom.inBounds(a + vectorBA)) {
                    resonantAntiNodes.add(a + vectorBA)
                    a += vectorBA
                }

                while (antennaRoom.inBounds(b + vectorAB)) {
                    resonantAntiNodes.add(b + vectorAB)
                    b += vectorAB
                }
            }
        }
        println(antiNodes.toSet().count())
        println(resonantAntiNodes.toSet().count())
    }

}