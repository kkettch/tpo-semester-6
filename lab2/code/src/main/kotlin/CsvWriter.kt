package org.example

import java.io.File
import java.io.PrintWriter
import java.nio.charset.StandardCharsets

class CsvWriter(private val filePath: String) {

    fun write(function: (Double) -> Double?, xStart: Double, xEnd: Double, step: Double) {
        val file = File(filePath)
        val writer = PrintWriter(file, StandardCharsets.UTF_8)
        writer.write("x,result\n")

        var x = xStart
        while (x <= xEnd) {
            val result = try {
                function(x)
            } catch (_: IllegalArgumentException) {
                null
            }

            if (result != null) {
                writer.println("$x,$result")
            }

            x += step
        }

        writer.close()
        println("Данные записаны в CSV: $filePath")
    }
}