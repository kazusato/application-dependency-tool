package dev.kazusato.tools.appdep.diagram.renderer

import dev.kazusato.tools.appdep.NodeType
import dev.kazusato.tools.appdep.diagram.TypeSpecificConfiguration
import dev.kazusato.tools.appdep.node.Node

class NodeRenderer(val nodeType: NodeType, val config: TypeSpecificConfiguration) {

    fun generateDisplayName(node: Node): String {
        return quoteName(node.name)
    }

    fun generateItemName(node: Node): String {
        return replaceDashWithUnderscore(node.name)
    }

    fun generateStereotype(): String {
        return "<<${nodeType.expression}>>"
    }

    fun generateItemDeclaration(node: Node): String {
        return "${config.shape.expression} ${generateDisplayName(node)} ${generateStereotype()} as ${generateItemName(node)}"
    }

    private fun quoteName(name: String): String {
        return "\"${name}\""
    }

    private fun replaceDashWithUnderscore(name: String): String {
        return name.replace("-", "_")
    }

}