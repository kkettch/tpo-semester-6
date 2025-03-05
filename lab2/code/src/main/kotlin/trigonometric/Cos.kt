package org.example.trigonometric

import kotlin.math.PI

class Cos(private val sin: Sin): TrigonometricFunction() {
    override fun compute(x: Double, epsilon: Double): Double {
        return sin.calculate(PI/2 - x, epsilon)
    }
}