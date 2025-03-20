package org.example.trigonometric

import kotlin.math.PI

class Cot(
    private val sin: Sin = Sin(),
    private val cos: Cos = Cos(sin)
): TrigonometricFunction() {
    override fun compute(x: Double, epsilon: Double): Double {
        return cos.calculate(x, epsilon) / sin.calculate(x, epsilon)
    }

    override fun validateInput(x: Double, epsilon: Double) {
        super.validateInput(x, epsilon)
        if (x % PI == 0.0) {
            throw IllegalArgumentException("X cannot be a multiple of pi: $x")
        }
    }
}