package trigonometric

import org.example.trigonometric.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class TrigonometricFunctionTest {

 @ParameterizedTest
 @DisplayName("Testing csc(x) with invalid input")
 @ValueSource(doubles = [-PI, 0.0, PI])
 fun `Should not calculate csc(x)`(x: Double) {
  assertThrows<IllegalArgumentException> {
   Csc(Sin()).calculate(x, 0.001)
  }
 }

 @ParameterizedTest
 @DisplayName("Testing csc(x) with valid input")
 @ValueSource(doubles = [0.001, 0.2, PI/2, 2.9, PI - 0.001])
 fun `Should calculate csc(x)`(x: Double) {
  val epsilon = 0.001
  val expected = 1 / sin(x)
  val actual = Csc(Sin()).calculate(x, epsilon)
  assertTrue(abs(expected - actual) < epsilon, "Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing sec(x) with invalid input")
 @ValueSource(doubles = [-PI/2, PI/2])
 fun `Should not calculate sec(x)`(x: Double) {
  assertThrows<IllegalArgumentException> {
   Sec(Cos(Sin())).calculate(x, 0.001)
  }
 }

 @ParameterizedTest
 @DisplayName("Testing sec(x) with valid input")
 @ValueSource(doubles = [-3*PI/2 + 0.001, -4.7, PI, 1.7, PI/2 - 0.001])
 fun `Should calculate sec(x)`(x: Double) {
  val epsilon = 0.001
  val expected = 1 / cos(x)
  val actual = Sec(Cos(Sin())).calculate(x, epsilon)
  assertTrue(abs(expected - actual) < epsilon, "Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing cot(x) with invalid input")
 @ValueSource(doubles = [-PI, 0.0, PI])
 fun `Should not calculate cot(x)`(x: Double) {
  assertThrows<IllegalArgumentException> {
   Cot(Sin(), Cos(Sin())).calculate(x, 0.001)
  }
 }

 @ParameterizedTest
 @DisplayName("Testing cot(x) with valid input")
 @ValueSource(doubles = [0.001, 0.5, PI / 2, 2.5, PI - 0.001])
 fun `Should calculate cot(x)`(x: Double) {
  val epsilon = 0.001
  val expected = cos(x) / sin(x)
  val actual = Cot(Sin(), Cos(Sin())).calculate(x, epsilon)
  assertTrue(abs(expected - actual) < epsilon, "Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing cos(x)")
 @ValueSource(doubles = [0.0, 0.9, PI / 2, 2.3, PI])
 fun `Should calculate cos(x)`(x: Double) {
  val epsilon = 0.001
  val expected = cos(x)
  val actual = Cos(Sin()).calculate(x, epsilon)
  assertTrue(abs(expected - actual) < epsilon, "Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing sin(x)")
 @ValueSource(doubles = [-1.5 * PI, -PI / 2, -0.7, 0.0, 0.6, PI / 2])
 fun `Should calculate sin(x)`(x: Double) {
  val epsilon = 0.001
  val expected = sin(x)
  val actual = Sin().calculate(x, epsilon)
  assertTrue(abs(expected - actual) < epsilon, "Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing invalid x input")
 @ValueSource(doubles = [Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY])
 fun `should throw exception when x is invalid`(x: Double) {
  assertThrows<IllegalArgumentException> {
   Sin().calculate(x, 0.001)
  }
 }

 @ParameterizedTest
 @DisplayName("Testing invalid epsilon input")
 @ValueSource(doubles = [Double.NaN, -0.5, 0.0, 1.0, 1.5])
 fun `should throw exception when epsilon is invalid`(epsilon: Double) {
  assertThrows<IllegalArgumentException> {
   Sin().calculate(1.0, epsilon)
  }
 }
}