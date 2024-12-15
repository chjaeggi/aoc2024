package days

import utils.*

class Day15 {

    // part 1 warehouse
    private val warehouse =
        Array(numberOfLinesPerFile(151)) { CharArray(numberOfCharsPerLine(151)) }

    // part 2 warehouse
    private val warehouseWide =
        Array(numberOfLinesPerFile(151)) { CharArray(numberOfCharsPerLine(151) * 2) }

    private val movements = mutableListOf<Direction>()
    fun solve() {
        lateinit var start: Point2D
        lateinit var startWide: Point2D
        execFileByLineIndexed(151) { y, line ->
            line.forEachIndexed { x, v ->
                warehouse[y][x] = v
                if (v == '@') {
                    start = Point2D(x, y)
                }
            }
        }
        execFileByLineIndexed(151) { y, line ->
            line.forEachIndexed { x, v ->
                if (v == '.') {
                    warehouseWide[y][2 * x] = '.'
                    warehouseWide[y][2 * x + 1] = '.'
                }
                if (v == 'O') {
                    warehouseWide[y][2 * x] = '['
                    warehouseWide[y][2 * x + 1] = ']'
                }
                if (v == '#') {
                    warehouseWide[y][2 * x] = '#'
                    warehouseWide[y][2 * x + 1] = '#'
                }
                if (v == '@') {
                    warehouseWide[y][2 * x] = '@'
                    startWide = Point2D(2 * x, y)
                    warehouseWide[y][2 * x + 1] = '.'
                }
            }
        }
        execFileByLine(152) { line ->
            line.forEach {
                movements += it.toDirection()
            }
        }
        moveBoxes(start)
        var sum1 = 0
        warehouse.forEachIndexed { y, line ->
            line.forEachIndexed { x, v ->
                print("$v")
                if (v == 'O') {
                    sum1 += 100 * y + x
                }
            }
            println()
        }
        println(sum1)

        var sum2 = 0
        moveBoxesWide(startWide)
        warehouseWide.forEachIndexed { y, line ->
            line.forEachIndexed { x, v ->
                print("$v")
                if (v == '[') {
                    sum2 += 100 * y + x
                }
            }
            println()
        }
        println(sum2)
    }

    // part 1
    private fun moveBoxes(start: Point2D) {
        var currentPosition = start
        for (direction in movements) {
            if (wayIsBlocked(warehouse, currentPosition, direction)) {
                continue
            }
            if (warehouse.at(currentPosition + direction) == '.') {
                warehouse[currentPosition.y][currentPosition.x] = '.'
                warehouse[(currentPosition + direction).y][(currentPosition + direction).x] = '@'
                currentPosition += direction
                continue
            }
            applyBoxShift(currentPosition, direction)
            currentPosition += direction
        }
    }

    // part 2
    private fun moveBoxesWide(start: Point2D) {
        var currentPosition = start
        for (direction in movements) {
            val visited: MutableList<Pair<Point2D, Point2D>> = mutableListOf() // visited is a list of boxes along the way (DFS) that I need in order to know which one I need to move later on
            if (wayIsBlockedWide(currentPosition, direction, visited)) {
                continue
            }
            if (warehouseWide.at(currentPosition + direction) == '.') {
                warehouseWide[currentPosition.y][currentPosition.x] = '.'
                warehouseWide[(currentPosition + direction).y][(currentPosition + direction).x] =
                    '@'
                currentPosition += direction
                continue
            }
            applyBoxShiftWide(currentPosition, direction, visited)
            currentPosition += direction
        }
    }

    // part 1
    private fun applyBoxShift(currentPosition: Point2D, direction: Direction) {
        val upcomingBoxes = ArrayDeque<Point2D>()
        var testIfBox = currentPosition + direction
        while (warehouse.at(testIfBox) == 'O') {
            upcomingBoxes.addLast(testIfBox)
            testIfBox += direction
        }
        if (warehouse.at(testIfBox) == '#') return
        warehouse[currentPosition.y][currentPosition.x] = '.'
        for ((index, box) in upcomingBoxes.withIndex()) {
            val boxAfterBox = box + direction
            warehouse[boxAfterBox.y][boxAfterBox.x] = 'O'
            if (index == 0) warehouse[box.y][box.x] = '@'
        }
    }

    // part2
    private fun applyBoxShiftWide(
        currentPosition: Point2D,
        direction: Direction,
        visited: MutableList<Pair<Point2D, Point2D>>
    ) {
        // I replace every box from last to first (b/c it's easier to set them in the array this way)
        if (direction in listOf(Direction.N, Direction.S)) {
            visited.reverse()
            visited.forEach {
                warehouseWide[(it.first + direction).y][(it.first + direction).x] = '['
                warehouseWide[(it.second + direction).y][(it.second + direction).x] = ']'

                // overwrite old boxes that were "cut in half" during move
                // so one side of a box will be left behind in the array after a move, so I need to clear them
                if (warehouseWide[(it.first + direction).y][(it.first + direction).x - 1] == '[') {
                    warehouseWide[(it.first + direction).y][(it.first + direction).x - 1] = '.'
                }
                if (warehouseWide[(it.second + direction).y][(it.second + direction).x + 1] == ']') {
                    warehouseWide[(it.second + direction).y][(it.second + direction).x + 1] = '.'
                }
            }
            // do the same logic also for the moved robot
            warehouseWide[(currentPosition + direction).y][(currentPosition + direction).x] = '@'
            warehouseWide[(currentPosition).y][(currentPosition).x] = '.'
            if (warehouseWide[(currentPosition + direction).y][(currentPosition + direction).x + 1] == ']') {
                warehouseWide[(currentPosition + direction).y][(currentPosition + direction).x + 1] =
                    '.'
            }
            if (warehouseWide[(currentPosition + direction).y][(currentPosition + direction).x - 1] == '[') {
                warehouseWide[(currentPosition + direction).y][(currentPosition + direction).x - 1] =
                    '.'
            }
        }
        // those shifts will never create a tree structure, so I can just use the same mechanism as in part 1 slightly improved with a reversed list
        if (direction in listOf(Direction.E, Direction.W)) {
            val upcomingBoxes = ArrayDeque<Pair<Point2D, Point2D>>()
            var testIfBox = currentPosition + direction
            while (warehouseWide.at(testIfBox) == '[' || warehouseWide.at(testIfBox) == ']') {
                if (warehouseWide.at(testIfBox) == '[') {
                    upcomingBoxes.addLast(testIfBox to testIfBox + direction)
                } else {
                    upcomingBoxes.addLast(testIfBox + direction to testIfBox)
                }
                repeat(2) {
                    testIfBox += direction
                }
            }
            if (warehouseWide.at(testIfBox) == '#') return
            upcomingBoxes.reverse()
            upcomingBoxes.forEach {
                warehouseWide[(it.first + direction).y][(it.first + direction).x] = '['
                warehouseWide[(it.second + direction).y][(it.second + direction).x] = ']'
            }
            warehouseWide[(currentPosition + direction).y][(currentPosition + direction).x] = '@'
            warehouseWide[(currentPosition).y][(currentPosition).x] = '.'
        }
    }

    // part 1
    private fun wayIsBlocked(
        warehouse: Array<CharArray>,
        pos: Point2D,
        direction: Direction
    ): Boolean {
        when (direction) {
            Direction.N -> {
                for (y in pos.y downTo 0) {
                    if (warehouse.at(pos.copy(y = y)) == '#') return true
                    if (warehouse.at(pos.copy(y = y)) == '.') return false
                }
                return true
            }

            Direction.E -> {
                for (x in pos.x..warehouse[0].lastIndex) {
                    if (warehouse.at(pos.copy(x = x)) == '#') return true
                    if (warehouse.at(pos.copy(x = x)) == '.') return false
                }
                return true
            }

            Direction.S -> {
                for (y in pos.y..warehouse.lastIndex) {
                    if (warehouse.at(pos.copy(y = y)) == '#') return true
                    if (warehouse.at(pos.copy(y = y)) == '.') return false
                }
                return true
            }

            Direction.W -> {
                for (x in pos.x downTo 0) {
                    if (warehouse.at(pos.copy(x = x)) == '#') return true
                    if (warehouse.at(pos.copy(x = x)) == '.') return false
                }
                return true
            }

            else -> throw IllegalArgumentException("Only N E S W allowed")
        }
    }

    // part 2
    private fun wayIsBlockedWide(
        pos: Point2D,
        direction: Direction,
        visited: MutableList<Pair<Point2D, Point2D>>
    ): Boolean {
        when (direction) {
            in listOf(Direction.E, Direction.W) -> return wayIsBlocked(
                warehouseWide,
                pos,
                direction
            )

            in listOf(Direction.N, Direction.S) -> {
                var boxPoints: Pair<Point2D, Point2D>? = null
                if (warehouseWide.at(pos + direction) == '#') {
                    return true
                }
                if (warehouseWide.at(pos + direction) == '[') {
                    boxPoints = Pair(pos + direction, pos.copy(x = pos.x + 1) + direction)
                }
                if (warehouseWide.at(pos + direction) == ']') {
                    boxPoints = Pair(pos.copy(x = pos.x - 1) + direction, pos + direction)
                }
                boxPoints?.let {
                    return !isThereAWay(boxPoints, direction, visited)
                }
                return false
            }

            else -> throw IllegalArgumentException("Only N E S W allowed")
        }
    }

    // part 2
    private fun isThereAWay(
        boxPoints: Pair<Point2D, Point2D>,
        direction: Direction,
        visited: MutableList<Pair<Point2D, Point2D>>
    ): Boolean {
        if (warehouseWide.at(boxPoints.first) == '.' && warehouseWide.at(boxPoints.second) == '.') return true
        visited += boxPoints
        val nextLeftP = boxPoints.first + direction
        val nextRightP = boxPoints.second + direction
        val nextLeft = warehouseWide.at(nextLeftP)
        val nextRight = warehouseWide.at(nextRightP)
        if (nextLeft == '.' && nextRight == '.') return true
        if (nextLeft == '#' || nextRight == '#') return false

        /* several cases for boxes after the box next
        ---------------------------------------------
        [][]    [][]    [][]    []..    ..[]
        []        []     []      []      []
         @         @      @       @       @
         */
        if (nextLeft == '[') {
            return isThereAWay(
                Pair(nextLeftP, nextRightP),
                direction,
                visited
            )
        } else if (nextLeft == ']' && nextRight == '.') {
            return isThereAWay(
                Pair(Point2D(nextLeftP.x - 1, nextLeftP.y), nextLeftP),
                direction,
                visited
            )
        } else if (nextRight == '[' && nextLeft == '.') {
            return isThereAWay(
                Pair(nextRightP, Point2D(nextRightP.x + 1, nextRightP.y)),
                direction,
                visited
            )
        } else if (nextRight == '[') {
            return isThereAWay(
                Pair(nextLeftP.copy(x = nextLeftP.x - 1), nextLeftP),
                direction,
                visited
            )
                    && isThereAWay(
                Pair(nextRightP, nextRightP.copy(x = nextRightP.x + 1)), direction, visited
            )
        }
        throw IllegalStateException("Unexpected upcoming boxes")
    }

    private fun Char.toDirection(): Direction {
        return when (this) {
            '^' -> Direction.N
            '<' -> Direction.W
            '>' -> Direction.E
            'v' -> Direction.S
            else -> throw IllegalArgumentException("Not a valid direction input")
        }
    }
}


