package dev.kazusato.tools.appdep.diagram.renderer

import dev.kazusato.tools.appdep.NodeType
import dev.kazusato.tools.appdep.diagram.DiagramConfiguration
import dev.kazusato.tools.appdep.diagram.NodeShape

class ShapeRenderer(val shape: NodeShape, val config: DiagramConfiguration, val typeList: List<NodeType>) {

    fun generateShapeSpecificConfiguration(): String {
        val builder = StringBuilder()
        builder.appendln("skinparam ${shape.expression} {")

        typeList.forEach { type ->
            builder.appendln("backgroundColor<<${type.expression}>> ${config.typeSpecificConfigurationMap[type]!!.backgroundColor}")
        }

        builder.appendln("}")

        return builder.toString()
    }

}