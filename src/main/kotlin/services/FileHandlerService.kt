package net.jsbase.helloworld.services

import java.io.File

class FileHandlerService {
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