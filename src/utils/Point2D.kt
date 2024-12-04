package utils

data class Point2D(
    val x: Int,
    val y: Int
)

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

fun inBounds(p: Point2D, coords: Array<CharArray>): Boolean {
    return p.y >= 0 && p.y <= coords.lastIndex && p.x >= 0 && p.x <= coords[0].lastIndex
}