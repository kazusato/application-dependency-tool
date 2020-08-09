package dev.kazusato.tools.appdep.node

import dev.kazusato.tools.appdep.ApplicationDependencyConfig
import dev.kazusato.tools.appdep.NodeType
import java.lang.IllegalArgumentException

class NodeRepository {

    val nodeMaps = mapOf(
        NodeType.FRONTEND to mutableMapOf<String, Node>(),
        NodeType.BACKEND to mutableMapOf<String, Node>(),
        NodeType.LIBRARY to mutableMapOf<String, Node>(),
        NodeType.DATABASE to mutableMapOf<String, Node>(),
        NodeType.QUEUE to mutableMapOf<String, Node>(),
        NodeType.EXTSYS to mutableMapOf<String, Node>()
    )

    fun registerNodeFromApplicationDependencyConfig(config: ApplicationDependencyConfig): Node {
        val result = prepareNode(config)
        updateLinkToMyself(result)

        return result
    }

    fun findNode(config: ApplicationDependencyConfig): Node? {
        return chooseNodeMap(config.type)[config.name]
    }

    private fun updateLinkToMyself(self: Node) {
        nodeMaps.values.forEach { nodeMap -> replaceMyself(nodeMap, self) }
    }

    private fun replaceMyself(nodeMap: Map<String, Node>, self: Node) {
        for (key in nodeMap.keys) {
            val node = nodeMap[key]!!
            val dependencyList = chooseDependencyList(node, self.type)
            val myselfInDependencyList = dependencyList.filter { it.name == self.name }.singleOrNull()
            if (myselfInDependencyList != null && myselfInDependencyList.isTemporary) {
                dependencyList.remove(myselfInDependencyList)
                dependencyList.add(self)
            }
        }
    }

    private fun chooseDependencyList(node: Node, type: NodeType): MutableList<Node> {
        return when (type) {
            NodeType.FRONTEND -> node.frontendDependencies
            NodeType.BACKEND -> node.backendDependencies
            NodeType.LIBRARY -> node.libraryDependencies
            NodeType.DATABASE -> node.databaseDependencies
            NodeType.QUEUE -> node.queueDependencies
            NodeType.EXTSYS -> node.extsysDependencies
        }
    }

    private fun prepareNode(config: ApplicationDependencyConfig): Node {
        val nodeMap = chooseNodeMap(config.type)
        val currentNode = nodeMap[config.name]
        if (currentNode != null) {
            throw IllegalArgumentException("Node with name ${config.name} and type ${config.type} already exists.")
        }

        val node = Node(config.name, config.type).also { node ->
            fillDependencies(node, config)
        }
        nodeMap.put(config.name, node)

        return node
    }

    private fun chooseNodeMap(type: NodeType): MutableMap<String, Node> {
        return nodeMaps[type]!!
    }

    private fun fillDependencies(node: Node, config: ApplicationDependencyConfig) {
        config.dependencies.frontend.forEach { name ->
            node.frontendDependencies.add(chooseNodeMap(NodeType.FRONTEND)[name] ?: Node(name, NodeType.FRONTEND, true))
        }
        config.dependencies.backend.forEach { name ->
            node.backendDependencies.add(chooseNodeMap(NodeType.BACKEND)[name] ?: Node(name, NodeType.BACKEND, true))
        }
        config.dependencies.library.forEach { name ->
            node.libraryDependencies.add(chooseNodeMap(NodeType.LIBRARY)[name] ?: Node(name, NodeType.LIBRARY, true))
        }
        config.dependencies.database.forEach { name ->
            node.databaseDependencies.add(chooseNodeMap(NodeType.DATABASE)[name] ?: Node(name, NodeType.DATABASE, true))
        }
        config.dependencies.queue.forEach { name ->
            node.queueDependencies.add(chooseNodeMap(NodeType.QUEUE)[name] ?: Node(name, NodeType.QUEUE, true))
        }
        config.dependencies.extsys.forEach { name ->
            node.extsysDependencies.add(chooseNodeMap(NodeType.EXTSYS)[name] ?: Node(name, NodeType.EXTSYS, true))
        }
    }
}