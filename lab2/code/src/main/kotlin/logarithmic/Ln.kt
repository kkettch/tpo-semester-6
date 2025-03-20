package org.example.logarithmic

import kotlin.math.abs
import kotlin.math.pow


class Ln : LogarithmicFunction() {

    override fun compute(x: Double, epsilon: Double): Double {
        val newEpsilon = epsilon * 0.01
        var sum = 0.0
        var prev: Double
        var step = 1

        if (x < 1) {
            sum = -compute(1 / x, epsilon)
        }
        else if (x <= 2) {
            do {
                prev = sum
                sum += ((-1.0).pow((step - 1)) * (x - 1).pow(step)) / step
                step++
            } while (abs(prev - sum) >= newEpsilon && step < 10000)
            sum = (sum + prev) / 2
        } else {
            do {
                prev = sum
                sum += ((-1.0).pow((step - 1))) / ((x - 1).pow(step) * step)
                step++
            } while (abs(prev - sum) >= newEpsilon)
            sum += compute(x - 1, epsilon)
        }

        return sum

//        var x_2 = x
//        var epsilon_2 = epsilon
//        epsilon_2 *= 0.01
//
//        var newX: Double = epsilon_2
//        var result = 0.0
//
//        if (x_2 <= 2) {
//            x_2 -= 1
//            result = x_2
//            var iter = 2
//            while (abs(newX) >= epsilon_2) {
//                newX = x_2.pow(iter.toDouble()) / iter
//                result = if (iter % 2 == 0) (newX.let { result -= it; result }) else (newX.let { result += it; result })
//                iter += 1
//            }
//            return result
//        } else {
//            x_2 = (x_2 - 1) / (x_2 + 1)
//            var iter = 1
//            while (abs(newX) >= epsilon_2) {
//                newX = x_2.pow(iter.toDouble()) / iter
//                result += newX
//                iter += 2
//            }
//            return result * 2
//        }
    }
}