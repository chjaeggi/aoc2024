package days

import utils.*
import java.util.*

private data class Location(
    val position: Point2D,
    val facing: Direction,
    val cost: Int,
    val steps: List<Point2D> = emptyList()
)

class Day16 {

    private val raceMap =
        Array(numberOfLinesPerFile(16)) { CharArray(numberOfCharsPerLine(16)) }

    fun solve() {

        var start: Point2D? = null
        var end: Point2D? = null

        execFileByLineIndexed(16) { y, line ->
            line.forEachIndexed { x, v ->
                raceMap[y][x] = v
                if (v == 'S') start = Point2D(x, y)
                if (v == 'E') end = Point2D(x, y)
            }
        }

        if (start == null || end == null) {
            throw IllegalStateException("No start or end position found")
        }
        println(part1(start!!, end!!))
        println(part2(start!!, end!!))
    }

    private fun part1(start: Point2D, end: Point2D): Int {
        val queue =
            PriorityQueue<Location>(compareBy { it.cost }).apply {
                add(
                    Location(
                        start,
                        Direction.E,
                        0,
                        listOf()
                    )
                )
            }
        val seen = mutableSetOf<Pair<Direction, Point2D>>()
        while (queue.isNotEmpty()) {
            val location = queue.poll()
            if (location.facing to location.position in seen) {
                continue
            } else {
                seen += location.facing to location.position
            }
            val pos = location.position
            val cost = location.cost

            if (pos == end) {
                return cost
            }

            val straight = location.facing
            val left = location.facing.turnCCW90()
            val right = location.facing.turnCW90()
            if (raceMap.atOrNull(pos + straight) != '#') {
                queue.add(Location(pos + straight, straight, cost + 1))
            }
            if (raceMap.atOrNull(pos + left) != '#') {
                queue.add(Location(pos + left, left, cost + 1001))
            }
            if (raceMap.atOrNull(pos + right) != '#') {
                queue.add(Location(pos + right, right, cost + 1001))
            }
        }
        throw IllegalStateException("No way to target found")
    }

    private fun part2(start: Point2D, end: Point2D): Int {
        val queue =
            PriorityQueue<Location>(compareBy { it.cost }).apply {
                add(
                    Location(
                        start,
                        Direction.E,
                        0,
                        listOf()
                    )
                )
            }

        // Thanks, ClouddJR
        val paths = mutableSetOf<Point2D>()
        var lowest = Int.MAX_VALUE
        val scores = mutableMapOf<Pair<Point2D, Direction>, Int>().withDefault { Int.MAX_VALUE }

        while (queue.isNotEmpty()) {
            val (pos, dir, score, path) = queue.poll()

            if (score > scores.getValue(pos to dir)) {
                continue
            }
            scores[pos to dir] = score

            if (pos == end) {
                if (score > lowest) break
                paths.addAll(path)
                lowest = score
            }

            val left = dir.turnCCW90()
            val right = dir.turnCW90()

            if (raceMap.atOrNull(pos + dir) != '#') {
                queue.add(Location(pos + dir, dir, score + 1, path + pos))
            }
            if (raceMap.atOrNull(pos + left) != '#') {
                queue.add(Location(pos + left, left, score + 1001, path + pos))
            }
            if (raceMap.atOrNull(pos + right) != '#') {
                queue.add(Location(pos + right, right, score + 1001, path + pos))
            }
        }
        return paths.size + 1
    }
}