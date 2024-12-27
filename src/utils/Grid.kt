package utils

data class Point2D(
    val x: Int,
    val y: Int,
) {
    val n get() = this + Direction.N
    val e get() = this + Direction.E
    val s get() = this + Direction.S
    val w get() = this + Direction.W
    val ne get() = this + Direction.NE
    val se get() = this + Direction.SE
    val sw get() = this + Direction.SW
    val nw get() = this + Direction.NW
}

data class Point2DWithDirection(
    val p: Point2D,
    val d: Direction, // signifies the direction in which the point was entered e.g. Direction.N == from Direction.S below the coordinates
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

operator fun Point2D.minus(direction: Direction): Point2D {
    return when (direction) {
        Direction.N -> this + Direction.S
        Direction.E -> this + Direction.W
        Direction.S -> this + Direction.N
        Direction.W -> this + Direction.E
        Direction.NE -> this + Direction.SW
        Direction.SE -> this + Direction.NW
        Direction.SW -> this + Direction.NE
        Direction.NW -> this + Direction.SE
    }
}

fun Direction.turnCW90(): Direction {
    return when (this) {
        Direction.N -> Direction.E
        Direction.E -> Direction.S
        Direction.S -> Direction.W
        Direction.W -> Direction.N
        Direction.NE -> Direction.SE
        Direction.SE -> Direction.SW
        Direction.SW -> Direction.NW
        Direction.NW -> Direction.NE
    }
}

fun Direction.turnCCW90(): Direction {
    return this.turnCW90().turnCW90().turnCW90()
}

operator fun Point2D.plus(other: Point2D): Point2D = Point2D(other.x + x, other.y + y)
operator fun Point2D.minus(other: Point2D): Point2D = Point2D(x - other.x, y - other.y)

inline fun <reified T> Array<T>.inBounds(p: Point2D): Boolean {
    return when (T::class) {
        CharArray::class -> p.y in indices && p.x in (this[0] as CharArray).indices
        IntArray::class -> p.y in indices && p.x in (this[0] as IntArray).indices
        else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
    }
}

fun Array<CharArray>.at(p: Point2D) = this[p.y][p.x]
fun Array<CharArray>.atOrNull(p: Point2D) = if (inBounds(p)) this[p.y][p.x] else null
fun Array<IntArray>.at(p: Point2D) = this[p.y][p.x]
fun Array<IntArray>.atOrNull(p: Point2D) = if (inBounds(p)) this[p.y][p.x] else null