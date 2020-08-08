package dev.kazusato.tools.appdep.graph

import dev.kazusato.tools.appdep.NodeType
import dev.kazusato.tools.appdep.node.Node

class DependencyGraph {

    val nodeMaps = mapOf(
        NodeType.FRONTEND to mutableMapOf<String, Node>(),
        NodeType.BACKEND to mutableMapOf<String, Node>(),
        NodeType.LIBRARY to mutableMapOf<String, Node>(),
        NodeType.DATABASE to mutableMapOf<String, Node>(),
        NodeType.QUEUE to mutableMapOf<String, Node>(),
        NodeType.EXTSYS to mutableMapOf<String, Node>()
    )

    fun add(node: Node) {
        chooseNodeMap(node.type).put(node.name, node)
    }

    fun addAll(nodeList: List<Node>) {
        nodeList.forEach { node -> add(node) }
    }

    fun contians(node: Node): Boolean {
         return chooseNodeMap(node.type).containsKey(node.name)
    }

    fun containsAny(nodeList: List<Node>): Boolean {
        return nodeList.filter { node -> contians(node) }.size > 0
    }

    fun getAllNodes(): List<Node> {
        val result = mutableListOf<Node>()
        nodeMaps.values.forEach { nodeMap -> result.addAll(nodeMap.values) }
        return result
    }

    private fun chooseNodeMap(type: NodeType): MutableMap<String, Node> {
        return nodeMaps[type]!!
    }

}