package org.example.logarithmic

import kotlin.math.abs
import kotlin.math.pow


class Ln : LogarithmicFunction() {

    override fun compute(x: Double, epsilon: Double): Double {

        val newEpsilon = epsilon * 0.01
        var sum = 0.0
        var prev: Double
        var step = 1

        if (x < 1) {
            sum = -compute(1 / x, epsilon)
        }
        else if (x <= 2) {
            do {
                prev = sum
                sum += ((-1.0).pow((step - 1)) * (x - 1).pow(step)) / step
                step++
            } while (abs(prev - sum) >= newEpsilon && step < 10000)
            sum = (sum + prev) / 2
        } else {
            do {
                prev = sum
                sum += ((-1.0).pow((step - 1))) / ((x - 1).pow(step) * step)
                step++
            } while (abs(prev - sum) >= newEpsilon)
            sum += compute(x - 1, epsilon)
        }

        return sum
    }
}