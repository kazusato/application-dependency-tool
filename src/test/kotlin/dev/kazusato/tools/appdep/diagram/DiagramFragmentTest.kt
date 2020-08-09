package dev.kazusato.tools.appdep.diagram

import dev.kazusato.tools.appdep.ApplicationDependencyConfig
import dev.kazusato.tools.appdep.graph.DependencyGraphRepository
import dev.kazusato.tools.appdep.node.NodeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.BufferedInputStream
import java.nio.charset.StandardCharsets

class DiagramFragmentTest {

    @Test
    fun testToString() {
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

        val graph = graphRepo.graphList[0]
        val diagramFragment = DiagramFragment(graph)
        val diagramFragmentStr = diagramFragment.toString()

        assertThat(diagramFragmentStr).isEqualTo(readFromResource("diagram/fragment-project1.txt"))
    }

    @Test
    fun testToStringComplex() {
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

        val graph = graphRepo.graphList[0]
        val diagramFragment = DiagramFragment(graph)
        val diagramFragmentStr = diagramFragment.toString()

        println(diagramFragmentStr)
//        assertThat(diagramFragmentStr).isEqualTo(readFromResource("diagram/fragment-project1.txt"))
    }

    private fun readFromResource(filePath: String): String {
        BufferedInputStream(this.javaClass.classLoader.getResourceAsStream(filePath)).use { bis ->
            return String(bis.readAllBytes(), StandardCharsets.UTF_8)
        }
    }

}