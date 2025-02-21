package task1

import kotlin.math.abs

class SeriesExpansion {

    fun arctgCalculation(x: Float, epsilon: Float = 0.001F) : Float {
        var coefficient = 1
        var i = x
        var result = x

        if (x.isNaN() || x.isInfinite()) {
            throw InvalidInputException("Invalid input: x must be a finite number")
        }

        if (abs(x) >= 1) {
            throw InvalidInputException("x is out of range, correct range: (-1; 1)")
        }

        while (abs(i) > epsilon) {
            coefficient += 2
            i = -i * (x * x)
            result += i / coefficient
        }

        return result
    }

}