package days

import utils.*

class Day4 {

    private fun nextChar(c: Char): Char {
        return when (c) {
            'X' -> 'M'
            'M' -> 'A'
            'A' -> 'S'
            else -> throw IllegalStateException("Invalid char detected")
        }
    }

    private fun findXMASSequence(
        direction: Direction,
        p: Point2D,
        wordSearch: Array<CharArray>,
    ): Boolean {
        if (wordSearch[p.y][p.x] == 'S') return true
        if (inBounds(p + direction, wordSearch)) {
            if (nextChar(wordSearch[p.y][p.x]) == wordSearch[(p + direction).y][(p + direction).x]) {
                return findXMASSequence(direction, p + direction, wordSearch)
            }
        }
        return false
    }

    private fun findMASCross(
        p: Point2D,
        wordSearch: Array<CharArray>,
    ): Boolean {
        if (inBounds(p + Direction.NE, wordSearch) &&
            inBounds(p + Direction.SE, wordSearch) &&
            inBounds(p + Direction.SW, wordSearch) &&
            inBounds(p + Direction.NW, wordSearch)
        ) {
            return (wordSearch[(p + Direction.NE).y][(p + Direction.NE).x] == 'M' && wordSearch[(p + Direction.SW).y][(p + Direction.SW).x] == 'S' ||
                    wordSearch[(p + Direction.NE).y][(p + Direction.NE).x] == 'S' && wordSearch[(p + Direction.SW).y][(p + Direction.SW).x] == 'M') &&
                    (wordSearch[(p + Direction.NW).y][(p + Direction.NW).x] == 'M' && wordSearch[(p + Direction.SE).y][(p + Direction.SE).x] == 'S' ||
                            wordSearch[(p + Direction.NW).y][(p + Direction.NW).x] == 'S' && wordSearch[(p + Direction.SE).y][(p + Direction.SE).x] == 'M')
        }
        return false
    }

    fun solve() {
        var res1 = 0
        var res2 = 0
        val wordSearch =
            Array(numberOfLinesPerFile(4)) { CharArray(numberOfCharsPerLine(4)) }

        execFileByLineIndexed(4) { index, line ->
            line.forEachIndexed { i, v ->
                wordSearch[index][i] = v
            }
        }
        wordSearch.forEachIndexed { y, line ->
            line.forEachIndexed { x, v ->
                if (v == 'X') {
                    Direction.entries.forEach {
                        if (findXMASSequence(it, Point2D(x, y), wordSearch)) res1++
                    }
                }
            }
        }
        wordSearch.forEachIndexed { y, line ->
            line.forEachIndexed { x, v ->
                if (v == 'A') {
                    if (findMASCross(Point2D(x, y), wordSearch)) res2++
                }
            }
        }
        println(res1)
        println(res2)
    }
}