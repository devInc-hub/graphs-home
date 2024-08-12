package model.algorithms

import model.graph.DirectedGraph
import model.graph.Graph
import model.graph.UndirectedGraph
import kotlin.math.roundToInt

class HarmonicCentrality<D>(private val graph: Graph<D>) {

    private fun roundTo(number: Double): Double {
        return (number * 10000.0).roundToInt() / 10000.0
    }

    private fun getIndexEdge(getGraph: Graph<D>, vertexId: Int): Double {

        var index: Double = 0.00

        getGraph.vertices.filter { it.key != vertexId }
            .forEach {
                if (Dijkstra(getGraph).findShortestPaths(vertexId, it.key).isNotEmpty()) {
                    index += 1.0 / ((Dijkstra(getGraph).findShortestPaths(vertexId, it.key)).size - 1)
                }
            }

        return roundTo(index / (getGraph.vertices.size - 1))

    }

    fun harmonicCentrality(): HashMap<Int, Double> {

        val centralityIndexes = HashMap<Int, Double>()

        val graphForCentrality: Graph<D> = if (graph is UndirectedGraph<D>) {
            UndirectedGraph<D>()
        } else {
            DirectedGraph<D>()
        }
        graph.vertices.forEach { graphForCentrality.addVertex(it.key, it.value.data) }
        graph.edges.forEach { graphForCentrality.addEdge(it.vertices, 1) }

        for (vertexId in graphForCentrality.vertices.keys) {
            centralityIndexes[vertexId] = getIndexEdge(graphForCentrality, vertexId)
        }

        return centralityIndexes

    }

}

/*import forTests.addVertices
import model.algorithms.HarmonicCentrality
import model.graph.DirectedGraph
import model.graph.UndirectedGraph
import org.junit.jupiter.api.Test
import kotlin.math.roundToInt
import kotlin.test.assertEquals

class HarmonicCentralityTest {

    private var undirGraph = UndirectedGraph<Int>()
    private var dirGraph = DirectedGraph<Int>()
    private var undirCentrality = HarmonicCentrality(undirGraph)
    private var dirCentrality = HarmonicCentrality(dirGraph)
    private fun roundTo(number: Double): Double {
        return (number * 10000.0).roundToInt() / 10000.0
    }

    @Test
    fun `indexes of single graph's vertices must be equal to zero`() {

        addVertices(undirGraph, 3)
        undirGraph.addEdge(1 to 2, 23)

        assertEquals(0.0, (undirCentrality.harmonicCentrality())[3])

    }

    @Test
    fun `the index of the vertex connected with all vertices of the graph must be equal to 1`() {

        addVertices(undirGraph, 3)
        undirGraph.addEdge(1 to 2, 23)
        undirGraph.addEdge(1 to 3, 23)

        assertEquals(1.0, (undirCentrality.harmonicCentrality())[1])

    }

    @Test
    fun `result of function for simple graph must be correct`() {

        addVertices(undirGraph, 4)
        undirGraph.run {
            addEdge(1 to 2, null)
            addEdge(2 to 3, null)
            addEdge(3 to 4, null)
        }

        undirCentrality.harmonicCentrality().let {
            val expected = listOf(0.0, 11.0 / 18, 5.0 / 6, 5.0 / 6, 11.0 / 18)
            for (index in 1..4) {
                assertEquals(roundTo(expected[index]), it[index]!!)
            }
        }

    }

    @Test
    fun `result of function for non-trivial(more complex) undirected graph must be correct`() {

        addVertices(undirGraph, 9)

        undirGraph.run {
            addEdge(1 to 2, null)
            addEdge(3 to 2, null)
            addEdge(6 to 2, null)
            addEdge(6 to 4, null)
            addEdge(6 to 5, null)
            addEdge(6 to 3, null)
            addEdge(4 to 5, null)
            addEdge(5 to 3, null)
            addEdge(5 to 8, null)
            addEdge(1 to 8, null)
            addEdge(1 to 6, null)
            addEdge(7 to 9, null)
        }

        undirCentrality.harmonicCentrality().let {
            val expected = listOf(0.0, 4.5 / 8, 4.5 / 8, 4.5 / 8, 0.5, 5.0 / 8, 11.0 / 16, 1.0 / 8, 0.5, 0.125)
            for (index in 1..9) {
                assertEquals(roundTo(expected[index]), it[index])
            }
        }

    }

    @Test
    fun `result of function for non-trivial(more complex) directed graph must be correct`() {

        addVertices(dirGraph, 9)

        dirGraph.run {
            addEdge(1 to 3, null)
            addEdge(2 to 1, null)
            addEdge(2 to 3, null)
            addEdge(4 to 3, null)
            addEdge(5 to 3, null)
            addEdge(3 to 8, null)
            addEdge(3 to 9, null)
            addEdge(5 to 9, null)
            addEdge(8 to 7, null)
            addEdge(8 to 9, null)
            addEdge(9 to 7, null)
            addEdge(7 to 6, null)
        }

        dirCentrality.harmonicCentrality().let {
            val expected = listOf(0.0, 31.0 / (12*8), 43.0/ (12*8), 17.0 / 48, 31.0/ (12*8), 10.0 / 24, 0.0, 1.0 / 8, 2.5/8, 3.0/16)
            for (index in 1..9) {
                assertEquals(roundTo(expected[index]), it[index])
            }
        }

    }

}
*/
