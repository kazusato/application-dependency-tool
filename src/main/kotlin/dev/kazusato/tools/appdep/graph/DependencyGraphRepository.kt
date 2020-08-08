package dev.kazusato.tools.appdep.graph

import dev.kazusato.tools.appdep.node.Node

class DependencyGraphRepository {

    val graphList = mutableListOf<DependencyGraph>()

    fun registerNode(node: Node) {
        val allDep = node.retrieveAllDependencies()

        var addedToExisting = false
        for (graph in graphList) {
            if (graph.containsAny(allDep)) {
                graph.addAll(allDep)
                addedToExisting = true
            }
        }

        if (!addedToExisting) {
            val newGraph = DependencyGraph()
            newGraph.addAll(allDep)
            graphList.add(newGraph)
        }
    }


}