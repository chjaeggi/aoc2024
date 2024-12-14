package days

import utils.Point2D
import utils.execFileByLine

data class Robot(
    val initial: Point2D,
    val velocity: Point2D,
    val waypoints: List<Point2D>
)

class Day14 {

    private val xMax = 101
    private val yMax = 103

    fun solve() {
        val robots = mutableListOf<Robot>()
        var resQ1 = 0
        var resQ2 = 0
        var resQ3 = 0
        var resQ4 = 0
        var minScore = Int.MAX_VALUE

        execFileByLine(14) {
            robots.add(it.toRobot())
        }

        // TODO refactor ...
        val robotsAfter = robots.simulatePositionsAfter(100).mapValues { (_, value) -> value.size }
        for ((key, value) in robotsAfter) {
            when (key.toQuadrant()) {
                1 -> resQ1 += value
                2 -> resQ2 += value
                3 -> resQ3 += value
                4 -> resQ4 += value
                else -> throw IllegalStateException("Unknown quadrant")
            }
        }
        println(resQ1 * resQ2 * resQ3 * resQ4)

        for (i in 1..10000) { // assume it's enough
            resQ1 = 0
            resQ2 = 0
            resQ3 = 0
            resQ4 = 0
            val r = robots.simulatePositionsAfter(i).mapValues { (_, value) -> value.size }
            for ((key, value) in r) {
                when (key.toQuadrant()) {
                    1 -> resQ1 += value
                    2 -> resQ2 += value
                    3 -> resQ3 += value
                    4 -> resQ4 += value
                    else -> throw IllegalStateException("Unknown quadrant")
                }
            }
            val score = resQ1 * resQ2 * resQ3 * resQ4
            if (minScore > score) {
                println("score: $minScore after $i second")
                minScore = score
                (0..<yMax).forEach { y ->
                    (0..<xMax).forEach { x ->
                        if (r[Point2D(x, y)] != null) {
                            print("X")
                        } else {
                            print(" ")
                        }
                    }
                    println("")
                }
                println("------------------------------------")
            }
        }
    }

    private fun Point2D.toQuadrant(): Int {
        return when {
            (this.x < (xMax / 2) && this.y < (yMax / 2)) -> 1
            (this.x > (xMax / 2) && this.y < (yMax / 2)) -> 2
            (this.x < (xMax / 2) && this.y > (yMax / 2)) -> 3
            (this.x > (xMax / 2) && this.y > (yMax / 2)) -> 4
            else -> throw IllegalStateException("Point is not in coordinate system")
        }
    }

    private fun List<Robot>.simulatePositionsAfter(seconds: Int) = this.groupBy {
        it.waypoints[seconds % it.waypoints.size]
    }.filterNot { it.key.x == xMax / 2 || it.key.y == yMax / 2 }


    private fun String.toRobot(): Robot {
        val pv = this.split(" ")
        val initPx = pv.first().split(",").first().replace("p=", "").toInt()
        val initPy = pv.first().split(",").last().toInt()
        val initVx = pv.last().split(",").first().replace("v=", "").toInt()
        val initVy = pv.last().split(",").last().toInt()
        val initPosition = Point2D(initPx, initPy)
        val initVelocity = Point2D(initVx, initVy)
        val waypoints = calculateWaypoints(initPosition, initVelocity)
        return Robot(initPosition, initVelocity, waypoints)
    }

    private fun calculateWaypoints(initP: Point2D, initV: Point2D): List<Point2D> {
        val waypoints = mutableListOf(initP)
        while (true) {
            val possibleX = (waypoints.last().x + initV.x) % xMax
            val possibleY = (waypoints.last().y + initV.y) % yMax
            val x = if (possibleX >= 0) possibleX else possibleX + xMax
            val y = if (possibleY >= 0) possibleY else possibleY + yMax
            val next = Point2D(x, y)
            if (next == initP) {
                break
            }
            waypoints.addLast(next)
        }
        return waypoints
    }
}


