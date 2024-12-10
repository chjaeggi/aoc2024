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

fun Direction.turn90degreesCW(): Direction {
    return when (this) {
        Direction.N -> Direction.E
        Direction.E -> Direction.S
        Direction.S -> Direction.W
        Direction.W -> Direction.N
        else -> {
            throw IllegalStateException()
        }
    }
}

operator fun Point2DWithDirection.plus(direction: Direction): Point2DWithDirection {
    return when (direction) {
        Direction.N -> Point2DWithDirection(x, y - 1, Direction.N)
        Direction.E -> Point2DWithDirection(x + 1, y, Direction.E)
        Direction.S -> Point2DWithDirection(x, y + 1, Direction.S)
        Direction.W -> Point2DWithDirection(x - 1, y, Direction.W)
        Direction.NE -> Point2DWithDirection(x + 1, y - 1, Direction.NE)
        Direction.SE -> Point2DWithDirection(x + 1, y + 1, Direction.SE)
        Direction.SW -> Point2DWithDirection(x - 1, y + 1, Direction.SW)
        Direction.NW -> Point2DWithDirection(x - 1, y - 1, Direction.NW)
    }
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

operator fun Point2DWithDirection.plus(p: Point2DWithDirection): Point2DWithDirection = Point2DWithDirection(p.x + x, p.y + y, Direction.N)
operator fun Point2D.plus(p: Point2D): Point2D = Point2D(p.x + x, p.y + y)
fun Point2D.to(p: Point2D): Point2D = Point2D(p.x - x, p.y - y)

inline fun <reified T> Array<T>.inBounds(p: Point2DWithDirection): Boolean {
    return when (T::class) {
        CharArray::class -> p.y in indices && p.x in (this[0] as CharArray).indices
        IntArray::class -> p.y in indices && p.x in (this[0] as IntArray).indices
        else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
    }
}

inline fun <reified T> Array<T>.inBounds(p: Point2D): Boolean {
    return when (T::class) {
        CharArray::class -> p.y in indices && p.x in (this[0] as CharArray).indices
        IntArray::class -> p.y in indices && p.x in (this[0] as IntArray).indices
        else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
    }
}

fun Array<CharArray>.at(p: Point2DWithDirection): Char = this[p.y][p.x]
fun Array<IntArray>.at(p: Point2DWithDirection): Int = this[p.y][p.x]

fun Array<CharArray>.at(p: Point2D) = this[p.y][p.x]
fun Array<IntArray>.at(p: Point2D) = this[p.y][p.x]


fun Array<CharArray>.find(c: Char): Point2D? {
    this.forEachIndexed { y, line ->
        line.forEachIndexed { x, v ->
            if (v == c) return Point2D(x, y)
        }
    }
    return null
}

fun Array<CharArray>.findAll(c: Char): List<Point2D> {
    return this.withIndex().flatMap { (y, line) ->
        line.withIndex().mapNotNull { (x, v) ->
            if (v == c) Point2D(x, y) else null
        }
    }
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