package org.example.trigonometric

import kotlin.math.PI
import kotlin.math.abs

class Sin: TrigonometricFunction() {

    override fun compute(x: Double, epsilon: Double): Double {
        val normX = (x % (2 * PI)).let {
            if (it > PI) it - 2 * PI
            else if (it < -PI) it + 2 * PI
            else it
        }
        var sum = normX
        var prev: Double
        var term = normX
        var step = 1
        var maxSteps = 10

        do {
            prev = sum
            term *= -normX * normX / ((2 * step) * (2 * step + 1))
            sum += term
            step++
            if (abs(sum - prev) < epsilon) { maxSteps-- }
        } while (maxSteps > 0)

        return sum
    }
}