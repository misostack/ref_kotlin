package services

import net.jsbase.helloworld.services.FileHandlerService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths

class FileHandlerServiceTest {
    private val fileHandlerService: FileHandlerService = FileHandlerService()

    @RepeatedTest(value=100)
    @Disabled
    fun `test file write and read`(){
        var path = Paths.get("tmp").toAbsolutePath()
        val filePath = path.resolve("testfile.txt").toFile()
        val file = File(filePath.toString())
        if (!file.exists()){
            file.writeText("Hello, World!\n", Charsets.UTF_8)
        } else {
            val lines = file.readLines()
            file.appendText("This is kotlin ${lines.size}\n")
        }


        assertEquals(true, true)
    }

    @Test
    fun `test generate file 1_000_000 lines`(){
        val size = 1_000_000
        val path = Paths.get("tmp").toAbsolutePath()
        val filePath = path.resolve("${size}.txt").toFile()
        fileHandlerService.writeNLinesToFile(filePath.toString(), size)
        val lines = File(filePath.toString()).readLines()
        assertEquals(lines.size, size)
    }

    @Test
    @Disabled
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