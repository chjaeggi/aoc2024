package days

import utils.execFileByLine

/*
    Hints needed:
    =============

    https://www.reddit.com/r/adventofcode/comments/1hj2odw/comment/m3c7602/
    Priorities in moves:
    - No zig-zag path. So the path must be at most a row move + a column move.
    - When go left, prefer left and then up/down
    - When go right, prefer down/up and then right
    - When the preferred path is blocked by the empty space, use the opposite path (row, column moves flipped)

    https://www.reddit.com/r/adventofcode/comments/1hj2odw/comment/m37cd9b/
    I generally did < before ^ and v and both of those before >
    And also minimized turns before applying either of those rules
    for example from A to 7 I went ^^^<< even though it breaks the previous rule.
    Reason being, because of the empty space, I could not do all of the left before doing
    all of the up, so it was better to go ahead and do all the up and minimize turns,
    because the longer you can stay on the same direction, the nested layers of controls
    get to just keep hitting A, A, A and not moving.

*/

class Day21 {

    fun solve() {
        execFileByLine(21) {

        }
    }


//
//    private fun Char.fromNumerical(from: Char): String {
//        return when (this) {
//            'A' -> {
//                when (from) {
//                    'A' -> "A"
//                    '0' -> ">A"
//                    '1' -> ">>vA"
//                    '2' -> ">vA"
//                    '3' -> "vA"
//                    '4' -> ">>vvA"
//                    '5' -> ">vvA"
//                    '6' -> "vvA"
//                    '7' -> ">>vvvA"
//                    '8' -> ">vvvA"
//                    '9' -> "vvvA"
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '0' -> {
//                when (from) {
//                    'A' -> "<A"
//                    '0' -> "A"
//                    '1' -> ">vA"
//                    '2' -> "vA"
//                    '3' -> "v<A"
//                    '4' -> "^^<A"
//                    '5' -> "^^A"
//                    '6' -> "^^>A"
//                    '7' -> "^^^<A"
//                    '8' -> "^^^A"
//                    '9' -> "^^^>A"
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '1' -> {
//                when (from) {
//                    'A' -> ""
//                    '0' -> ""
//                    '1' -> ""
//                    '2' -> ""
//                    '3' -> ""
//                    '4' -> ""
//                    '5' -> ""
//                    '6' -> ""
//                    '7' -> ""
//                    '8' -> ""
//                    '9' -> ""
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '2' -> {
//                when (from) {
//                    'A' -> ""
//                    '0' -> ""
//                    '1' -> ""
//                    '2' -> ""
//                    '3' -> ""
//                    '4' -> ""
//                    '5' -> ""
//                    '6' -> ""
//                    '7' -> ""
//                    '8' -> ""
//                    '9' -> ""
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '3' -> {
//                when (from) {
//                    'A' -> ""
//                    '0' -> ""
//                    '1' -> ""
//                    '2' -> ""
//                    '3' -> ""
//                    '4' -> ""
//                    '5' -> ""
//                    '6' -> ""
//                    '7' -> ""
//                    '8' -> ""
//                    '9' -> ""
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '4' -> {
//                when (from) {
//                    'A' -> ""
//                    '0' -> ""
//                    '1' -> ""
//                    '2' -> ""
//                    '3' -> ""
//                    '4' -> ""
//                    '5' -> ""
//                    '6' -> ""
//                    '7' -> ""
//                    '8' -> ""
//                    '9' -> ""
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '5' -> {
//                when (from) {
//                    'A' -> ""
//                    '0' -> ""
//                    '1' -> ""
//                    '2' -> ""
//                    '3' -> ""
//                    '4' -> ""
//                    '5' -> ""
//                    '6' -> ""
//                    '7' -> ""
//                    '8' -> ""
//                    '9' -> ""
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '6' -> {
//                when (from) {
//                    'A' -> ""
//                    '0' -> ""
//                    '1' -> ""
//                    '2' -> ""
//                    '3' -> ""
//                    '4' -> ""
//                    '5' -> ""
//                    '6' -> ""
//                    '7' -> ""
//                    '8' -> ""
//                    '9' -> ""
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '7' -> {
//                when (from) {
//                    'A' -> ""
//                    '0' -> ""
//                    '1' -> ""
//                    '2' -> ""
//                    '3' -> ""
//                    '4' -> ""
//                    '5' -> ""
//                    '6' -> ""
//                    '7' -> ""
//                    '8' -> ""
//                    '9' -> ""
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '8' -> {
//                when (from) {
//                    'A' -> ""
//                    '0' -> ""
//                    '1' -> ""
//                    '2' -> ""
//                    '3' -> ""
//                    '4' -> ""
//                    '5' -> ""
//                    '6' -> ""
//                    '7' -> ""
//                    '8' -> ""
//                    '9' -> ""
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '9' -> {
//                when (from) {
//                    'A' -> ""
//                    '0' -> ""
//                    '1' -> ""
//                    '2' -> ""
//                    '3' -> ""
//                    '4' -> ""
//                    '5' -> ""
//                    '6' -> ""
//                    '7' -> ""
//                    '8' -> ""
//                    '9' -> ""
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            else -> throw IllegalStateException("This is not a valid from direction: $this")
//        }
//    }
//
//    private fun Char.fromDirectional(from: Char): String {
//        return when (this) {
//            'A' -> {
//                when (from) {
//                    'A' -> "A"
//                    '^' -> ">A"
//                    'v' -> ">^A"
//                    '<' -> ">>^A"
//                    '>' -> "^A"
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '^' -> {
//                when (from) {
//                    'A' -> ">A"
//                    '^' -> "A"
//                    'v' -> "^A"
//                    '<' -> ">^A"
//                    '>' -> "<^A"
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            'v' -> {
//                when (from) {
//                    'A' -> "v<A"
//                    '^' -> "vA"
//                    'v' -> "A"
//                    '<' -> ">A"
//                    '>' -> "<A"
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '<' -> {
//                when (from) {
//                    'A' -> "v<<A"
//                    '^' -> "v<A"
//                    'v' -> "<A"
//                    '<' -> "A"
//                    '>' -> "<<A"
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            '>' -> {
//                when (from) {
//                    'A' -> "vA"
//                    '^' -> ">vA"
//                    'v' -> ">A"
//                    '<' -> ">>A"
//                    '>' -> "A"
//                    else -> throw IllegalStateException("This is not a valid to direction: $from")
//                }
//            }
//            else -> throw IllegalStateException("This is not a valid from direction: $this")
//        }
//    }
}