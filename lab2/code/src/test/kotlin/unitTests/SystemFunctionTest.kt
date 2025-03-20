package unitTests

import org.example.systemFunction.SystemFunction

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.math.*

class SystemFunctionTest {

 @ParameterizedTest
 @DisplayName("Testing calculate(x)")
 @ValueSource(doubles = [-4.5, -0.1, 0.1, 5.0])
 fun `Should calculate full function`(x: Double) {
  val cot = cos(x) / sin(x)
  val sec = 1 / cos(x)
  val csc = 1 / sin(x)

  val firstPart = ((((sin(x) / cot).pow(3))/cos(x)/cot).pow(2)/(cot+cot)) + cot.pow(2)
  val secondPart = ((sec + (csc * cos(x))).pow(2))*cos(x)

  val numerator = (((log(x, 5.0) + log(x, 3.0)) + log(x, 2.0))- ln(x)) * ln(x)
  val denominator = (log(x, 5.0) * log(x, 2.0)) * log(x, 2.0)

  val expected = if (x <= 0)
   (firstPart - secondPart)
  else
   numerator / denominator

  val actual = SystemFunction().calculate(x, 0.001)

  assertEquals(expected, actual, 0.001, "Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing invalid input for calculateTrigPart(x)")
 @ValueSource(doubles = [0.0, -PI/2, -PI, -3*PI/2, -2*PI])
 fun `Should not calculate calculateTrigPart(x)`(x: Double) {
  val epsilon = 0.001

  assertThrows<IllegalArgumentException> {
   SystemFunction().calculate(x, epsilon)
  }
 }

 @ParameterizedTest
 @DisplayName("Testing calculateTrigPart(x)")
 @ValueSource(doubles = [0.1, -PI/2 - 0.1, -PI/2 + 0.1, -PI-1, -PI+1, -3*PI/2 - 0.1, -3*PI/2 + 0.1, -2*PI - 1, -2* PI + 1, 0.9081, 2.22077, 3.91591, 5.44063])
 fun `Should calculate calculateTrigPart(x)`(x: Double) {

  val cot = cos(x) / sin(x)
  val sec = 1 / cos(x)
  val csc = 1 / sin(x)

  val firstPart = ((((sin(x) / cot).pow(3))/cos(x)/cot).pow(2)/(cot+cot)) + cot.pow(2)
  val secondPart = ((sec + (csc * cos(x))).pow(2))*cos(x)

  val expected = firstPart - secondPart
  val actual = SystemFunction().calculateTrigPart(x, 0.01)

  assertEquals(expected, actual, 0.01,"Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing invalid input for calculateLogPart(x)")
 @ValueSource(doubles = [1.0])
 fun `Should not calculate calculateLogPart(x)`(x: Double) {
  val epsilon = 0.001
  assertThrows<IllegalArgumentException> {
   SystemFunction().calculateLogPart(x, epsilon)
  }
 }

 @ParameterizedTest
 @DisplayName("Testing calculateLogPart(x)")
 @ValueSource(doubles = [0.1, 0.5, 0.9, 1.1, 5.0])
 fun `Should calculate calculateLogPart(x)`(x: Double) {
  val numerator = (((log(x, 5.0) + log(x, 3.0)) + log(x, 2.0))- ln(x)) * ln(x)
  val denominator = (log(x, 5.0) * log(x, 2.0)) * log(x, 2.0)

  val expected = numerator / denominator
  val actual = SystemFunction().calculateLogPart(x, 0.001)

  assertEquals(expected, actual, 0.001,"Expected: $expected, actual: $actual")
 }
}