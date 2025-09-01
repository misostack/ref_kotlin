package services

import net.jsbase.helloworld.services.FileHandlerService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class FileHandlerServiceTest {
    private val fileHandlerService: FileHandlerService = FileHandlerService()
    @Test
    fun `test readfile`(){
        val fileUrl = javaClass.getResource("/hello.txt")
        requireNotNull(fileUrl) { "hello.txt not found in resources!" }
        val resourceDirectory = Paths.get(fileUrl.toURI()).parent
        val outputPath = resourceDirectory.resolve("output.txt").toString()
        val actualValue = fileHandlerService.readTextFile(resourceDirectory.resolve("hello.txt").toString(), outputPath)
        val expectedLines = listOf("Content", "1. Line one", "2. Line two", "3. Line three")
        assertEquals(expectedLines, actualValue)
    }
}