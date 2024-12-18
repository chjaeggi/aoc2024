package days

import utils.*

class Day18 {
    private val memory =
        Array(71) { CharArray(71) { '.' } }

    fun solve() {
        val maxBytes = 1024
        var byteCount = 0
        val bytesToCome = mutableListOf<Point2D>()
        execFileByLine(18) { line ->
            val (x, y) = line.split(",").map { it.toInt() }
            if (byteCount < maxBytes) {
                memory[y][x] = '#'
                byteCount++
            } else {
                bytesToCome += Point2D(x, y)
            }
        }

        val start = Point2D(0, 0)
        val end = Point2D(70, 70)

        val nextSteps = ArrayDeque<Point2D>().apply { add(start) }
        val costMap = mutableMapOf<Point2D, Int>()
        costMap[start] = 0
        shortestPath(end = end, cost = costMap, nextSteps = nextSteps)
        costMap[end]?.let {
            println("Number of fields crossed: $it")
        } ?: println("No path found to $end")

        bytesToCome.forEach { byte ->
            nextSteps.clear()
            nextSteps.addLast(start)
            memory[byte.y][byte.x] = '#'
            costMap.clear()
            costMap[start] = 0
            shortestPath(end = end, cost = costMap, nextSteps = nextSteps)
            costMap[end]?.let {
                println("Still a way with length: $it")
            } ?: println("No path found to $end at byte: $byte")
        }
    }

    private fun shortestPath(
        end: Point2D,
        cost: MutableMap<Point2D, Int>,
        nextSteps: ArrayDeque<Point2D>
    ) {
        while (nextSteps.isNotEmpty()) {
            val currentPos = nextSteps.removeFirst()
            if (currentPos == end) break
            for (d in listOf(Direction.N, Direction.E, Direction.S, Direction.W)) {
                val nextPos = currentPos + d
                if (memory.atOrNull(nextPos) != null && memory.at(nextPos) != '#') {
                    if (cost[nextPos] == null) {
                        nextSteps.addLast(nextPos)
                        cost[nextPos] = cost[currentPos]!! + 1
                    }
                }
            }
        }
    }
}