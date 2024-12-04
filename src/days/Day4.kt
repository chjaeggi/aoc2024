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
        if (wordSearch.inBounds(p + direction)) {
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
        if (wordSearch.inBounds(p.ne()) &&
            wordSearch.inBounds(p.se()) &&
            wordSearch.inBounds(p.sw()) &&
            wordSearch.inBounds(p.nw())
        ) {
            return (wordSearch.at(p.ne()) == 'M' && wordSearch.at(p.sw()) == 'S' ||
                    wordSearch.at(p.ne()) == 'S' && wordSearch.at(p.sw()) == 'M') &&
                    (wordSearch.at(p.nw()) == 'M' && wordSearch.at(p.se()) == 'S' ||
                            wordSearch.at(p.nw()) == 'S' && wordSearch.at(p.se()) == 'M')
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