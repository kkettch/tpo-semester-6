package org.example

import org.example.logarithmic.*
import org.example.systemFunction.SystemFunction
import org.example.trigonometric.*
import kotlin.math.*

fun main() {
    val csvPath = "src/main/resources/CsvFiles/output.csv"
    val imagePath = "src/main/resources/images/trigonometric/sec.png"
    val xStart = -10.0
    val xEnd = 10.0
    val step = 0.001
    val epsilon = 0.001

    val function = { x: Double -> Sec().calculate(x) }

    val csvWriter = CsvWriter(csvPath)
    csvWriter.write(function, xStart, xEnd, step)

    val csvPlotter = CsvPlotter(csvPath)
    csvPlotter.plot(imagePath)

//    val dots = listOf(0.3, 1.0, 10.0)
//    dots.forEach { println("${it},${ln(it)}") }
}


