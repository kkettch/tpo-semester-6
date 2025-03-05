package logarithmic


import org.example.logarithmic.*
import org.junit.jupiter.api.Assertions.assertTrue

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.abs
import kotlin.math.ln

class LogarithmicFunctionTest {

    @ParameterizedTest
    @DisplayName("Testing ln(x)")
    @ValueSource(doubles = [0.001, 0.3, 1.0, 5.0])
    fun `Should calculate ln(x)`(x: Double) {
        val epsilon = 0.001
        val expected = ln(x)
        val actual = Ln().calculate(x, epsilon)
        assertTrue(abs(expected - actual) < epsilon, "Expected: $expected, actual: $actual")
    }

    @ParameterizedTest
    @DisplayName("Testing invalid x input")
    @ValueSource(doubles = [Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0])
    fun `should throw exception when x is invalid`(x: Double) {
        assertThrows<IllegalArgumentException> {
            Ln().calculate(x, 0.001)
        }
    }

    @ParameterizedTest
    @DisplayName("Testing invalid epsilon input")
    @ValueSource(doubles = [Double.NaN, -0.5, 0.0, 1.0, 1.5])
    fun `should throw exception when epsilon is invalid`(epsilon: Double) {
        assertThrows<IllegalArgumentException> {
            Ln().calculate(1.0, epsilon)
        }
    }
}