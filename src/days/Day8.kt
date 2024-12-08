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
                        antennas[v] = listOf(Point2D(x, y, Direction.N))
                    } else {
                        antennas[v] = antennas[v]!! + Point2D(x, y, Direction.N)
                    }
                }
            }
        }
        antennas.forEach {
            combinations(it.value, 2).forEach {
                var a = it[0]
                var b = it[1]
                if (antennaRoom.inBounds(a + b)) antiNodes += a + b
                if (antennaRoom.inBounds(b + a)) antiNodes += b + a

                resonantAntiNodes += listOf(a, b)
                while (antennaRoom.inBounds(a + b)) {
                    val new = a + b
                    resonantAntiNodes.add(new)
                    a = b
                    b = new
                }
                a = it[0]
                b = it[1]
                while (antennaRoom.inBounds(b + a)) {
                    val new = b + a
                    resonantAntiNodes.add(new)
                    b = a
                    a = new
                }
            }
        }
        println(antiNodes.distinctBy { it.x to it.y }.count())
        println(resonantAntiNodes.distinctBy { it.x to it.y }.count())
    }

}