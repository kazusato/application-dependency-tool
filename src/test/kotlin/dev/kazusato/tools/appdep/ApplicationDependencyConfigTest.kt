package dev.kazusato.tools.appdep

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ApplicationDependencyConfigTest {

    @Test
    fun testLoad() {
        val config = ApplicationDependencyConfig.load(this.javaClass.classLoader.getResourceAsStream("dev/kazusato/tools/appdep/frontend-success.yaml"))
        assertThat(config).hasSize(1)
        assertThat(config[0]).isNotNull
        assertThat(config[0].datatype).isEqualTo(DataType.APPLICATION_CONFIG)
        assertThat(config[0].version).isEqualTo(SchemaVersion.V1)
        assertThat(config[0].name).isEqualTo("project1-ui")
        assertThat(config[0].type).isEqualTo(NodeType.FRONTEND)
        assertThat(config[0].dependencies).isNotNull()
        assertThat(config[0].dependencies.frontend).hasSize(0)
        assertThat(config[0].dependencies.backend).containsExactly("project1-api", "project2-api")
        assertThat(config[0].dependencies.library).hasSize(0)
        assertThat(config[0].dependencies.database).hasSize(0)
        assertThat(config[0].dependencies.queue).hasSize(0)
        assertThat(config[0].dependencies.extsys).contains("extsys1")
    }

    @Test
    fun testLoadMultiple() {
        val config = ApplicationDependencyConfig.load(this.javaClass.classLoader.getResourceAsStream("dev/kazusato/tools/appdep/multiple-success.yaml"))
        assertThat(config).hasSize(2)
        assertThat(config[0]).isNotNull
        assertThat(config[0].datatype).isEqualTo(DataType.APPLICATION_CONFIG)
        assertThat(config[0].version).isEqualTo(SchemaVersion.V1)
        assertThat(config[0].name).isEqualTo("project1-ui")
        assertThat(config[0].type).isEqualTo(NodeType.FRONTEND)
        assertThat(config[0].dependencies).isNotNull()
        assertThat(config[0].dependencies.frontend).hasSize(0)
        assertThat(config[0].dependencies.backend).containsExactly("project1-api", "project2-api")
        assertThat(config[0].dependencies.library).hasSize(0)
        assertThat(config[0].dependencies.database).hasSize(0)
        assertThat(config[0].dependencies.queue).hasSize(0)
        assertThat(config[0].dependencies.extsys).contains("extsys1")

        assertThat(config[1]).isNotNull
        assertThat(config[1].datatype).isEqualTo(DataType.APPLICATION_CONFIG)
        assertThat(config[1].version).isEqualTo(SchemaVersion.V1)
        assertThat(config[1].name).isEqualTo("project1-api")
        assertThat(config[1].type).isEqualTo(NodeType.BACKEND)
        assertThat(config[1].dependencies).isNotNull()
        assertThat(config[1].dependencies.frontend).hasSize(0)
        assertThat(config[1].dependencies.backend).containsExactly("project3-api")
        assertThat(config[1].dependencies.library).hasSize(0)
        assertThat(config[1].dependencies.database).containsExactly("project1-db")
        assertThat(config[1].dependencies.queue).hasSize(0)
        assertThat(config[1].dependencies.extsys).contains("extsys2")
    }

}