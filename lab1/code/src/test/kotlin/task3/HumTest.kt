package task3


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import task3.enums.CharacterState

class HumTest {

    @Test
    fun testIncrease() {
        val hum = Hum(id = 1, intensity = 0)

        // Увеличиваем интенсивность
        hum.increase()

        assertEquals(1, hum.intensity)
    }

    @Test
    fun testDecrease() {
        val hum = Hum(id = 1, intensity = 1)

        // Уменьшаем интенсивность
        hum.decrease()

        assertEquals(0, hum.intensity)
    }

    @Test
    fun testModifyCharacterWhenIntensityGreaterThan5() {
        val lungs = Lungs()
        val character = Character("Artur", lungs)
        val hum = Hum(id = 1, intensity = 6)

        hum.modify(character)

        assertEquals(CharacterState.DISTRACTED, character.state)
    }

    @Test
    fun testModifyCharacterWhenIntensityLessThanOrEqualTo5() {
        val lungs = Lungs()
        val character = Character("Artur", lungs, CharacterState.NORMAL)
        val hum = Hum(id = 1, intensity = 5)

        hum.modify(character)

        assertEquals(CharacterState.NORMAL, character.state)
    }
}
