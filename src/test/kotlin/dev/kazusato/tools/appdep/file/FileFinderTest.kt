package dev.kazusato.tools.appdep.file

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

class FileFinderTest {

    @Test
    fun testFindFromDir() {
        val dirUri = this.javaClass.classLoader.getResource("project3").toURI()
        val dir = File(dirUri)
        val finder = FileFinder()
        val result = finder.findFromDir(dir)
        assertThat(result).hasSize(6)
    }

}