package dev.kazusato.tools.appdep.diagram

import dev.kazusato.tools.appdep.NodeType
import dev.kazusato.tools.appdep.graph.DependencyGraph
import java.lang.StringBuilder

data class DiagramFragment(
    val graph: DependencyGraph
) {

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendln("@startuml")
        builder.appendln()
        graph.nodeMaps[NodeType.FRONTEND]!!.values.forEach { node -> builder.appendln("rectangle ${quoteName(node.name)} as ${replaceDashWithUnderscore(node.name)}") }
        graph.nodeMaps[NodeType.BACKEND]!!.values.forEach { node -> builder.appendln("rectangle ${quoteName(node.name)} as ${replaceDashWithUnderscore(node.name)}") }
        graph.nodeMaps[NodeType.LIBRARY]!!.values.forEach { node -> builder.appendln("rectangle ${quoteName(node.name)} as ${replaceDashWithUnderscore(node.name)}") }
        graph.nodeMaps[NodeType.DATABASE]!!.values.forEach { node -> builder.appendln("rectangle ${quoteName(node.name)} as ${replaceDashWithUnderscore(node.name)}") }
        graph.nodeMaps[NodeType.QUEUE]!!.values.forEach { node -> builder.appendln("rectangle ${quoteName(node.name)} as ${replaceDashWithUnderscore(node.name)}") }
        graph.nodeMaps[NodeType.EXTSYS]!!.values.forEach { node -> builder.appendln("rectangle ${quoteName(node.name)} as ${replaceDashWithUnderscore(node.name)}") }
        builder.appendln()
        graph.nodeMaps[NodeType.FRONTEND]!!.values.forEach { node ->
            node.frontendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
            node.backendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
            node.libraryDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
            node.databaseDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
            node.queueDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
            node.extsysDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
        }
        graph.nodeMaps[NodeType.BACKEND]!!.values.forEach { node ->
            node.frontendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
            node.backendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
            node.libraryDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
            node.databaseDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
            node.queueDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
            node.extsysDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
        }
        graph.nodeMaps[NodeType.LIBRARY]!!.values.forEach { node ->
            node.frontendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
            node.backendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
            node.libraryDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
            node.databaseDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
            node.queueDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
            node.extsysDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
        }
        graph.nodeMaps[NodeType.DATABASE]!!.values.forEach { node ->
            node.frontendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
            node.backendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
            node.libraryDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
            node.databaseDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
            node.queueDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
            node.extsysDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
        }
        graph.nodeMaps[NodeType.QUEUE]!!.values.forEach { node ->
            node.frontendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -l-> ${replaceDashWithUnderscore(dep.name)}") }
            node.backendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
            node.libraryDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
            node.databaseDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
            node.queueDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -r-> ${replaceDashWithUnderscore(dep.name)}") }
            node.extsysDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
        }
        graph.nodeMaps[NodeType.EXTSYS]!!.values.forEach { node ->
            node.frontendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -u-> ${replaceDashWithUnderscore(dep.name)}") }
            node.backendDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -l-> ${replaceDashWithUnderscore(dep.name)}") }
            node.libraryDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -l-> ${replaceDashWithUnderscore(dep.name)}") }
            node.databaseDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
            node.queueDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
            node.extsysDependencies.forEach { dep -> builder.appendln("${replaceDashWithUnderscore(node.name)} -d-> ${replaceDashWithUnderscore(dep.name)}") }
        }
        builder.appendln()
        builder.appendln("@enduml")

        return builder.toString()
    }

    private fun quoteName(name: String): String {
        return "\"${name}\""
    }

    private fun replaceDashWithUnderscore(name: String): String {
        return name.replace("-", "_")
    }

}