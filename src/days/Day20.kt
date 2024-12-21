package days

import utils.*
import kotlin.math.abs

class Day20 {

    private val memory =
        Array(numberOfLinesPerFile(20)) { CharArray(numberOfCharsPerLine(20)) }
    private var start: Point2D? = null
    private var end: Point2D? = null

    fun solve() {
        execFileByLineIndexed(20) { y, line ->
            line.forEachIndexed { x, v ->
                memory[y][x] = v
                if (v == 'S') start = Point2D(x, y)
                if (v == 'E') end = Point2D(x, y)
            }
        }

        if (start == null || end == null) {
            throw IllegalStateException("No start or end position found")
        }

        val solution: MutableSet<Point2D> = mutableSetOf()
        findSolution(start!!, solution)
        println(find2picosecondsCheats(solution).count { it.second >= 100 })
        println(findXPicosecondsCheats(solution, 20).count { it.third >= 100 })
    }

    private fun find2picosecondsCheats(solution: Set<Point2D>): List<Pair<Point2D, Int>> {
        val cheatSaves = mutableListOf<Pair<Point2D, Int>>()
        val borders = solution.flatMap { s ->
            listOf(Direction.N, Direction.S, Direction.W, Direction.E).mapNotNull {
                if (memory.atOrNull(s + it) == '#') s to it else null
            }
        }
        borders.forEach {
            if (it.first + it.second + it.second in solution) {
                val index = solution.indexOf(it.first + it.second + it.second)
                cheatSaves += (it.first + it.second + it.second) to (index - solution.indexOf(it.first) - 2)
            }
        }
        return cheatSaves.filter { it.second > 0 }
    }

    private fun findXPicosecondsCheats(
        solution: Set<Point2D>,
        xPicoSeconds: Int
    ): Set<Triple<Point2D, Point2D, Int>> {
        val cheatSaves = mutableSetOf<Triple<Point2D, Point2D, Int>>()
        solution.forEach {
            val yStartOffsetUp = 0
            val yEndOffsetUp = -xPicoSeconds
            for (y in yStartOffsetUp downTo yEndOffsetUp) {
                for (x in yEndOffsetUp - y..yStartOffsetUp) {
                    if (Point2D(it.x + x, it.y + y) in solution) {
                        cheatSaves += Triple(
                            it, Point2D(
                                it.x + x,
                                it.y + y
                            ), solution.indexOf(
                                Point2D(
                                    it.x + x,
                                    it.y + y
                                )
                            ) - solution.indexOf(it) - (abs(x) + abs(y))
                        )
                    }
                    if (Point2D(it.x - x, it.y + y) in solution) {
                        cheatSaves += Triple(
                            it, Point2D(
                                it.x - x,
                                it.y + y
                            ), solution.indexOf(
                                Point2D(
                                    it.x - x,
                                    it.y + y
                                )
                            ) - solution.indexOf(it) - (abs(x) + abs(y))
                        )
                    }
                }
            }
            val yStartOffsetDown = 0
            val yEndOffsetDown = xPicoSeconds
            for (y in yStartOffsetDown..yEndOffsetDown) {
                for (x in (yEndOffsetDown - y) downTo yStartOffsetDown) {
                    if (Point2D(it.x + x, it.y + y) in solution) {
                        cheatSaves += Triple(
                            it, Point2D(
                                it.x + x,
                                it.y + y
                            ), solution.indexOf(
                                Point2D(
                                    it.x + x,
                                    it.y + y
                                )
                            ) - solution.indexOf(it) - (abs(x) + abs(y))
                        )
                    }
                    if (Point2D(it.x - x, it.y + y) in solution) {
                        cheatSaves += Triple(
                            it, Point2D(
                                it.x - x,
                                it.y + y
                            ), solution.indexOf(
                                Point2D(
                                    it.x - x,
                                    it.y + y
                                )
                            ) - solution.indexOf(it) - (abs(x) + abs(y))
                        )
                    }
                }
            }
        }
        return cheatSaves.toSet().filter { it.third > 0 }.toSet()
    }

    private tailrec fun findSolution(
        pos: Point2D,
        solution: MutableSet<Point2D>,
        currentDirection: Direction? = null
    ) {
        solution += pos
        if (pos == end) return

        var next: Point2D? = null
        var cd: Direction? = null
        val nextD = mutableListOf(Direction.N, Direction.E, Direction.S, Direction.W)
        if (currentDirection != null) {
            nextD -= currentDirection.turnCW90().turnCW90()
        }
        for (d in nextD) {
            if (memory.atOrNull(pos + d) == '.' || pos + d == end) {
                next = pos + d
                cd = d
            }
        }
        if (next == null) {
            throw IllegalStateException("No path found")
        }
        findSolution(next, solution, cd)
    }
}