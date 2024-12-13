package days

import utils.*

data class Plant(
    val pos: Point2D,
    val name: Char,
    val fences: Int,
)
typealias Plot = MutableMap<Plant, List<PlantConnection>>

data class PlantConnection(val from: Plant, val to: Plant)

class Day12 {
    private val garden =
        Array(numberOfLinesPerFile(12)) { CharArray(numberOfCharsPerLine(12)) }
    private val seen = mutableListOf<Plant>()
    private val startingPlants = mutableListOf<Plant>()

    fun solve() {
        execFileByLineIndexed(12) { y, line ->
            line.forEachIndexed { x, v ->
                garden[y][x] = v
            }
        }
        val startPoint = Point2D(0, 0)
        startingPlants += Plant(startPoint, garden.at(startPoint), startPoint.calculateFences())
        val plots = mutableListOf<Plot>()
        while (startingPlants.isNotEmpty()) {
            val plot = mutableMapOf<Plant, List<PlantConnection>>()
            gardening(startingPlants.removeFirst(), plot)
            startingPlants.removeAll { it in seen }
            plots += plot
        }
        println(plots.map { it.map { it.key.fences } }.sumOf { it.size * it.sum() })

        plots.map {
            var corners = 0
            it.keys.map {
                if (it.isCornerPiece()) {

                }
            }
        }

    }

    private fun gardening(plant: Plant, plot: Plot) {
        if (plant in seen) return
        seen += plant
        startingPlants += plant.getNeighboringDifferentPlants()
        val differentPlants = plant.getNeighboringSamePlant()
        if (differentPlants.isEmpty()) {
            plot.add(PlantConnection(plant, plant))
        } else {
            plant.getNeighboringSamePlant().forEach {
                plot.add(PlantConnection(plant, it))
                gardening(it, plot)
            }
        }
    }

    private fun Plant.isCornerPiece(): Boolean {
        return when {
            else -> false
        }
    }

    private fun Plant.getNeighboringSamePlant(): List<Plant> {
        val neighbors = mutableListOf<Plant>()
        if (garden.inBounds(pos.n) && garden.at(pos.n) == garden.at(pos)) neighbors += Plant(
            pos.n,
            garden.at(pos.n),
            pos.n.calculateFences()
        )
        if (garden.inBounds(pos.e) && garden.at(pos.e) == garden.at(pos)) neighbors += Plant(
            pos.e,
            garden.at(pos.e),
            pos.e.calculateFences()
        )
        if (garden.inBounds(pos.s) && garden.at(pos.s) == garden.at(pos)) neighbors += Plant(
            pos.s,
            garden.at(pos.s),
            pos.s.calculateFences()
        )
        if (garden.inBounds(pos.w) && garden.at(pos.w) == garden.at(pos)) neighbors += Plant(
            pos.w,
            garden.at(pos.w),
            pos.w.calculateFences()
        )
        return neighbors
    }

    private fun Plant.getNeighboringDifferentPlants(): List<Plant> {
        val neighbors = mutableListOf<Plant>()
        if (garden.inBounds(pos.n) && garden.at(pos.n) != garden.at(pos)) neighbors += Plant(
            pos.n,
            garden.at(pos.n),
            pos.n.calculateFences()
        )
        if (garden.inBounds(pos.e) && garden.at(pos.e) != garden.at(pos)) neighbors += Plant(
            pos.e,
            garden.at(pos.e),
            pos.e.calculateFences()
        )
        if (garden.inBounds(pos.s) && garden.at(pos.s) != garden.at(pos)) neighbors += Plant(
            pos.s,
            garden.at(pos.s),
            pos.s.calculateFences()
        )
        if (garden.inBounds(pos.w) && garden.at(pos.w) != garden.at(pos)) neighbors += Plant(
            pos.w,
            garden.at(pos.w),
            pos.w.calculateFences()
        )
        return neighbors
    }

    private fun Point2D.calculateFences(): Int {
        var res = 4
        if (garden.inBounds(n) && garden.at(n) == garden.at(this)) res--
        if (garden.inBounds(e) && garden.at(e) == garden.at(this)) res--
        if (garden.inBounds(s) && garden.at(s) == garden.at(this)) res--
        if (garden.inBounds(w) && garden.at(w) == garden.at(this)) res--
        return res
    }

    private fun MutableMap<Plant, List<PlantConnection>>.add(connection: PlantConnection) {
        val fromPlantConnections = this[connection.from] ?: emptyList()
        this[connection.from] = fromPlantConnections + connection
        val toPlantConnections = this[connection.to] ?: emptyList()
        this[connection.to] = toPlantConnections + connection.invert()
    }

    private fun PlantConnection.invert() = copy(from = to, to = from)


}
