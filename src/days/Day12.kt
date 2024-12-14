package days

import utils.*

data class Plant(
    val pos: Point2D,
    val name: Char,
    val fences: Int,
)
// TODO refactor ... We don't need a graph here, a tree is enough
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
        println(plots.sumOf { plot -> plot.keys.sumOf { it.numberCornerPieces() * plot.keys.size } })
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

    private fun Plant.numberCornerPieces(): Int {
        return listOf(Direction.N, Direction.E, Direction.S, Direction.W, Direction.N)
            .zipWithNext()
            .map {
                listOf(
                    garden.atOrNull(this.pos), // current field
                    garden.atOrNull(this.pos + it.first), // N for instance
                    garden.atOrNull(this.pos + it.second), // E for instance
                    garden.atOrNull(this.pos + it.first + it.second), // NE for instance
                )
            }.count { (current, side1, side2, corner) ->
                (current != side1 && current != side2) || (side1 == current && side2 == current && corner != current)
            }
    }

    private fun Plant.getNeighboringSamePlant(): List<Plant> {
        val neighbors = mutableListOf<Plant>()
        listOf(
            Direction.N,
            Direction.E,
            Direction.S,
            Direction.W,
            Direction.NE,
            Direction.SW,
            Direction.SW,
            Direction.NW
        ).forEach { d ->
            if (garden.inBounds(pos + d) && garden.at(pos + d) == garden.at(pos)) {
                neighbors += Plant(pos + d, garden.at(pos + d), (pos + d).calculateFences())
            }
        }
        return neighbors
    }

    private fun Plant.getNeighboringDifferentPlants(): List<Plant> {
        val neighbors = mutableListOf<Plant>()
        listOf(
            Direction.N,
            Direction.E,
            Direction.S,
            Direction.W,
            Direction.NE,
            Direction.SW,
            Direction.SW,
            Direction.NW
        ).forEach { d ->
            if (garden.inBounds(pos + d) && garden.at(pos + d) != garden.at(pos)) {
                neighbors += Plant(pos + d, garden.at(pos + d), (pos + d).calculateFences())
            }
        }
        return neighbors
    }

    private fun Point2D.calculateFences(): Int {
        var res = 4
        listOf(Direction.N, Direction.E, Direction.S, Direction.W).forEach { d ->
            if (garden.inBounds(n) && garden.at(n) == garden.at(this)) res--
        }
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
