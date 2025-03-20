package org.example

import org.example.logarithmic.*
import org.example.systemFunction.SystemFunction
import org.example.trigonometric.*
import java.io.File
import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import org.knowm.xchart.*
import org.knowm.xchart.BitmapEncoder.saveBitmap

fun main() {
    val csvPath = "src/main/resources/CsvFiles/output.csv"
    val fileForPng = "src/main/resources/images/trigonometric/sec.png"
    val xStart = -10.0
    val xEnd = 10.0
    val step = 0.001
    val epsilon = 0.001

    writeToCsv(csvPath, { x -> Sec(Cos(Sin())).calculate(x, epsilon) }, xStart, xEnd, step)
    drawFromCsv(csvPath, fileForPng)
}


fun writeToCsv(filePath: String, function: (Double) -> Double?, xStart: Double, xEnd: Double, step: Double) {
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
}

fun drawFromCsv(filePath: String, fileToWrite: String) {
    val file = File(filePath)
    val xData = mutableListOf<Double>()
    val yData = mutableListOf<Double>()

    file.useLines { lines ->
        lines.drop(1).forEach { line ->
            val parts = line.split(",")
            if (parts.size == 2) {
                val x = parts[0].toDoubleOrNull()
                val y = parts[1].toDoubleOrNull()
                if (x != null && y != null && y > -50.0 && y < 50.0) {
                    xData.add(x)
                    yData.add(y)
                }
            }
        }
    }

    val chart = XYChartBuilder().width(800).height(600).title("Function Plot")
        .xAxisTitle("X").yAxisTitle("Y").build()

    chart.addSeries("sec(x)", xData, yData)

//    SwingWrapper(chart).displayChart()
    saveBitmap(chart, fileToWrite, BitmapEncoder.BitmapFormat.PNG)
    println("График сохранен в .png файл")
}

