package dev.kazusato.tools.appdep.node

import dev.kazusato.tools.appdep.NodeType

data class Node(
    val name: String,
    val type: NodeType,
    val isTemporary: Boolean = false,
    val frontendDependencies: MutableList<Node> = mutableListOf(),
    val backendDependencies: MutableList<Node> = mutableListOf(),
    val libraryDependencies: MutableList<Node> = mutableListOf(),
    val databaseDependencies: MutableList<Node> = mutableListOf(),
    val queueDependencies: MutableList<Node> = mutableListOf(),
    val extsysDependencies: MutableList<Node> = mutableListOf()
) {
    fun allDependencies(): List<MutableList<Node>> {
        return listOf(
            frontendDependencies,
            backendDependencies,
            libraryDependencies,
            databaseDependencies,
            queueDependencies,
            extsysDependencies
        )
    }

    fun retrieveAllDependencies(): MutableList<Node> {
        val result = mutableListOf<Node>()
        allDependencies().forEach {dependencyList ->
            dependencyList.forEach { dep -> result.addAll(dep.retrieveAllDependencies()) }
        }
        result.add(this)
        return result
    }
}