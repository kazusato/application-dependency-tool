package dev.kazusato.tools.appdep.diagram

import dev.kazusato.tools.appdep.NodeType
import java.lang.IllegalArgumentException

data class TypeSpecificConfiguration(
    val type: NodeType,
    val shape: NodeShape,
    val backgroundColor: String
)

enum class NodeShape(val expression: String) {
    RECTANGLE("rectangle"),
    DATABASE("database");

    companion object {
        fun expessionOf(expression: String): NodeShape {
            return when (expression) {
                RECTANGLE.expression -> RECTANGLE
                DATABASE.expression -> DATABASE
                else -> throw IllegalArgumentException("No such shape: ${expression}")
            }
        }
    }
}