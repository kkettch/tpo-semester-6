package org.example

import org.knowm.xchart.*
import org.knowm.xchart.BitmapEncoder
import java.io.File

class CsvPlotter(private val csvFilePath: String) {

    fun plot(outputImagePath: String) {
        val file = File(csvFilePath)
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

        chart.addSeries("f(x)", xData, yData)

//        SwingWrapper(chart).displayChart()
        BitmapEncoder.saveBitmap(chart, outputImagePath, BitmapEncoder.BitmapFormat.PNG)
        println("График сохранен: $outputImagePath")
    }
}