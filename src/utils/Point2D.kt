package utils

data class Point2D(
    val x: Int,
    val y: Int,
) {
   fun n() = this + Direction.N
   fun e() = this + Direction.E
   fun s() = this + Direction.S
   fun w() = this + Direction.W
   fun ne() = this + Direction.NE
   fun se() = this + Direction.SE
   fun sw() = this + Direction.SW
   fun nw() = this + Direction.NW
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