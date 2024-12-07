package days

import utils.*

class Day6 {
    private val patrolRoom =
        Array(numberOfLinesPerFile(6)) { CharArray(numberOfCharsPerLine(6)) }
    private val initialPatrolRoute = mutableListOf<Point2D>()
    private val createdObstacles = mutableListOf<Point2D>()
    fun solve() {

        execFileByLineIndexed(6) { index, line ->
            line.forEachIndexed { i, v ->
                patrolRoom[index][i] = v
            }
        }
        val start = patrolRoom.find('^')?.copy(d = Direction.N)
        start?.let {
            patrolAround(start, true, null, patrolRoom)
        }
        println(initialPatrolRoute.distinctBy { listOf(it.x, it.y) }.count())
        findLoops()
        println(createdObstacles.distinctBy { listOf(it.x, it.y) }.count())
    }

    private tailrec fun patrolAround(
        pos: Point2D,
        initialPatrol: Boolean,
        visited: MutableList<Point2D>?,
        room: Array<CharArray>,
    ): Boolean {
        var current = pos
        if (initialPatrol) initialPatrolRoute.add(current)
        if (visited != null) {
            if (current in visited) {
                return true
            }
            visited += current
        }

        if (!room.inBounds(current.next)) {
            return false
        }

        if (room.obstacleAhead(current.next)) {
            current = current.copy(d = turn90degrees(current.d))
        }

        return patrolAround(current.next, initialPatrol, visited, room)
    }

    private fun findLoops() {
        initialPatrolRoute.forEachIndexed { index, it ->
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
                    if (patrolAround(initialPatrolRoute[0], false, loopList, clonedPatrolRoom)) {
                        createdObstacles.add(it)
                    }
            }
        }
    }

    private fun Array<CharArray>.obstacleAhead(p: Point2D) = this.at(p) == '#'

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