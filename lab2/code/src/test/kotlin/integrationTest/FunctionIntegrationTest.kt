package integrationTest

import org.example.logarithmic.*
import org.example.trigonometric.*
import org.example.systemFunction.SystemFunction

import org.mockito.kotlin.*
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any
import kotlin.math.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource

class FunctionIntegrationTest {

    @ParameterizedTest
    @CsvFileSource(resources = ["/CsvFiles/testDataTrigonometric.csv"], numLinesToSkip = 1)
    fun `test with all trigonometric functions mocked`(x: Double, expected: Double) {
        // mocked
        val sinMock: Sin = mock()
        val cosMock: Cos = mock()
        val cotMock: Cot = mock()
        val secMock: Sec = mock()
        val cscMock: Csc = mock()

        whenever(sinMock.calculate(any(), any())).thenAnswer { sin(it.arguments[0] as Double) }
        whenever(cosMock.calculate(any(), any())).thenAnswer { cos(it.arguments[0] as Double) }
        whenever(cotMock.calculate(any(), any())).thenAnswer { 1 / tan(it.arguments[0] as Double) }
        whenever(secMock.calculate(any(), any())).thenAnswer { 1 / cos(it.arguments[0] as Double) }
        whenever(cscMock.calculate(any(), any())).thenAnswer { 1 / sin(it.arguments[0] as Double) }

        val systemFunction = SystemFunction(
            sin = sinMock,
            cos = cosMock,
            cot = cotMock,
            sec = secMock,
            csc = cscMock
        )

        val result = systemFunction.calculate(x, 0.001)

        assertEquals(expected, result, 0.001)

        verify(sinMock).calculate(eq(x), any())
        verify(cosMock).calculate(eq(x), any())
        verify(cotMock).calculate(eq(x), any())
        verify(secMock).calculate(eq(x), any())
        verify(cscMock).calculate(eq(x), any())
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

        whenever(sinMock.calculate(any(), any())).thenAnswer { sin(it.arguments[0] as Double) }
        whenever(cosMock.calculate(any(), any())).thenAnswer { cos(it.arguments[0] as Double) }
        whenever(cscMock.calculate(any(), any())).thenAnswer { 1 / sin(it.arguments[0] as Double) }

        val systemFunction = SystemFunction(
            sin = sinMock,
            cos = cosMock,
            cot = cotReal,
            sec = secReal,
            csc = cscMock
        )

        val result = systemFunction.calculate(x, 0.01)

        assertEquals(expected, result, 0.01)

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
        val cotReal = Cot(sinMock, cosReal)
        val secReal = Sec(cosReal)
        val cscReal = Csc(sinMock)

        whenever(sinMock.calculate(any(), any())).thenAnswer { sin(it.arguments[0] as Double) }

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
        val cosReal = Cos(sinReal)
        val cotReal = Cot(sinReal, cosReal)
        val secReal = Sec(cosReal)
        val cscReal = Csc(sinReal)

        val systemFunction = SystemFunction(
            sin = sinReal,
            cos = cosReal,
            cot = cotReal,
            sec = secReal,
            csc = cscReal
        )

        val result = systemFunction.calculate(x, 0.001)

        assertEquals(expected, result, 0.001)
    }

    @ParameterizedTest
    @ValueSource(doubles = [0.0, -PI/2, -PI, -3*PI/2])
    fun `test with all trigonometric functions mocked with invalid input`(x: Double) {
        // mocked
        val sinMock: Sin = mock()
        val cosMock: Cos = mock()
        val cotMock: Cot = mock()
        val secMock: Sec = mock()
        val cscMock: Csc = mock()

        whenever(sinMock.calculate(any(), any())).thenAnswer { sin(it.arguments[0] as Double) }
        whenever(cosMock.calculate(any(), any())).thenAnswer { cos(it.arguments[0] as Double) }
        whenever(cotMock.calculate(any(), any())).thenAnswer { 1 / tan(it.arguments[0] as Double) }
        whenever(secMock.calculate(any(), any())).thenAnswer { 1 / cos(it.arguments[0] as Double) }
        whenever(cscMock.calculate(any(), any())).thenAnswer { 1 / sin(it.arguments[0] as Double) }

        val systemFunction = SystemFunction(
            sin = sinMock,
            cos = cosMock,
            cot = cotMock,
            sec = secMock,
            csc = cscMock
        )

        assertThrows<IllegalArgumentException> {
            systemFunction.calculate(x, 0.001)
        }

        verify(sinMock, times(0)).calculate(any(), any())
        verify(cosMock, times(0)).calculate(any(), any())
        verify(cscMock, times(0)).calculate(any(), any())
        verify(cotMock, times(0)).calculate(any(), any())
        verify(secMock, times(0)).calculate(any(), any())
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/CsvFiles/testDataLogarithmic.csv"], numLinesToSkip = 1)
    fun `test with all logarithmic functions mocked`(x: Double, expected: Double) {
        // mocked
        val lnMock: Ln = mock()
        val log2Mock: LogBase = mock()
        val log3Mock: LogBase = mock()
        val log5Mock: LogBase = mock()

        whenever(lnMock.calculate(any(), any())).thenAnswer { ln(it.arguments[0] as Double) }
        whenever(log2Mock.calculate(any(), any())).thenAnswer { ln(it.arguments[0] as Double) / ln(2.0) }
        whenever(log3Mock.calculate(any(), any())).thenAnswer { ln(it.arguments[0] as Double) / ln(3.0) }
        whenever(log5Mock.calculate(any(), any())).thenAnswer { ln(it.arguments[0] as Double) / ln(5.0) }

        val systemFunction = SystemFunction(
            ln = lnMock,
            log2 = log2Mock,
            log3 = log3Mock,
            log5 = log5Mock
        )
        val result = systemFunction.calculate(x, 0.001)

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

        whenever(lnMock.calculate(any(), any())).thenAnswer { ln(it.arguments[0] as Double) }

        val systemFunction = SystemFunction(
            ln = lnMock,
            log2 = log2Real,
            log3 = log3Real,
            log5 = log5Real
        )
        val result = systemFunction.calculate(x, 0.001)

        assertEquals(expected, result, 0.001)

        verify(lnMock, times(7)).calculate(any(), any())
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/CsvFiles/testDataLogarithmic.csv"], numLinesToSkip = 1)
    fun `test logarithmic with all real`(x: Double, expected: Double) {
        // real
        val lnReal: Ln = Ln()
        val log2Real = LogBase(lnReal, 2.0)
        val log3Real = LogBase(lnReal, 3.0)
        val log5Real = LogBase(lnReal, 5.0)

        val systemFunction = SystemFunction(
            ln = lnReal,
            log2 = log2Real,
            log3 = log3Real,
            log5 = log5Real
        )
        val result = systemFunction.calculate(x, 0.001)

        assertEquals(expected, result, 0.001)
    }

    @ParameterizedTest
    @ValueSource(doubles = [0.0, 1.0])
    fun `test with all logarithmic functions mocked with invalid input`(x: Double) {
        // mocked
        val lnMock: Ln = mock()
        val log2Mock: LogBase = mock()
        val log3Mock: LogBase = mock()
        val log5Mock: LogBase = mock()

        whenever(lnMock.calculate(any(), any())).thenAnswer { ln(it.arguments[0] as Double) }
        whenever(log2Mock.calculate(any(), any())).thenAnswer { ln(it.arguments[0] as Double) / ln(2.0) }
        whenever(log3Mock.calculate(any(), any())).thenAnswer { ln(it.arguments[0] as Double) / ln(3.0) }
        whenever(log5Mock.calculate(any(), any())).thenAnswer { ln(it.arguments[0] as Double) / ln(5.0) }

        val systemFunction = SystemFunction(
            ln = lnMock,
            log2 = log2Mock,
            log3 = log3Mock,
            log5 = log5Mock
        )

        assertThrows<IllegalArgumentException> {
            systemFunction.calculate(x, 0.001)
        }

        verify(lnMock, times(0)).calculate(any(), any())
        verify(log2Mock, times(0)).calculate(any(), any())
        verify(log3Mock, times(0)).calculate(any(), any())
        verify(log5Mock, times(0)).calculate(any(), any())
    }

}