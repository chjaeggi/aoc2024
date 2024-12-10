package days

import utils.*

class Day6 {

    private val initialPatrolRoute = mutableListOf<Point2D>()
    private val createdObstacles = mutableListOf<Point2D>()
    fun solve() {
        var start: Point2D? = null
        val patrolRoom =
            Array(numberOfLinesPerFile(6)) { CharArray(numberOfCharsPerLine(6)) }

        execFileByLineIndexed(6) { y, line ->
            line.forEachIndexed { x, v ->
                patrolRoom[y][x] = v
                if (v == '^') start = Point2D(x, y)
            }

        }
        start?.let {
            patrolAround(Point2D(it.x, it.y), Direction.N, patrolRoom)
        }
        println(initialPatrolRoute.toSet().count())
        createObstaclesOnRoute(initialPatrolRoute, patrolRoom)
        println(createdObstacles.toSet().count())

    }

    private tailrec fun patrolAround(
        pos: Point2D,
        direction: Direction,
        room: Array<CharArray>,
    ) {
        var nextDirection = direction
        initialPatrolRoute += pos
        if (!room.inBounds(pos + nextDirection)) {
            return
        }

        while (room.obstacleAt(pos + nextDirection)) {
            nextDirection = nextDirection.turn90degreesCW()
        }
        patrolAround(pos + nextDirection, nextDirection, room)
    }

    private tailrec fun findLoops(
        pos: Point2D,
        direction: Direction,
        visited: MutableList<Pair<Point2D, Direction>>,
        room: Array<CharArray>,
    ): Boolean {
        var nextDirection = direction
        if (pos to nextDirection in visited) {
            return true
        }
        visited += pos to nextDirection

        if (!room.inBounds(pos + nextDirection)) {
            return false
        }

        while (room.obstacleAt(pos + nextDirection)) {
            nextDirection = nextDirection.turn90degreesCW()
        }

        return findLoops(pos + nextDirection, nextDirection, visited, room)
    }

    private fun createObstaclesOnRoute(route: List<Point2D>, patrolRoom: Array<CharArray>) {
        for ((index, point) in route.withIndex()) {
            if (index > 0) {
                val originalWayPoint = patrolRoom[point.y][point.x]
                patrolRoom[point.y][point.x] = '#'
                val loopList = mutableListOf<Pair<Point2D, Direction>>()
                if (findLoops(route[0], Direction.N, loopList, patrolRoom)) {
                    createdObstacles += point
                }
                patrolRoom[point.y][point.x] = originalWayPoint
            }
        }
    }

    private fun Array<CharArray>.obstacleAt(p: Point2D) = at(p) == '#'

    private fun Direction.turn90degreesCW(): Direction {
        return when (this) {
            Direction.N -> Direction.E
            Direction.E -> Direction.S
            Direction.S -> Direction.W
            Direction.W -> Direction.N
            else -> {
                throw IllegalStateException()
            }
        }
    }
}