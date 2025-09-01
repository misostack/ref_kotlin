package net.jsbase.helloworld.services

import java.io.File

class FileHandlerService {

    fun writeNLinesToFile(filePath: String, linesCount: Int = 1_000_000) {
        val file = File(filePath)
        val startTime = System.currentTimeMillis()
        file.printWriter().use { out ->
            for (i in 1..linesCount) {
                out.println("This is line number $i")
            }
        }
        println("Written $linesCount lines to file: $filePath")
        val endTime = System.currentTimeMillis()
        println("File write in ${(endTime - startTime)} ms. Output written to $filePath")
    }

    fun readTextFile(filePath: String, outputPath: String): List<String>{
        try {

            val file = File(filePath)
            println("Reading file: $filePath. File meta: name=${file.name} size=${file.length()} bytes")
            val lines = file.readLines()
            var output = ""
            output += "Name: ${file.name}\n"
            output += "Size: ${file.length()} bytes\n"
            output += "Lines: ${lines.size}\n"
            File(outputPath).writeText(output, Charsets.UTF_8)
            // Read all lines from the file
            return lines
        } catch (e: Exception) {
            println("Error reading file: ${e.message}")
        }
        return listOf<String>()
    }
}