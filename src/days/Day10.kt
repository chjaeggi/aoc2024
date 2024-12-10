package days

import utils.*

class Day10 {

    private val topography =
        Array(numberOfLinesPerFile(10)) { IntArray(numberOfCharsPerLine(10)) }

    fun solve() {
        val startPositions = mutableListOf<Point2D>()
        val trails = mutableMapOf<Point2D, List<Point2D>>()
        execFileByLineIndexed(10) { y, line ->
            line.forEachIndexed { x, v ->
                topography[y][x] = v.digitToInt()
                if (v == '0') {
                    startPositions += Point2D(x, y)
                }
            }
        }

        for (start in startPositions) {
            trails[start] = mutableListOf()
            traverse(start, start, 0, trails)

        }
        println(trails.values.flatMap { it.toSet() }.count())
        println(trails.values.sumOf { it.size })
    }

    private fun traverse(
        start: Point2D,
        currentPos: Point2D,
        currentHeight: Int,
        trails: MutableMap<Point2D, List<Point2D>>
    ) {
        if (topography.at(currentPos) == 9) {
            trails[start] = trails[start]!! + currentPos
            return
        }
        val next = nextPositions(currentPos)
        if (next.isNotEmpty()) {
            next.forEach {
                traverse(start, it, currentHeight + 1, trails)
            }
        }
    }

    private fun nextPositions(pos: Point2D): List<Point2D> =
        listOf(Direction.N, Direction.E, Direction.S, Direction.W)
            .map { pos + it }
            .filter { topography.inBounds(it) && topography.at(it) == topography.at(pos) + 1 }

}