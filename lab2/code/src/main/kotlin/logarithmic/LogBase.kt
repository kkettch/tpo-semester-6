package org.example.logarithmic

class LogBase(
    private val ln: Ln = Ln(),
    private val base: Double
): LogarithmicFunction() {
    override fun compute(x: Double, epsilon: Double): Double {
        return ln.calculate(x, epsilon) / ln.calculate(base, epsilon)
    }

    override fun validateInput(x: Double, epsilon: Double) {
        super.validateInput(x, epsilon)
        if (base <= 0 || base == 1.0) {
            throw IllegalArgumentException("base cannot be <= 0 or == 1: $x")
        }
    }
}