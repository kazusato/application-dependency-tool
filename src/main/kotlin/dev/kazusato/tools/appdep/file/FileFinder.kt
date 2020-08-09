package dev.kazusato.tools.appdep.file

import java.io.File

class FileFinder {

    companion object {
        private val fileNames = listOf("application-dependency.yaml", "application-dependency.yml")
    }

    fun findFromDir(directory: File): List<File> {
        val result = mutableListOf<File>()

        directory.listFiles { file -> file.isFile && fileNames.contains(file.name) }.forEach { file -> result.add(file) }
        directory.listFiles { file -> file.isDirectory }.forEach { dir -> result.addAll(findFromDir(dir)) }

        return result
    }

}