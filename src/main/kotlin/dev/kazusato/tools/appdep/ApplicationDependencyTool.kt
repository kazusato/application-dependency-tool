package dev.kazusato.tools.appdep

import dev.kazusato.tools.appdep.diagram.DiagramFragment
import dev.kazusato.tools.appdep.file.FileFinder
import dev.kazusato.tools.appdep.graph.DependencyGraphRepository
import dev.kazusato.tools.appdep.node.NodeRepository
import picocli.CommandLine
import java.io.*
import java.nio.charset.StandardCharsets

@CommandLine.Command(name = "appdep", description = ["Tool to visualize application dependencies from YAML."])
class ApplicationDependencyTool : Runnable {

    @CommandLine.Option(names = ["-d", "--dir"], required = true, description = ["Directory to search YAML files."])
    private lateinit var rootDir: String

    @CommandLine.Option(names = ["-n", "--name"], required = true, description = ["Base name of output files."])
    private lateinit var baseName: String

    override fun run() {
        val files = FileFinder().findFromDir(File(rootDir))
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
        strList.forEachIndexed() { index, str ->
            BufferedWriter(OutputStreamWriter(FileOutputStream("%s_%02d.txt".format(baseName, index+1)), StandardCharsets.UTF_8)).use { bw ->
                bw.write(str)
            }
        }
    }
}

fun main(args: Array<String>) {
    val exitCode = CommandLine(ApplicationDependencyTool()).execute(*args)
    System.exit(exitCode)
}