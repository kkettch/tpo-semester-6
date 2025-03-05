package org.example.trigonometric

import kotlin.math.PI

class Csc(private val sin: Sin): TrigonometricFunction() {
    override fun compute(x: Double, epsilon: Double): Double {
        return 1 / sin.calculate(x, epsilon)
    }

    override fun validateInput(x: Double, epsilon: Double) {
        super.validateInput(x, epsilon)
        if (x % PI == 0.0) {
            throw IllegalArgumentException("X cannot be a multiple of pi: $x")
        }
    }
}