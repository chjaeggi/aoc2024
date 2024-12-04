package utils

data class Point2D(
    val x: Int,
    val y: Int,
) {
    val n get() = this+Direction.N
    val e get() = this+Direction.E
    val s get() = this+Direction.S
    val w get() = this+Direction.W
    val ne get() = this + Direction.NE
    val se get() = this + Direction.SE
    val sw get() = this + Direction.SW
    val nw get() = this + Direction.NW
}

enum class Direction {
    N, E, S, W, NE, SE, SW, NW
}

operator fun Point2D.plus(direction: Direction): Point2D {
    return when (direction) {
        Direction.N -> Point2D(x, y - 1)
        Direction.E -> Point2D(x + 1, y)
        Direction.S -> Point2D(x, y + 1)
        Direction.W -> Point2D(x - 1, y)
        Direction.NE -> Point2D(x + 1, y - 1)
        Direction.SE -> Point2D(x + 1, y + 1)
        Direction.SW -> Point2D(x - 1, y + 1)
        Direction.NW -> Point2D(x - 1, y - 1)
    }
}

fun Array<CharArray>.inBounds(p: Point2D): Boolean {
    return p.y >= 0 && p.y <= this.lastIndex && p.x >= 0 && p.x <= this[0].lastIndex
}

fun Array<CharArray>.at(p: Point2D): Char = this[p.y][p.x]