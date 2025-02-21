package task3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import task3.enums.CharacterState

class CloudOfAirTest {

    @Test
    fun testFillWithSmell() {
        val cloud = CloudOfAir(id = 1)
        cloud.fillWithSmell("fresh")

        assertEquals("fresh", cloud.smell)
    }

    @Test
    fun testModifyCharacterWhenSmellDefinedAndIntensityGreaterThan3() {
        val lungs = Lungs()
        val character = Character("Artur", lungs)
        val cloud = CloudOfAir(id = 1, intensity = 4, smell = "fresh")

        cloud.modify(character)
        assertEquals(CharacterState.INVISIBLE, character.state)
    }

    @Test
    fun testModifyCharacterWhenSmellUndefined() {
        val lungs = Lungs()
        val character = Character("Artur", lungs, CharacterState.SCARED)
        val cloud = CloudOfAir(id = 1, intensity = 4)

        cloud.modify(character)
        assertEquals(CharacterState.SCARED, character.state)
    }

    @Test
    fun testModifyCharacterWhenIntensityLessThanOrEqualTo3() {
        val lungs = Lungs()
        val character = Character("Artur", lungs, CharacterState.NORMAL)
        val cloud = CloudOfAir(id = 1, intensity = 3, smell = "fresh")

        cloud.modify(character)
        assertEquals(CharacterState.NORMAL, character.state)
    }
}






