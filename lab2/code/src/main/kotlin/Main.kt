package org.example

import org.example.logarithmic.*
import org.example.systemFunction.SystemFunction
import org.example.trigonometric.*

fun main() {
    val csvPath = "src/main/resources/CsvFiles/output.csv"
    val imagePath = "src/main/resources/images/trigonometric/sec.png"
    val xStart = -10.0
    val xEnd = 10.0
    val step = 0.001
    val epsilon = 0.001

    val function = { x: Double -> SystemFunction().calculate(x, epsilon) }

    val csvWriter = CsvWriter(csvPath)
    csvWriter.write(function, xStart, xEnd, step)

    val csvPlotter = CsvPlotter(csvPath)
    csvPlotter.plot(imagePath)
}


