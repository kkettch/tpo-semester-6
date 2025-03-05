package org.example.systemFunction

import org.example.trigonometric.Cos
import org.example.trigonometric.Sin

class SystemFunction(
    private val sin: Sin,
    private val cos: Cos
): Function {
    override fun calculate(x: Double, epsilon: Double): Double {
        TODO("Not yet implemented")
    }
}