package utils

data class Point2D(
    val x: Int,
    val y: Int,
    val d: Direction,
) {
    val n get() = this + Direction.N
    val e get() = this + Direction.E
    val s get() = this + Direction.S
    val w get() = this + Direction.W
    val ne get() = this + Direction.NE
    val se get() = this + Direction.SE
    val sw get() = this + Direction.SW
    val nw get() = this + Direction.NW
    val next get() = this + d
}

enum class Direction {
    N, E, S, W, NE, SE, SW, NW
}

operator fun Point2D.plus(direction: Direction): Point2D {
    return when (direction) {
        Direction.N -> Point2D(x, y - 1, Direction.N)
        Direction.E -> Point2D(x + 1, y, Direction.E)
        Direction.S -> Point2D(x, y + 1, Direction.S)
        Direction.W -> Point2D(x - 1, y, Direction.W)
        Direction.NE -> Point2D(x + 1, y - 1, Direction.NE)
        Direction.SE -> Point2D(x + 1, y + 1, Direction.SE)
        Direction.SW -> Point2D(x - 1, y + 1, Direction.SW)
        Direction.NW -> Point2D(x - 1, y - 1, Direction.NW)
    }
}

operator fun Point2D.plus(p: Point2D): Point2D = Point2D(2 * p.x - x, 2 * p.y - y, Direction.N)

fun Array<CharArray>.inBounds(p: Point2D): Boolean {
    return p.y >= 0 && p.y <= this.lastIndex && p.x >= 0 && p.x <= this[0].lastIndex
}

fun Array<CharArray>.at(p: Point2D): Char = this[p.y][p.x]

fun Array<CharArray>.find(c: Char): Point2D? {
    this.forEachIndexed { y, line ->
        line.forEachIndexed { x, v ->
            if (v == c) return Point2D(x, y, Direction.N)
        }
    }
    return null
}

fun Array<CharArray>.count(c: Char): Int {
    var res = 0
    this.forEachIndexed { y, line ->
        line.forEachIndexed { x, v ->
            if (v == c) res++
        }
    }
    return res
}