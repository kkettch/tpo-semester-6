package task3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import task3.enums.CharacterState
import task3.enums.ShadowState
import task3.exceptions.PathBlockedException
import kotlin.test.assertFailsWith

class ShadowTest {

    @Test
    fun testMoving() {
        val shadow = Shadow(id = 1)

        shadow.swing()
        assertEquals(ShadowState.BRIGHT, shadow.state)
        assertEquals(5, shadow.intensity)
        assertEquals("moving", shadow.position)

        shadow.still()
        assertEquals(ShadowState.DIM, shadow.state)
        assertEquals(4, shadow.intensity)
        assertEquals("unmoving", shadow.position)

        shadow.flicker()
        assertEquals(ShadowState.FLICKERING, shadow.state)
        assertEquals(7, shadow.intensity)
        assertEquals("flickers", shadow.position)
    }

    @Test
    fun testModifyCharacterWhenBright() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs)

        val shadow = Shadow(id = 1)
        shadow.swing()

        shadow.modify(character)
        assertEquals(CharacterState.SCARED, character.state)
    }

    @Test
    fun testModifyCharacterWhenDim() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs)

        val shadow = Shadow(id = 1)
        shadow.still()

        shadow.modify(character)
        assertEquals(CharacterState.VISIBLE, character.state)
    }

    @Test
    fun testModifyCharacterWhenFlickering() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs)

        val shadow = Shadow(id = 1)
        shadow.flicker()

        shadow.modify(character)
        assertEquals(CharacterState.DISTRACTED, character.state)
    }

    @Test
    fun testModifyCharacterWhenNotIdentified() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs)

        val shadow = Shadow(id = 1)

        shadow.modify(character)
        assertEquals(CharacterState.NORMAL, character.state)
    }

    @Test
    fun testBlockPathWhenIntensityLessThanOrEqualTo5() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs)

        val shadow = Shadow(id = 1, intensity = 5)

        shadow.blockPath(character)
        assertEquals(CharacterState.INVISIBLE, character.state)
    }

    @Test
    fun testBlockPathWhenIntensityGreaterThan5() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs)

        val shadow = Shadow(id = 1, intensity = 6)

        assertFailsWith<PathBlockedException> { shadow.blockPath(character) }
    }
}