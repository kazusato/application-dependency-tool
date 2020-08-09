package dev.kazusato.tools.appdep.diagram.renderer

import dev.kazusato.tools.appdep.NodeType
import dev.kazusato.tools.appdep.NodeType.*
import dev.kazusato.tools.appdep.diagram.DiagramConfiguration
import dev.kazusato.tools.appdep.diagram.renderer.NodePosition.*
import dev.kazusato.tools.appdep.node.Node

class RelationRenderer(val config: DiagramConfiguration) {

    private val nodePositionMap: Map<Pair<NodeType, NodeType>, NodePosition> = mapOf(
        Pair(FRONTEND, FRONTEND) to RIGHT,
        Pair(FRONTEND, BACKEND) to DOWN,
        Pair(FRONTEND, LIBRARY) to RIGHT,
        Pair(FRONTEND, DATABASE) to DOWN,
        Pair(FRONTEND, QUEUE) to RIGHT,
        Pair(FRONTEND, EXTSYS) to DOWN,
        Pair(BACKEND, FRONTEND) to UP,
        Pair(BACKEND, BACKEND) to RIGHT,
        Pair(BACKEND, LIBRARY) to RIGHT,
        Pair(BACKEND, DATABASE) to DOWN,
        Pair(BACKEND, QUEUE) to UP,
        Pair(BACKEND, EXTSYS) to RIGHT,
        Pair(LIBRARY, FRONTEND) to RIGHT,
        Pair(LIBRARY, BACKEND) to RIGHT,
        Pair(LIBRARY, LIBRARY) to RIGHT,
        Pair(LIBRARY, DATABASE) to DOWN,
        Pair(LIBRARY, QUEUE) to UP,
        Pair(LIBRARY, EXTSYS) to RIGHT,
        Pair(DATABASE, FRONTEND) to UP,
        Pair(DATABASE, BACKEND) to UP,
        Pair(DATABASE, LIBRARY) to UP,
        Pair(DATABASE, DATABASE) to RIGHT,
        Pair(DATABASE, QUEUE) to UP,
        Pair(DATABASE, EXTSYS) to UP,
        Pair(QUEUE, FRONTEND) to LEFT,
        Pair(QUEUE, BACKEND) to DOWN,
        Pair(QUEUE, LIBRARY) to DOWN,
        Pair(QUEUE, DATABASE) to DOWN,
        Pair(QUEUE, QUEUE) to RIGHT,
        Pair(QUEUE, EXTSYS) to DOWN,
        Pair(EXTSYS, FRONTEND) to UP,
        Pair(EXTSYS, BACKEND) to LEFT,
        Pair(EXTSYS, LIBRARY) to LEFT,
        Pair(EXTSYS, DATABASE) to DOWN,
        Pair(EXTSYS, QUEUE) to UP,
        Pair(EXTSYS, EXTSYS) to DOWN
    )

    fun generateRelation(fromNode: Node, toNode: Node, fromNodeRenderer: NodeRenderer, toNodeRenderer: NodeRenderer): String {
        val position = getPositionFor(fromNode.type, toNode.type)
        val fromNodeName = fromNodeRenderer.generateItemName(fromNode)
        val toNodeName = toNodeRenderer.generateItemName(toNode)
        return "${fromNodeName} -${position.expression}-> ${toNodeName}"
    }

    private fun getPositionFor(fromNodeType: NodeType, toNodeType: NodeType): NodePosition {
        return nodePositionMap[Pair(fromNodeType, toNodeType)]!!
    }

}

enum class NodePosition(val expression: String) {
    UP("u"),
    DOWN("d"),
    RIGHT("r"),
    LEFT("l");
}