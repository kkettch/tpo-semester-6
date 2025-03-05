package org.example.logarithmic

import org.example.systemFunction.Function

abstract class LogarithmicFunction : Function {

    override fun calculate(x: Double, epsilon: Double): Double {
        validateInput(x, epsilon)
        return compute(x, epsilon)
    }

    protected abstract fun compute(x: Double, epsilon: Double): Double

    protected open fun validateInput(x: Double, epsilon: Double) {
        if (x.isNaN() || x.isInfinite() || (x <= 0)) {
            throw IllegalArgumentException("Illegal Argument x: $x")
        }
        if (epsilon.isNaN() || epsilon <= 0 || epsilon >= 1) {
            throw IllegalArgumentException("Illegal Argument epsilon should be in range (0, 1): $epsilon")
        }
    }
}