package task3

import task3.exceptions.ImpossibleToInhaleException

class Lungs {
    private val absorbedSmells = mutableListOf<String>()

    fun inhale(smell: String) {
        absorbedSmells.add(smell)
        if (absorbedSmells.size > 3) {
            throw ImpossibleToInhaleException("The lungs are overloaded with smells! It's hard to breathe!")
        }
    }

    fun exhale() {
        absorbedSmells.clear()
    }

    fun getAbsorbedSmells(): List<String> = absorbedSmells.toList()
}