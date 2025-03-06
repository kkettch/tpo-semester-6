package unitTests


import org.example.logarithmic.*
import org.junit.jupiter.api.Assertions.assertTrue

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.log

class LogarithmicFunctionTest {

    @ParameterizedTest
    @DisplayName("Testing invalid input for lоgBase(x)")
    @ValueSource(doubles = [-5.0, 0.0, 1.0])
    fun `Should not calculate logBase(x)`(base: Double) {
        val epsilon = 0.001
        val x = 3.0
        assertThrows<IllegalArgumentException> {
            LogBase(Ln(), base).calculate(x, epsilon)
        }
    }

    @ParameterizedTest
    @DisplayName("Testing lоgBase(x)")
    @ValueSource(doubles = [1.0, 3.0, 10.0])
    fun `Should calculate logBase(x)`(x: Double) {
        val epsilon = 0.001
        val base = 3.0
        val expected = log(x, base)
        val actual = LogBase(Ln(), base).calculate(x, epsilon)
        assertTrue(abs(expected - actual) < epsilon, "Expected: $expected, actual: $actual")
    }

    @ParameterizedTest
    @DisplayName("Testing ln(x)")
    @ValueSource(doubles = [0.001, 0.3, 1.0, 1000.0])
    fun `Should calculate ln(x)`(x: Double) {
        val epsilon = 0.00001
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