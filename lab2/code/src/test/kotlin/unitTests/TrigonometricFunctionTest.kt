package unitTests

import integrationTest.FunctionIntegrationTest
import integrationTest.FunctionIntegrationTest.Companion
import org.example.trigonometric.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class TrigonometricFunctionTest {

 companion object {
  private val sinMockData: MutableMap<Double, Double> = mutableMapOf()
  private val cosMockData: MutableMap<Double, Double> = mutableMapOf()
  private val cotMockData: MutableMap<Double, Double> = mutableMapOf()
  private val secMockData: MutableMap<Double, Double> = mutableMapOf()
  private val cscMockData: MutableMap<Double, Double> = mutableMapOf()

  @BeforeAll
  @JvmStatic
  fun loadMockData() {
   loadMockDataFromFile("/CsvFiles/sinIn.csv", sinMockData)
   loadMockDataFromFile("/CsvFiles/cosIn.csv", cosMockData)
   loadMockDataFromFile("/CsvFiles/cotIn.csv", cotMockData)
   loadMockDataFromFile("/CsvFiles/secIn.csv", secMockData)
   loadMockDataFromFile("/CsvFiles/cscIn.csv", cscMockData)
  }

  private fun loadMockDataFromFile(fileName: String, dataMap: MutableMap<Double, Double>) {
   val inputStream = this::class.java.getResourceAsStream(fileName)
    ?: throw IllegalArgumentException("$fileName not found")

   inputStream.bufferedReader().useLines { lines ->
    lines.drop(1).forEach { line ->
     val (x, value) = line.split(",").map { it.trim().toDouble() }
     dataMap[x] = value
    }
   }
  }
 }

 @ParameterizedTest
 @DisplayName("Testing csc(x) with invalid input")
 @ValueSource(doubles = [-PI, 0.0, PI])
 fun `Should not calculate csc(x)`(x: Double) {
  assertThrows<IllegalArgumentException> {
   Csc().calculate(x, 0.001)
  }
 }

 @ParameterizedTest
 @DisplayName("Testing csc(x) with valid input")
 @ValueSource(doubles = [0.001, 0.2, PI/2, 2.9, PI - 0.001])
 fun `Should calculate csc(x)`(x: Double) {

  val sinMock: Sin = mock()

  whenever(sinMock.calculate(any(), any())).thenAnswer { invocation ->
   val inputX = invocation.arguments[0] as Double
   sinMockData[inputX] ?: throw IllegalArgumentException("No sin mock data for x = $inputX")
  }

  val expected = 1 / sinMock.calculate(x)
  val actual = Csc(Sin()).calculate(x)
  assertTrue(abs(expected - actual) < 0.000000001, "Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing sec(x) with invalid input")
 @ValueSource(doubles = [-PI/2, PI/2])
 fun `Should not calculate sec(x)`(x: Double) {
  assertThrows<IllegalArgumentException> {
   Sec().calculate(x, 0.001)
  }
 }

 @ParameterizedTest
 @DisplayName("Testing sec(x) with valid input")
 @ValueSource(doubles = [-3*PI/2 + 0.001, -4.7, PI, 1.7, PI/2 - 0.001])
 fun `Should calculate sec(x)`(x: Double) {

  val cosMock: Cos = mock()

  whenever(cosMock.calculate(any(), any())).thenAnswer { invocation ->
   val inputX = invocation.arguments[0] as Double
   cosMockData[inputX] ?: throw IllegalArgumentException("No cos mock data for x = $inputX")
  }

  val epsilon = 0.001
  val expected = 1 / cosMock.calculate(x)
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

  val sinMock: Sin = mock()
  val cosMock: Cos = mock()

  whenever(sinMock.calculate(any(), any())).thenAnswer { invocation ->
   val inputX = invocation.arguments[0] as Double
   sinMockData[inputX] ?: throw IllegalArgumentException("No sin mock data for x = $inputX")
  }

  whenever(cosMock.calculate(any(), any())).thenAnswer { invocation ->
   val inputX = invocation.arguments[0] as Double
   cosMockData[inputX] ?: throw IllegalArgumentException("No cos mock data for x = $inputX")
  }

  val expected = cosMock.calculate(x) / sinMock.calculate(x)
  val actual = Cot().calculate(x)
  assertTrue(abs(expected - actual) < 0.000000001, "Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing cos(x)")
 @ValueSource(doubles = [0.0, 0.9, PI / 2, 2.3, PI])
 fun `Should calculate cos(x)`(x: Double) {
  val epsilon = 0.001

  val cosMock: Cos = mock()

  whenever(cosMock.calculate(any(), any())).thenAnswer { invocation ->
   val inputX = invocation.arguments[0] as Double
   cosMockData[inputX] ?: throw IllegalArgumentException("No cos mock data for x = $inputX")
  }

  val expected = cosMock.calculate(x)
  val actual = Cos(Sin()).calculate(x, epsilon)
  assertTrue(abs(expected - actual) < epsilon, "Expected: $expected, actual: $actual")
 }

 @ParameterizedTest
 @DisplayName("Testing sin(x)")
 @ValueSource(doubles = [-1.5 * PI, -PI / 2, -0.7, 0.0, 0.6, PI / 2])
 fun `Should calculate sin(x)`(x: Double) {
  val epsilon = 0.001

  val sinMock: Sin = mock()

  whenever(sinMock.calculate(any(), any())).thenAnswer { invocation ->
   val inputX = invocation.arguments[0] as Double
   sinMockData[inputX] ?: throw IllegalArgumentException("No sin mock data for x = $inputX")
  }

  val expected = sinMock.calculate(x)
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