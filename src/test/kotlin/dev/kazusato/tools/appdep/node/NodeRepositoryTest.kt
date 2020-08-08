package dev.kazusato.tools.appdep.node

import dev.kazusato.tools.appdep.ApplicationDependencyConfig
import dev.kazusato.tools.appdep.NodeType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NodeRepositoryTest {

    @Test
    fun testRegisterNodeFromApplicationDependencyConfig() {
        val repo = NodeRepository()

        val configUi = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project1-ui/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configUi)

        assertThat(repo.nodeMaps[NodeType.FRONTEND]!!.keys).containsExactly("project1-ui")
        assertThat(repo.nodeMaps[NodeType.BACKEND]).hasSize(0)
        assertThat(repo.nodeMaps[NodeType.LIBRARY]).hasSize(0)
        assertThat(repo.nodeMaps[NodeType.DATABASE]).hasSize(0)
        assertThat(repo.nodeMaps[NodeType.QUEUE]).hasSize(0)
        assertThat(repo.nodeMaps[NodeType.EXTSYS]).hasSize(0)

        val registeredNode = repo.nodeMaps[NodeType.FRONTEND]!!["project1-ui"]!!
        assertThat(registeredNode.isTemporary).isFalse()
        assertThat(registeredNode.frontendDependencies).hasSize(0)
        assertThat(registeredNode.backendDependencies).hasSize(1)
        assertThat(registeredNode.backendDependencies[0].isTemporary).isTrue()
        assertThat(registeredNode.libraryDependencies).hasSize(0)
        assertThat(registeredNode.databaseDependencies).hasSize(0)
        assertThat(registeredNode.queueDependencies).hasSize(0)
        assertThat(registeredNode.extsysDependencies).hasSize(0)
    }

    @Test
    fun testRegisterNodeFromApplicationDependencyConfigLinked() {
        val repo = NodeRepository()

        val configUi = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project1-ui/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configUi)
        val configApi = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project1-api/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configApi)

        assertThat(repo.nodeMaps[NodeType.FRONTEND]!!.keys).containsExactly("project1-ui")
        assertThat(repo.nodeMaps[NodeType.BACKEND]!!.keys).containsExactly("project1-api")
        assertThat(repo.nodeMaps[NodeType.LIBRARY]).hasSize(0)
        assertThat(repo.nodeMaps[NodeType.DATABASE]).hasSize(0)
        assertThat(repo.nodeMaps[NodeType.QUEUE]).hasSize(0)
        assertThat(repo.nodeMaps[NodeType.EXTSYS]).hasSize(0)

        val uiNode = repo.nodeMaps[NodeType.FRONTEND]!!["project1-ui"]!!
        assertThat(uiNode.isTemporary).isFalse()
        assertThat(uiNode.frontendDependencies).hasSize(0)
        assertThat(uiNode.backendDependencies).hasSize(1)
        assertThat(uiNode.backendDependencies[0].isTemporary).isFalse()
        assertThat(uiNode.libraryDependencies).hasSize(0)
        assertThat(uiNode.databaseDependencies).hasSize(0)
        assertThat(uiNode.queueDependencies).hasSize(0)
        assertThat(uiNode.extsysDependencies).hasSize(0)

        val apiNode = repo.nodeMaps[NodeType.BACKEND]!!["project1-api"]!!
        assertThat(apiNode.isTemporary).isFalse()
        assertThat(apiNode.frontendDependencies).hasSize(0)
        assertThat(apiNode.backendDependencies).hasSize(0)
        assertThat(apiNode.libraryDependencies).hasSize(0)
        assertThat(apiNode.databaseDependencies).hasSize(1)
        assertThat(apiNode.databaseDependencies[0].isTemporary).isTrue()
        assertThat(apiNode.queueDependencies).hasSize(0)
        assertThat(apiNode.extsysDependencies).hasSize(1)
        assertThat(apiNode.extsysDependencies[0].isTemporary).isTrue()

    }

}