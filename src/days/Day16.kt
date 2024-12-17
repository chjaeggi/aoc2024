package days

import utils.*
import java.util.*
import kotlin.collections.ArrayDeque


private typealias Tracks = Map<DirectedPoint2D, List<Step>>

private data class DirectedPoint2D(
    val pos: Point2D,
    val dir: Direction,
)

private data class Step(
    val from: DirectedPoint2D,
    val to: DirectedPoint2D,
    val cost: Int
)

private data class Race(val step: Step, val totalCost: Int, val stepsAlong: List<DirectedPoint2D>) :
    Comparable<Race> {
    override fun compareTo(other: Race): Int {
        val comparison = totalCost - other.totalCost
        return when {
            comparison > 0 -> 1
            comparison < 0 -> -1
            else -> 0
        }
    }
}

enum class EdgeType {
    Directed, Undirected
}

class Day16 {

    private val raceMap =
        Array(numberOfLinesPerFile(16)) { CharArray(numberOfCharsPerLine(16)) }

    fun solve() {

        var start: Point2D? = null
        var end: Point2D? = null

        execFileByLineIndexed(16) { y, line ->
            line.forEachIndexed { x, v ->
                raceMap[y][x] = v
                if (v == 'S') start = Point2D(x, y)
                if (v == 'E') end = Point2D(x, y)
            }
        }
        val tracks = mutableMapOf<DirectedPoint2D, List<Step>>()
        start?.let {
            val toCheck = ArrayDeque<DirectedPoint2D>()
            val seen = mutableSetOf<DirectedPoint2D>()
            toCheck.addLast(DirectedPoint2D(it, Direction.E))
            while (toCheck.isNotEmpty()) {
                val next = toCheck.removeFirst()
                exploreTracks(next, toCheck, seen, tracks)
            }
            val dijkstraStart = it
            val dijkstraDest = end ?: throw IllegalStateException("No Endpoint found in maze")
            val costs = tracks.cheapestCostsFrom(DirectedPoint2D(it, Direction.E))
            var min = Int.MAX_VALUE
            for (d in listOf(Direction.N, Direction.E, Direction.S, Direction.W)) {
                costs[DirectedPoint2D(dijkstraDest, d)]?.let {
                    if (min > it) {
                        min = it
                    }
                }
            }
            println("The best race from $dijkstraStart to $dijkstraDest costs: $min")
//            val all = tracks.getAllPathsInCheapestFor(DirectedPoint2D(it, Direction.E))
//            println("all: $all")
        } ?: println("No start field found")
    }

    private fun exploreTracks(
        dp: DirectedPoint2D,
        toCheck: ArrayDeque<DirectedPoint2D>,
        seen: MutableSet<DirectedPoint2D>,
        tracks: MutableMap<DirectedPoint2D, List<Step>>
    ) {
        for (d in listOf(dp.dir, dp.dir.turnCW90(), dp.dir.turnCCW90())) {
            if (raceMap.at(dp.pos + d) == '#') continue
            if (dp.dir == d.turnCW90().turnCW90()) continue
            if (DirectedPoint2D(dp.pos, d) in seen) continue
            tracks.add(
                EdgeType.Directed,
                Step(dp, DirectedPoint2D(dp.pos + d, d), dp.dir.cost(d) + 1)
            )
            if (DirectedPoint2D(dp.pos + d, d) !in toCheck) {
                toCheck.addLast(DirectedPoint2D(dp.pos + d, d))
                seen += dp
                seen += DirectedPoint2D(dp.pos, dp.dir.turnCCW90().turnCW90())
            }
        }
    }

    private fun MutableMap<DirectedPoint2D, List<Step>>.add(type: EdgeType, step: Step) {
        val fromSteps = this[step.from] ?: emptyList()
        this[step.from] = fromSteps + step

        if (type == EdgeType.Undirected) {
            val toSteps = this[step.to] ?: emptyList()
            this[step.to] = toSteps + step.invert()
        }
    }

    private fun Step.invert() = copy(from = to, to = from)

    private fun Tracks.cost(from: DirectedPoint2D, to: DirectedPoint2D) =
        this[from]?.firstOrNull { it.to == to }?.cost

    private fun Direction.cost(next: Direction): Int {
        if (this == next) return 0
        return when (this) {
            in listOf(Direction.N, Direction.S) -> if (next in listOf(
                    Direction.E,
                    Direction.W
                )
            ) 1000 else throw IllegalStateException("Illegal direction detected")

            in listOf(Direction.E, Direction.W) -> if (next in listOf(
                    Direction.N,
                    Direction.S
                )
            ) 1000 else throw IllegalStateException("Illegal direction detected")

            else -> throw IllegalStateException("Illegal direction detected")
        }
    }

    // dijkstra
    private fun Tracks.cheapestCostsFrom(p: DirectedPoint2D): Map<DirectedPoint2D, Int> {
        val queue = PriorityQueue<Race>()
        queue.addAll(this[p]?.map { Race(it, it.cost, emptyList()) }
            ?: emptyList()) // add starting outbounds from source
        val seen = mutableSetOf<DirectedPoint2D>()
        val costPerDestination = mutableMapOf<DirectedPoint2D, Int>()
        while (queue.isNotEmpty()) { // always find the local cheapest path (min heap property by priority queue)
            val currentStep = queue.poll()
//            println("from: ${currentStep.step.from} to: ${currentStep.step.to} with: ${currentStep.totalCost}")
            if (currentStep.step.to !in seen) {
                if (currentStep.step.to != p) costPerDestination[currentStep.step.to] =
                    currentStep.totalCost
                this[currentStep.step.to]?.let { routes ->
                    queue += routes
                        .filterNot { it.to in seen } // prevent racing back
                        .map {
                            Race(it, currentStep.totalCost + it.cost, emptyList())
                        }

                }
                seen += currentStep.step.to
            }
        }
        return costPerDestination
    }

//    private fun Tracks.getAllPathsInCheapestFor(p: DirectedPoint2D): Int {
//        val queue = PriorityQueue<Race>()
//        queue.addAll(this[p]?.map { Race(it, it.cost, listOf(p)) }
//            ?: emptyList()) // add starting outbounds from source
//        val seen = mutableMapOf<DirectedPoint2D, Int>()
//        var costAtGoal: Int? = null
//        val allSpotsInAllPaths: MutableSet<DirectedPoint2D> = mutableSetOf()
//        while (queue.isNotEmpty()) {
//            val currentRace = queue.poll()
//            if (costAtGoal != null && currentRace.totalCost > costAtGoal) {
//                return allSpotsInAllPaths.size
//            } else if (raceMap.at(currentRace.step.from.pos) == 'E') {
//                costAtGoal = currentRace.totalCost
//                allSpotsInAllPaths.addAll(currentRace.stepsAlong)
//            } else if (seen.getOrDefault(
//                    currentRace.step.to,
//                    Int.MAX_VALUE
//                ) >= currentRace.totalCost
//            ) {
//                seen[currentRace.step.to] = currentRace.totalCost
//                this[currentRace.step.to]?.let { routes ->
//                    queue += routes.map {
//                        Race(it, currentRace.totalCost + it.cost, emptyList())
//                            .copy(stepsAlong = currentRace.stepsAlong + it.to)
//                    }
//
//                }
//            }
//        }
//        return allSpotsInAllPaths.size
//    }
}


