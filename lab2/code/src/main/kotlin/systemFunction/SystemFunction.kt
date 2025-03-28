package org.example.systemFunction

import org.example.logarithmic.*
import org.example.trigonometric.*
import kotlin.math.PI
import kotlin.math.pow

class SystemFunction(
    private val ln: Ln = Ln(),
    private val log2: LogBase = LogBase(ln, 2.0),
    private val log3: LogBase = LogBase(ln, 3.0),
    private val log5: LogBase = LogBase(ln, 5.0),

    private val sin: Sin = Sin(),
    private val cos: Cos = Cos(sin),
    private val cot: Cot = Cot(sin, cos),
    private val sec: Sec = Sec(cos),
    private val csc: Csc = Csc(sin)
) : Function {

    override fun calculate(x: Double, epsilon: Double): Double {
        return if (x <= 0) {
            calculateTrigPart(x, epsilon)
        } else {
            calculateLogPart(x, epsilon)
        }
    }


    fun calculateLogPart(x: Double, epsilon: Double = 1e-15): Double {
        if (x == 1.0) throw IllegalArgumentException("x cannot be 1: $x")
        val lnX = ln.calculate(x, epsilon)
        val log2X = log2.calculate(x, epsilon)
        val log3X = log3.calculate(x, epsilon)
        val log5X = log5.calculate(x, epsilon)

        val numerator = ((log5X + log3X + log2X) - lnX) * lnX
        val denominator = (log5X * log2X) * log2X

        return numerator / denominator
    }

    fun calculateTrigPart(x: Double, epsilon: Double = 1e-15): Double {
        val remainder = (x % (2 * PI) + 2 * PI) % (2 * PI)
        if ((x / PI) % 1 == 0.0 || ((x / PI) + 0.5) % 1 == 0.0) {
            throw IllegalArgumentException("Invalid x: $x")
        }

        val sinX = sin.calculate(x, epsilon)
        val cosX = cos.calculate(x, epsilon)
        val cotX = cot.calculate(x, epsilon)
        val secX = sec.calculate(x, epsilon)
        val cscX = csc.calculate(x, epsilon)

        val firstPart = (((((sinX / cotX).pow(3) / cosX) / cotX).pow(2)) / (cotX + cotX)) + cotX.pow(2)
        val secondPart = ((secX + (cscX * cosX)).pow(2)) * cosX

        return firstPart - secondPart
    }
}