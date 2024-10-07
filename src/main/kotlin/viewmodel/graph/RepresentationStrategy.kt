package viewmodel.graph

import viewmodel.graph.VertexViewModel

interface RepresentationStrategy {
  fun place(width: Double, height: Double, vertices: Collection<VertexViewModel>)
}