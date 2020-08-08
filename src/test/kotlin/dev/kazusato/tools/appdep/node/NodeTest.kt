package dev.kazusato.tools.appdep.node

import dev.kazusato.tools.appdep.ApplicationDependencyConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NodeTest {

    @Test
    fun testRetrieveAllDependencies() {
        val repo = NodeRepository()

        val configUi =
            ApplicationDependencyConfig.load(this.javaClass.classLoader.getResourceAsStream("project1-ui/application-dependency.yaml"))[0]
        repo.registerNodeFromApplicationDependencyConfig(configUi)
        val configApi =
            ApplicationDependencyConfig.load(this.javaClass.classLoader.getResourceAsStream("project1-api/application-dependency.yaml"))[0]
        repo.registerNodeFromApplicationDependencyConfig(configApi)

        val uiNode = repo.findNode(configUi)
        val allDependencies = uiNode!!.retrieveAllDependencies()
        assertThat(allDependencies).hasSize(4)
        assertThat(allDependencies.map { dep -> dep.name }).containsExactlyInAnyOrder(
            "project1-ui",
            "project1-api",
            "project1db",
            "extsys1"
        )
    }

}