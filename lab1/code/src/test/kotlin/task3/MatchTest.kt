package task3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import task3.enums.LightState

class MatchTest {

    @Test
    fun `test turnOn with already turned on Match`() {
        val match = Match()
        match.turnOn()
        assertFalse(match.turnOn())
    }

    @Test
    fun `test burn with turned off Match`() {
        val match = Match()
        assertFalse(match.burn())
    }

    @Test
    fun `test match lights up and burns`() {
    val match = Match()
    assertTrue(match.turnOn())
    assertEquals(LightState.ON, match.state)
    repeat(5) { match.burn() }
    assertEquals(LightState.OFF, match.state)
    }
}