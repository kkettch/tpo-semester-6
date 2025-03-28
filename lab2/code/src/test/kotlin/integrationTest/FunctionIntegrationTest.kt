package integrationTest

import org.example.logarithmic.*
import org.example.trigonometric.*
import org.example.systemFunction.SystemFunction

import org.mockito.kotlin.*
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any
import kotlin.math.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource

class FunctionIntegrationTest {

    companion object {
        private val sinMockData: MutableMap<Double, Double> = mutableMapOf()
        private val cosMockData: MutableMap<Double, Double> = mutableMapOf()
        private val cotMockData: MutableMap<Double, Double> = mutableMapOf()
        private val secMockData: MutableMap<Double, Double> = mutableMapOf()
        private val cscMockData: MutableMap<Double, Double> = mutableMapOf()
        private val lnMockData: MutableMap<Double, Double> = mutableMapOf()
        private val log2MockData: MutableMap<Double, Double> = mutableMapOf()
        private val log3MockData: MutableMap<Double, Double> = mutableMapOf()
        private val log5MockData: MutableMap<Double, Double> = mutableMapOf()

        @BeforeAll
        @JvmStatic
        fun loadMockData() {
            loadMockDataFromFile("/CsvFiles/sinIn.csv", sinMockData)
            loadMockDataFromFile("/CsvFiles/cosIn.csv", cosMockData)
            loadMockDataFromFile("/CsvFiles/cotIn.csv", cotMockData)
            loadMockDataFromFile("/CsvFiles/secIn.csv", secMockData)
            loadMockDataFromFile("/CsvFiles/cscIn.csv", cscMockData)
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
    @CsvFileSource(resources = ["/CsvFiles/testDataTrigonometric.csv"], numLinesToSkip = 1)
    fun `test with all trigonometric functions mocked`(x: Double, expected: Double) {

        // mocked
        val sinMock: Sin = mock()
        val cosMock: Cos = mock()
        val cotMock: Cot = mock()
        val secMock: Sec = mock()
        val cscMock: Csc = mock()

        whenever(sinMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            sinMockData[inputX] ?: throw IllegalArgumentException("No sin mock data for x = $inputX")
        }

        whenever(cosMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            cosMockData[inputX] ?: throw IllegalArgumentException("No cos mock data for x = $inputX")
        }

        whenever(cotMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            cotMockData[inputX] ?: throw IllegalArgumentException("No cot mock data for x = $inputX")
        }

        whenever(secMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            secMockData[inputX] ?: throw IllegalArgumentException("No sec mock data for x = $inputX")
        }

        whenever(cscMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            cscMockData[inputX] ?: throw IllegalArgumentException("No sec mock data for x = $inputX")
        }

        val systemFunction = SystemFunction(
            sin = sinMock,
            cos = cosMock,
            cot = cotMock,
            sec = secMock,
            csc = cscMock
        )

        val result = systemFunction.calculate(x)

        assertEquals(expected, result, 0.001)

        verify(sinMock, atLeastOnce()).calculate(any(), any())
        verify(cosMock, atLeastOnce()).calculate(any(), any())
        verify(cotMock, atLeastOnce()).calculate(any(), any())
        verify(secMock, atLeastOnce()).calculate(any(), any())
        verify(cscMock, atLeastOnce()).calculate(any(), any())
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/CsvFiles/testDataTrigonometric.csv"], numLinesToSkip = 1)
    fun `test trigonometric with real cot and sec, mocked sin, cos, csc`(x: Double, expected: Double) {
        // mocked
        val sinMock: Sin = mock()
        val cosMock: Cos = mock()
        val cscMock: Csc = mock()

        // real
        val cotReal = Cot(sinMock, cosMock)
        val secReal = Sec(cosMock)

        whenever(sinMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            sinMockData[inputX] ?: throw IllegalArgumentException("No sin mock data for x = $inputX")
        }

        whenever(cosMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            cosMockData[inputX] ?: throw IllegalArgumentException("No cos mock data for x = $inputX")
        }

        whenever(cscMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            cscMockData[inputX] ?: throw IllegalArgumentException("No sec mock data for x = $inputX")
        }

        val systemFunction = SystemFunction(
            sin = sinMock,
            cos = cosMock,
            cot = cotReal,
            sec = secReal,
            csc = cscMock
        )

        val result = systemFunction.calculate(x)

        assertEquals(expected, result, 0.001)

        verify(sinMock, times(2)).calculate(any(), any())
        verify(cosMock, times(3)).calculate(any(), any())
        verify(cscMock, times(1)).calculate(any(), any())
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/CsvFiles/testDataTrigonometric.csv"], numLinesToSkip = 1)
    fun `test trigonometric with real cot, sec, cos and csc, mocked sin`(x: Double, expected: Double) {
        // mocked
        val sinMock: Sin = mock()

        // real
        val cosReal = Cos(sinMock)
        val cotReal = Cot(sinMock)
        val secReal = Sec(cosReal)
        val cscReal = Csc(sinMock)

        whenever(sinMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            sinMockData[inputX] ?: throw IllegalArgumentException("No sin mock data for x = $inputX")
        }

        val systemFunction = SystemFunction(
            sin = sinMock,
            cos = cosReal,
            cot = cotReal,
            sec = secReal,
            csc = cscReal
        )

        val result = systemFunction.calculate(x, 0.001)

        assertEquals(expected, result, 0.001)

        verify(sinMock, times(6)).calculate(any(), any())
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/CsvFiles/testDataTrigonometric.csv"], numLinesToSkip = 1)
    fun `test trigonometric with all real functions`(x: Double, expected: Double) {
        // real
        val sinReal = Sin()
        val cosReal = Cos()
        val cotReal = Cot()
        val secReal = Sec()
        val cscReal = Csc()

        val systemFunction = SystemFunction(
            sin = sinReal,
            cos = cosReal,
            cot = cotReal,
            sec = secReal,
            csc = cscReal
        )

        val result = systemFunction.calculate(x)

        assertEquals(expected, result, 0.001)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/CsvFiles/testDataLogarithmic.csv"], numLinesToSkip = 1)
    fun `test with all logarithmic functions mocked`(x: Double, expected: Double) {
        // mocked
        val lnMock: Ln = mock()
        val log2Mock: LogBase = mock()
        val log3Mock: LogBase = mock()
        val log5Mock: LogBase = mock()

        whenever(lnMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            lnMockData[inputX] ?: throw IllegalArgumentException("No mock data for x = $inputX")
        }

        whenever(log2Mock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            log2MockData[inputX] ?: throw IllegalArgumentException("No mock data for x = $inputX")
        }

        whenever(log3Mock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            log3MockData[inputX] ?: throw IllegalArgumentException("No mock data for x = $inputX")
        }

        whenever(log5Mock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            log5MockData[inputX] ?: throw IllegalArgumentException("No mock data for x = $inputX")
        }


        val systemFunction = SystemFunction(
            ln = lnMock,
            log2 = log2Mock,
            log3 = log3Mock,
            log5 = log5Mock
        )
        val result = systemFunction.calculate(x)

        assertEquals(expected, result, 0.001)

        verify(lnMock).calculate(eq(x), any())
        verify(log2Mock).calculate(eq(x), any())
        verify(log3Mock).calculate(eq(x), any())
        verify(log5Mock).calculate(eq(x), any())
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/CsvFiles/testDataLogarithmic.csv"], numLinesToSkip = 1)
    fun `test logarithmic with real log2, log3 and log5, ln - mocked`(x: Double, expected: Double) {
        // mocked
        val lnMock: Ln = mock()

        // real
        val log2Real = LogBase(lnMock, 2.0)
        val log3Real = LogBase(lnMock, 3.0)
        val log5Real = LogBase(lnMock, 5.0)

        whenever(lnMock.calculate(any(), any())).thenAnswer { invocation ->
            val inputX = invocation.arguments[0] as Double
            lnMockData[inputX] ?: throw IllegalArgumentException("No mock data for x = $inputX")
        }

        val systemFunction = SystemFunction(
            ln = lnMock,
            log2 = log2Real,
            log3 = log3Real,
            log5 = log5Real
        )
        val result = systemFunction.calculate(x)

        assertEquals(expected, result, 0.001)

        verify(lnMock, times(7)).calculate(any(), any())
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/CsvFiles/testDataLogarithmic.csv"], numLinesToSkip = 1)
    fun `test logarithmic with all real`(x: Double, expected: Double) {
        // real
        val lnReal = Ln()
        val log2Real = LogBase(base = 2.0)
        val log3Real = LogBase(base = 3.0)
        val log5Real = LogBase(base = 5.0)

        val systemFunction = SystemFunction(
            ln = lnReal,
            log2 = log2Real,
            log3 = log3Real,
            log5 = log5Real
        )
        val result = systemFunction.calculate(x)

        assertEquals(expected, result, 0.001)
    }

}