package org.example.logarithmic

import kotlin.math.abs

class Ln : LogarithmicFunction() {

    override fun compute(x: Double, epsilon: Double): Double {

        if (x == 1.0) return 0.0

        val replacement = (x - 1) / (x + 1)
        var term = replacement
        var iter = 1
        var result = 0.0
        var compensation = 0.0

        while (abs(term) > epsilon) {
            val y = (term / iter) - compensation
            val newResult = result + y
            compensation = (newResult - result) - y
            result = newResult
            term *= (replacement * replacement)
            iter += 2
        }

        return 2 * result

    }
}