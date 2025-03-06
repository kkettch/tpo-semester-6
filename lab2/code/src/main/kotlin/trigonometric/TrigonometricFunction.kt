package org.example.trigonometric

import org.example.systemFunction.Function

abstract class TrigonometricFunction: Function {

    override fun calculate(x: Double, epsilon: Double): Double {
        validateInput(x, epsilon)
        return compute(x, epsilon)
    }

    protected abstract fun compute(x: Double, epsilon: Double): Double

    protected open fun validateInput(x: Double, epsilon: Double) {
        if (x.isNaN() || x.isInfinite()) {
            throw IllegalArgumentException("Illegal Argument x: $x")
        }
        if (epsilon.isNaN() || epsilon <= 0 || epsilon >= 1) {
            throw IllegalArgumentException("Illegal Argument epsilon should be in range (0, 1): $epsilon")
        }
    }
}