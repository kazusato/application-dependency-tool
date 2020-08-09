package dev.kazusato.tools.appdep.diagram

import dev.kazusato.tools.appdep.NodeType
import dev.kazusato.tools.appdep.diagram.renderer.NodeRenderer
import dev.kazusato.tools.appdep.diagram.renderer.RelationRenderer
import dev.kazusato.tools.appdep.diagram.renderer.ShapeRenderer
import dev.kazusato.tools.appdep.diagram.renderer.SystemwideRenderer
import dev.kazusato.tools.appdep.graph.DependencyGraph
import java.lang.StringBuilder

data class DiagramFragment(
    val graph: DependencyGraph,
    val configuration: DiagramConfiguration
) {

    constructor(graph: DependencyGraph) : this(
        graph, DiagramConfiguration(
            DefaultConfiguration(24),
            mapOf(
                NodeType.FRONTEND to TypeSpecificConfiguration(NodeType.FRONTEND, NodeShape.RECTANGLE, "DeepSkyBlue"),
                NodeType.BACKEND to TypeSpecificConfiguration(NodeType.BACKEND, NodeShape.RECTANGLE, "MediumSeaGreen"),
                NodeType.LIBRARY to TypeSpecificConfiguration(NodeType.LIBRARY, NodeShape.RECTANGLE, "LemonChiffon"),
                NodeType.DATABASE to TypeSpecificConfiguration(NodeType.DATABASE, NodeShape.DATABASE, "LightPink"),
                NodeType.QUEUE to TypeSpecificConfiguration(NodeType.QUEUE, NodeShape.RECTANGLE, "Violet"),
                NodeType.EXTSYS to TypeSpecificConfiguration(NodeType.EXTSYS, NodeShape.RECTANGLE, "SandyBrown")
            )
        )
    )

    private fun getNodeTypeListByShape(shape: NodeShape): List<NodeType> {
        return configuration.typeSpecificConfigurationMap.values
            .filter { config -> config.shape == shape }
            .map { config -> config.type }.toList()
    }

    override fun toString(): String {
        val systemwideRenderer =
            SystemwideRenderer(configuration)
        val rectangleShapeRenderer = ShapeRenderer(
            NodeShape.RECTANGLE,
            configuration,
            getNodeTypeListByShape(NodeShape.RECTANGLE)
        )
        val databaseShapeRenderer = ShapeRenderer(
            NodeShape.DATABASE,
            configuration,
            getNodeTypeListByShape(NodeShape.DATABASE)
        )

        val builder = StringBuilder()
        builder.appendln(systemwideRenderer.generateBeginStatement())
        builder.appendln()
        builder.appendln(systemwideRenderer.generateSystemwideConfiguration())
        builder.appendln(rectangleShapeRenderer.generateShapeSpecificConfiguration())
        builder.appendln(databaseShapeRenderer.generateShapeSpecificConfiguration())
        builder.appendln()
        val nodeRendererMap = mutableMapOf<NodeType, NodeRenderer>()
        NodeType.values().forEach { nodeType ->
            val nodeRenderer = NodeRenderer(
                nodeType,
                configuration.typeSpecificConfigurationMap[nodeType]!!
            )
            nodeRendererMap[nodeType] = nodeRenderer
            graph.nodeMaps[nodeType]!!.values.forEach { node ->
                builder.appendln(nodeRenderer.generateItemDeclaration(node))
            }
        }
        builder.appendln()
        NodeType.values().forEach { nodeType ->
            graph.nodeMaps[nodeType]!!.values.forEach { node ->
                node.allDependencies().forEach { dependencies ->
                    dependencies.forEach { dep ->
                        builder.appendln(
                            RelationRenderer(
                                configuration
                            ).generateRelation(node, dep, nodeRendererMap[node.type]!!, nodeRendererMap[dep.type]!!))
                    }
                }
            }
        }
        builder.appendln()
        builder.appendln(systemwideRenderer.generateEndStatement())

        return builder.toString()
    }

    private fun quoteName(name: String): String {
        return "\"${name}\""
    }

    private fun replaceDashWithUnderscore(name: String): String {
        return name.replace("-", "_")
    }
}