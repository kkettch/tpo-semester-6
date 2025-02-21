package task3

import task3.enums.LightState
import task3.exceptions.BulbIsNotConnectedException
import task3.exceptions.MatchNotLitException

class Switch {
    private var bulb: Bulb? = null

    fun connectBulb(bulb: Bulb) {
        this.bulb = bulb
    }

    fun toggle(match: Match): Boolean {
        if (match.state == LightState.OFF) {
            throw MatchNotLitException("Impossible to toggle because there is no light")
        }

        bulb?.let {
            if (it.state == LightState.OFF) {
                return it.turnOn()
            } else {
                return it.turnOff()
            }
        } ?: throw BulbIsNotConnectedException("Bulb is not connected to the switch!")
    }
}