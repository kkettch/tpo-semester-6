package task3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import task3.exceptions.ImpossibleToInhaleException

class LungsTest {
    @Test
    fun `test inhaling smells`() {
        val lungs = Lungs()
        lungs.inhale("musty")
        assertTrue("musty" in lungs.getAbsorbedSmells())
    }

    @Test
    fun `test lungs throw exception when overloaded`() {
        val lungs = Lungs()
        lungs.inhale("musty")
        lungs.inhale("disgusting")
        lungs.inhale("deadly")
        assertThrows<ImpossibleToInhaleException> { lungs.inhale("pleasant") }
    }
}