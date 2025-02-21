package task3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import task3.enums.CharacterState
import task3.enums.LightState

class CharacterTest {

    @Test
    fun `test standing up changes state to VISIBLE`() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs)
        character.standUp()
        assertTrue(character.isStanding)
        assertEquals(CharacterState.VISIBLE, character.state)
    }

    @Test
    fun `test sitting down does not change state`() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs, CharacterState.NORMAL, true)
        character.sitDown()
        assertFalse(character.isStanding)
        assertEquals(CharacterState.NORMAL, character.state)
    }

    @Test
    fun `test changing state updates character state`() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs, CharacterState.NORMAL, true)
        character.changeState(CharacterState.SCARED)
        assertEquals(CharacterState.SCARED, character.state)
    }

    @Test
    fun `testing breathing`() {
        val lungs = Lungs()
        val character = Character("Arthur", lungs)
        character.inhaleSmell("musty")

        assertTrue("musty" in character.lungs.getAbsorbedSmells())
        character.exhaleSmell()
        assertTrue(character.lungs.getAbsorbedSmells().isEmpty())
    }

    @Test
    fun `test light match`() {
        val lungs = Lungs()
        val character = Character("Ford", lungs)
        val match = Match()
        assertTrue(character.lightMatch(match))
        assertEquals(LightState.ON, match.state)
    }

    @Test
    fun `test put out match`() {
        val lungs = Lungs()
        val character = Character("Ford", lungs)
        val match = Match()
        character.lightMatch(match)
        assertTrue(character.putOutMatch(match))
        assertEquals(LightState.OFF, match.state)
    }

    @Test
    fun `test toggle switch with lit match`() {
        val lungs = Lungs()
        val character = Character("Ford", lungs)
        val match = Match()
        val switch = Switch()
        val bulb = Bulb()
        switch.connectBulb(bulb)
        character.lightMatch(match)
        assertTrue(character.toggleSwitch(switch, match))
        assertEquals(LightState.ON, bulb.state)
    }
}