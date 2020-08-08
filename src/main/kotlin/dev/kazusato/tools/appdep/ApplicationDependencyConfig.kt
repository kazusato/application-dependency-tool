package dev.kazusato.tools.appdep

import org.yaml.snakeyaml.Yaml
import java.io.InputStream

data class ApplicationDependencyConfig(
    val datatype: DataType,
    val version: SchemaVersion,
    val name: String,
    val type: NodeType,
    val dependencies: DependencyInfo
) {

    companion object {
        fun load(inputStream: InputStream): List<ApplicationDependencyConfig> {
            val yaml = Yaml()
            val dataList = yaml.loadAll(inputStream)

            return dataList.mapNotNull { data ->
                if (data == null) {
                    null
                } else {
                    val map = data as Map<String, Any>
                    ApplicationDependencyConfig(
                        map["datatype"]?.let { DataType.expressionOf(it as String) }
                            ?: throw RuntimeException("Datatype must be specified."),
                        map["version"]?.let { SchemaVersion.expressionOf(it as String) }
                            ?: throw RuntimeException("Version must be specified."),
                        map["name"]?.let { it as String } ?: throw RuntimeException("Name must be specified."),
                        map["type"]?.let { NodeType.expressionOf(it as String) }
                            ?: throw RuntimeException("Type must be specified."),
                        map["dependencies"]?.let { createDependencyInfo(it as Map<String, Any>) } ?: DependencyInfo()
                    )
                }
            }.toList()
        }

        private fun createDependencyInfo(yamlDep: Map<String, Any>): DependencyInfo {
            val dep = DependencyInfo()

            val yamlFrontendDep = yamlDep[NodeType.FRONTEND.expression]
            if (yamlFrontendDep != null) {
                dep.frontend.addAll(yamlFrontendDep as List<String>)
            }
            val yamlBackendDep = yamlDep[NodeType.BACKEND.expression]
            if (yamlBackendDep != null) {
                dep.backend.addAll(yamlBackendDep as List<String>)
            }
            val yamlLibraryDep = yamlDep[NodeType.LIBRARY.expression]
            if (yamlLibraryDep != null) {
                dep.library.addAll(yamlLibraryDep as List<String>)
            }
            val yamlDatabaseDep = yamlDep[NodeType.DATABASE.expression]
            if (yamlDatabaseDep != null) {
                dep.database.addAll(yamlDatabaseDep as List<String>)
            }
            val yamlQueueDep = yamlDep[NodeType.QUEUE.expression]
            if (yamlQueueDep != null) {
                dep.queue.addAll(yamlQueueDep as List<String>)
            }
            val yamlExtsysDep = yamlDep[NodeType.EXTSYS.expression]
            if (yamlExtsysDep != null) {
                dep.extsys.addAll(yamlExtsysDep as List<String>)
            }

            return dep
        }
    }

}

enum class DataType(val expression: String) {
    APPLICATION_CONFIG("application-config");

    companion object {
        fun expressionOf(expression: String): DataType {
            return when (expression) {
                APPLICATION_CONFIG.expression -> APPLICATION_CONFIG
                else -> throw IllegalArgumentException("No such data type: ${expression}")
            }
        }
    }

    override fun toString(): String {
        return expression
    }
}

enum class NodeType(val expression: String) {
    FRONTEND("frontend"),
    BACKEND("backend"),
    LIBRARY("library"),
    DATABASE("database"),
    QUEUE("queue"),
    EXTSYS("extsys");

    companion object {
        fun expressionOf(expression: String): NodeType {
            return when (expression) {
                FRONTEND.expression -> FRONTEND
                BACKEND.expression -> BACKEND
                LIBRARY.expression -> LIBRARY
                DATABASE.expression -> DATABASE
                QUEUE.expression -> QUEUE
                EXTSYS.expression -> EXTSYS
                else -> throw IllegalArgumentException("No such node type: ${expression}")
            }
        }
    }

    override fun toString(): String {
        return expression
    }
}

enum class SchemaVersion(val expression: String) {
    V1("v1");

    companion object {
        fun expressionOf(expression: String): SchemaVersion {
            return when (expression) {
                V1.expression -> V1
                else -> throw IllegalArgumentException("No such version: ${expression}")
            }
        }
    }

    override fun toString(): String {
        return expression
    }
}