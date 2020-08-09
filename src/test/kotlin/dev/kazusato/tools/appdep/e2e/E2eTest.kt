package dev.kazusato.tools.appdep.e2e

import dev.kazusato.tools.appdep.ApplicationDependencyConfig
import dev.kazusato.tools.appdep.diagram.DiagramFragment
import dev.kazusato.tools.appdep.file.FileFinder
import dev.kazusato.tools.appdep.graph.DependencyGraphRepository
import dev.kazusato.tools.appdep.node.NodeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets

class E2eTest {

    @Test
    fun testE2e() {
        val dirUri = this.javaClass.classLoader.getResource("project3").toURI()
        val dir = File(dirUri)
        val files = FileFinder().findFromDir(dir)
        val repo = NodeRepository()
        val allConfigList = mutableListOf<ApplicationDependencyConfig>()
        files.forEach { file ->
            BufferedInputStream(FileInputStream(file)).use { bis ->
                val configList = ApplicationDependencyConfig.load(bis)
                allConfigList.addAll(configList)
                configList.forEach { config -> repo.registerNodeFromApplicationDependencyConfig(config) }
            }
        }
        val graphRepo = DependencyGraphRepository()
        allConfigList.forEach { config -> graphRepo.registerNode(repo.findNode(config)!!) }
        val strList = graphRepo.graphList.map { graph -> DiagramFragment(graph).toString() }.toList()
        strList.forEach { str ->
            println(str)
            assertThat(str).isEqualTo(readFromResource("diagram/fragment-project3.txt"))
        }
    }

    private fun readFromResource(filePath: String): String {
        BufferedInputStream(this.javaClass.classLoader.getResourceAsStream(filePath)).use { bis ->
            return String(bis.readAllBytes(), StandardCharsets.UTF_8)
        }
    }
}