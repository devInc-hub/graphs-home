package viewmodel.graph

import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.graph.Edge
import model.graph.Graph
import model.graph.UndirectedGraph
import model.graph.Vertex

class GraphViewModel<D>(
  private val graph: Graph<D>,
  private val showVerticesLabels: State<Boolean>,
  private val showEdgesLabels: State<Boolean>,
) {
  internal val verticesView: HashMap<Int, VertexViewModel<D>> = hashMapOf()
  internal val edgesView: HashMap<Edge<D>, EdgeViewModel<D>> = hashMapOf()

  init {
    graph.getVertices().forEach { vertex ->
      verticesView[vertex.id] = VertexViewModel(0.dp, 0.dp, Color.Gray, vertex, showVerticesLabels)
    }
    graph.edges.forEach { edge ->
      val fst = verticesView[edge.vertices.first]
        ?: throw IllegalStateException("VertexView for vertex with id: ${edge.vertices.first} not found")
      val snd = verticesView[edge.vertices.second]
        ?: throw IllegalStateException("VertexView for vertex with id: ${edge.vertices.second} not found")
      edgesView[edge] = EdgeViewModel(fst, snd, edge, showEdgesLabels)
    }
  }

  fun addVertex(id: Int, data: D) {
    graph.addVertex(id, data)
    verticesView[id] = VertexViewModel(0.dp, 0.dp, Color.Gray, graph.vertices[id]!!, showVerticesLabels)
  }
}
