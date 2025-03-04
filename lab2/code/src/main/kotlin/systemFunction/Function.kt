package org.example.systemFunction

interface Function {
    fun calculate(x: Double, epsilon: Double = 1e-10): Double
}