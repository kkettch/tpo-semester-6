package unitTests


import integrationTest.FunctionIntegrationTest
import integrationTest.FunctionIntegrationTest.Companion
import org.example.logarithmic.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.log

class LogarithmicFunctionTest {

    companion object {
        private val lnMockData: MutableMap<Double, Double> = mutableMapOf()
        private val log2MockData: MutableMap<Double, Double> = mutableMapOf()
        private val log3MockData: MutableMap<Double, Double> = mutableMapOf()
        private val log5MockData: MutableMap<Double, Double> = mutableMapOf()

        @BeforeAll
        @JvmStatic
        fun loadMockData() {
            loadMockDataFromFile("/CsvFiles/lnIn.csv", lnMockData)
            loadMockDataFromFile("/CsvFiles/log2In.csv", log2MockData)
            loadMockDataFromFile("/CsvFiles/log3In.csv", log3MockData)
            loadMockDataFromFile("/CsvFiles/log5In.csv", log5MockData)
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
    @DisplayName("Testing ln(x)")
    @ValueSource(doubles = [0.1, 0.3, 1.0, 10.0])
    fun `Should calculate ln(x)`(x: Double) {
        val epsilon = 0.001

        val lnMock: Ln = mock()

        whenever(lnMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            lnMockData[inputX] ?: throw IllegalArgumentException("No mock data for x = $inputX")
        }

        val expected = lnMock.calculate(x)
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