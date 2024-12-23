package days

import utils.combinations
import utils.execFileByLine
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph

// Thanks, https://www.reddit.com/r/adventofcode/comments/1hkgj5b/comment/m3elpae/
// Thanks, https://www.reddit.com/r/adventofcode/comments/1hkgj5b/comment/m3eamrr/ --> JGraphT

class Day23 {
    private val network = mutableMapOf<String, Set<String>>()
//    private val graph = SimpleGraph<String, DefaultEdge>(DefaultEdge::class.java)

    fun solve() {
        execFileByLine(23) {
            val computers = it.split("-")
            val c1 = computers.first()
            val c2 = computers.last()
            if (c1 in network) {
                network[c1] = network[c1]!! + c2
            } else {
                network[c1] = setOf(c2)
            }
            if (c2 in network) {
                network[c2] = network[c2]!! + c1
            } else {
                network[c2] = setOf(c1)
            }
//            graph.addVertex(c1)
//            graph.addVertex(c2)
//            graph.addEdge(c1, c2)
        }

        val threeGroupGraphs = mutableListOf<Triple<String, String, String>>()
        threeGroupGraphs += network.flatMap { computer ->
            network[computer.key]!!.toList().combinations(2).map { it + computer.key }
                .map { it.sorted() }.map { Triple(it[0], it[1], it[2]) }
        }
        val groups =
            threeGroupGraphs.groupingBy { it }.eachCount().filter { (_, v) -> v >= 2 }
        val tStartingGroups = groups.filter {
            it.key.first.startsWith("t") ||
                    it.key.second.startsWith("t") ||
                    it.key.third.startsWith("t")
        }

        // part 2
        val finalCliques = mutableListOf<List<String>>()
        groups.keys.forEach { triple ->
            val newVertices = mutableSetOf<String>()
            val initList = triple.toList().toMutableSet()
            while(true) {
                if (!checkMoreNodesConnected(initList.toList(), newVertices)) {
                    break
                }
                if (newVertices.all { it in initList }) {
                    break
                }
                initList += newVertices
                newVertices.clear()
            }
            finalCliques += (initList + newVertices).sorted()
        }

        println(tStartingGroups.count())
        println(finalCliques.maxByOrNull { it.size }!!.joinToString(","))
        // println(BronKerboschCliqueFinder(graph).maxBy { it.size }.sorted().joinToString (","))
    }

    private fun checkMoreNodesConnected(
        inList: List<String>,
        newVertices: MutableSet<String>
    ): Boolean {
        for (connection in network) {
            if (inList.all { it in connection.value }) {
                newVertices += connection.key
                break // check vertex for vertex, hence the break
            }
        }
        return newVertices.size > 0
    }

}