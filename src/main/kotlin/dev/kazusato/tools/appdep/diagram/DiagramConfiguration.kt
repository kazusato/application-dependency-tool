package dev.kazusato.tools.appdep.diagram

import dev.kazusato.tools.appdep.NodeType
import java.lang.IllegalArgumentException

data class DiagramConfiguration(
    val defaultConfiguration: DefaultConfiguration,
    val typeSpecificConfigurationMap: Map<NodeType, TypeSpecificConfiguration>
) {
    init {
        listOf(
            NodeType.FRONTEND,
            NodeType.BACKEND,
            NodeType.LIBRARY,
            NodeType.DATABASE,
            NodeType.QUEUE,
            NodeType.EXTSYS
        ).forEach { nodeType ->
            if (!typeSpecificConfigurationMap.containsKey(nodeType)) {
                throw IllegalArgumentException("Type specific configuration map should contain ${nodeType}.")
            }
        }
    }
}