package task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.abs
import kotlin.math.atan

class SeriesExpansionTest {

    @ParameterizedTest
    @DisplayName("Testing in range (-1, 1)")
    @ValueSource(floats = [-0.999F, -0.5F, -0.001F, 0F, 0.001F, 0.5F, 0.999F])
    fun `Function arctgCalculation() should calculate arctg correctly for all the inputs`(x: Float) {
        val expected = atan(x)
        val actual = SeriesExpansion().arctgCalculation(x)
        assertTrue(abs(expected - actual) < 0.001, "Expected: $expected, actual: $actual")
    }

    @ParameterizedTest
    @DisplayName("Testing out of range (-1, 1)")
    @ValueSource(floats = [-5F, -1F, 1F, 5F])
    fun `Function arctgCalculation() should throw an error`(x: Float) {
        assertThrows<InvalidInputException> {
            SeriesExpansion().arctgCalculation(x)
        }
    }

    @ParameterizedTest
    @DisplayName("Testing invalid input (NaN, Infinity)")
    @ValueSource(floats = [Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY])
    fun `Function arctgCalculation() should throw an error for invalid input`(x: Float) {
        assertThrows<InvalidInputException> {
            SeriesExpansion().arctgCalculation(x)
        }
    }
 }
