package org.example.trigonometric

import kotlin.math.PI

class Sec(
    private val cos: Cos = Cos(Sin()),
): TrigonometricFunction() {
    override fun compute(x: Double, epsilon: Double): Double {
        return 1 / cos.calculate(x, epsilon)
    }

    override fun validateInput(x: Double, epsilon: Double) {
        super.validateInput(x, epsilon)
        if (x % (PI/2) == 0.0 && x % PI != 0.0) {
            throw IllegalArgumentException("X cannot be a multiple of pi/2: $x")
        }
    }
}