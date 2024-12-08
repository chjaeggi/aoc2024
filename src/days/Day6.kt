package days

import utils.*

class Day6 {

    private val initialPatrolRoute = mutableListOf<Point2D>()
    private val createdObstacles = mutableListOf<Point2D>()
    fun solve() {
        val patrolRoom =
            Array(numberOfLinesPerFile(6)) { CharArray(numberOfCharsPerLine(6)) }
        execFileByLineIndexed(6) { index, line ->
            line.forEachIndexed { i, v ->
                patrolRoom[index][i] = v
            }
        }
        val start = patrolRoom.find('^')?.copy(d = Direction.N)
        start?.let {
            patrolAround(start, patrolRoom)
        }
        println(initialPatrolRoute.distinctBy { listOf(it.x, it.y) }.count())
        createObstaclesOnRoute(initialPatrolRoute)
        println(createdObstacles.distinctBy { listOf(it.x, it.y) }.count())
    }

    private tailrec fun patrolAround(
        pos: Point2D,
        room: Array<CharArray>,
    ) {
        var current = pos
        initialPatrolRoute += current
        if (!room.inBounds(current.next)) {
            return
        }
        while (room.obstacleAt(current.next)) {
            current = current.copy(d = turn90degrees(current.d))
        }
        patrolAround(current.next, room)
    }

    private tailrec fun findLoops(
        pos: Point2D,
        visited: MutableList<Point2D>,
        room: Array<CharArray>,
    ): Boolean {
        var current = pos

        if (current in visited) {
            return true
        }
        visited += current

        if (!room.inBounds(current.next)) {
            return false
        }

        while (room.obstacleAt(current.next)) {
            current = current.copy(d = turn90degrees(current.d))
        }

        return findLoops(current.next, visited, room)
    }

    private fun createObstaclesOnRoute(route: List<Point2D>) {
        route.forEachIndexed { index, it ->
            if (index > 0) {
                val clonedPatrolRoom =
                    Array(numberOfLinesPerFile(6)) { CharArray(numberOfCharsPerLine(6)) }
                execFileByLineIndexed(6) { y, line ->
                    line.forEachIndexed { x, v ->
                        clonedPatrolRoom[y][x] = v
                    }
                }
                clonedPatrolRoom[it.y][it.x] = '#'
                val loopList = mutableListOf<Point2D>()
                if (findLoops(route[0], loopList, clonedPatrolRoom)) {
                    createdObstacles += it
                }
            }
        }
    }

    private fun Array<CharArray>.obstacleAt(p: Point2D) = at(p) == '#'

    private fun turn90degrees(direction: Direction): Direction {
        return when (direction) {
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