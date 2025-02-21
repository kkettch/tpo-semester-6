package task3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import task3.enums.LightState
import task3.exceptions.BulbIsNotConnectedException
import task3.exceptions.MatchNotLitException

class SwitchTest {
    @Test
    fun `test switch toggles bulb on and off`() {
         val bulb = Bulb()
         val switch = Switch()
         val match = Match()
         switch.connectBulb(bulb)
         match.turnOn()
         assertTrue(switch.toggle(match))
         assertEquals(LightState.ON, bulb.state)
         assertTrue(switch.toggle(match))
         assertEquals(LightState.OFF, bulb.state)
    }

    @Test
    fun `test switch toggles without match`() {
         val bulb = Bulb()
         val switch = Switch()
         val match = Match()
         switch.connectBulb(bulb)
         assertThrows<MatchNotLitException> { switch.toggle(match) }
    }

    @Test
    fun `test switch throws exception if no bulb connected`() {
        val switch = Switch()
        val match = Match()
        match.turnOn()
        assertThrows<BulbIsNotConnectedException> { switch.toggle(match) }
    }
}