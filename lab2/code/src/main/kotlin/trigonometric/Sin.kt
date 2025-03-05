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
        var term = normX
        var step = 0
        var maxSteps = 10

        while (maxSteps > 0) {
            term *= -normX * normX / ((2 * step + 2) * (2 * step + 3))
            sum += term
            step++
            if (abs(term) < epsilon) { maxSteps-- }
        }

        return sum
    }
}