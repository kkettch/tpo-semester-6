package task3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import task3.enums.LightState

class BulbTest {

    @Test
    fun `test bulb turns on and off`() {
        val bulb = Bulb()
        assertTrue(bulb.turnOn())
        assertEquals(LightState.ON, bulb.state)
        assertTrue(bulb.turnOff())
        assertEquals(LightState.OFF, bulb.state)
    }

    @Test
    fun `test bulb does not turn on if already on`() {
        val bulb = Bulb()
        bulb.turnOn()
        assertFalse(bulb.turnOn())
    }
}
