package dev.kazusato.tools.appdep.graph

import dev.kazusato.tools.appdep.ApplicationDependencyConfig
import dev.kazusato.tools.appdep.node.NodeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DependencyGraphRepositoryTest {

    @Test
    fun testRegisterNode() {
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

        val uiNode = repo.findNode(configUi)!!
        val graphRepo = DependencyGraphRepository()
        graphRepo.registerNode(uiNode)

        val graphList = graphRepo.graphList
        assertThat(graphList).hasSize(1)
        val allNodesInGraph = graphList[0].getAllNodes()
        assertThat(allNodesInGraph).hasSize(4)
        assertThat(allNodesInGraph.map { node -> node.name }).containsExactlyInAnyOrder(
            "project1-ui",
            "project1-api",
            "project1db",
            "extsys1"
        )
    }

    // 依存元が複数あるケース （兼・依存先に重複があるケース）
    @Test
    fun testRegisterNodeWithMultipleClientsGeneratingSingleGraph() {
        val repo = NodeRepository()

        val configUi1 = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project1-ui/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configUi1)
        val configApi1 = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project1-api/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configApi1)

        val configUi2 = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project2-ui/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configUi2)
        val configApi2 = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project2-api/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configApi2)

        val graphRepo = DependencyGraphRepository()

        val uiNode1 = repo.findNode(configUi1)!!
        graphRepo.registerNode(uiNode1)

        val uiNode2 = repo.findNode(configUi2)!!
        graphRepo.registerNode(uiNode2)

        val graphList = graphRepo.graphList
        assertThat(graphList).hasSize(1)
        val allNodesInGraph1 = graphList[0].getAllNodes()
        assertThat(allNodesInGraph1).hasSize(7)
        assertThat(allNodesInGraph1.map { node -> node.name }).containsExactlyInAnyOrder(
            "project1-ui",
            "project2-ui",
            "project1-api",
            "project2-api",
            "project1db",
            "project2db",
            "extsys1"
        )
    }

    // グラフが複数に分かれるケース
    @Test
    fun testRegisterNodeGeneratingMultipleGraphs() {
        val repo = NodeRepository()

        val configUi1 = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project1-ui/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configUi1)
        val configApi1 = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project1-api/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configApi1)

        val configUi99 = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project99-ui/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configUi99)
        val configApi99 = ApplicationDependencyConfig.load(
            this.javaClass.classLoader.getResourceAsStream(
                "project99-api/application-dependency.yaml"
            )
        )[0]
        repo.registerNodeFromApplicationDependencyConfig(configApi99)

        val graphRepo = DependencyGraphRepository()

        val uiNode1 = repo.findNode(configUi1)!!
        graphRepo.registerNode(uiNode1)

        val uiNode99 = repo.findNode(configUi99)!!
        graphRepo.registerNode(uiNode99)

        val graphList = graphRepo.graphList
        assertThat(graphList).hasSize(2)
        val allNodesInGraph1 = graphList[0].getAllNodes()
        assertThat(allNodesInGraph1).hasSize(4)
        assertThat(allNodesInGraph1.map { node -> node.name }).containsExactlyInAnyOrder(
            "project1-ui",
            "project1-api",
            "project1db",
            "extsys1"
        )
        val allNodesInGraph99 = graphList[1].getAllNodes()
        assertThat(allNodesInGraph99).hasSize(4)
        assertThat(allNodesInGraph99.map { node -> node.name }).containsExactlyInAnyOrder(
            "project99-ui",
            "project99-api",
            "project99db",
            "extsys99"
        )

    }

}