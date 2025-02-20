package task3

import task3.enums.LightState

class Bulb : Lightable {
    override var state: LightState = LightState.OFF

    override fun turnOn(): Boolean {
        if (state == LightState.OFF) {
            state = LightState.ON
            return true
        }
        return false
    }

    override fun turnOff(): Boolean {
        state = LightState.OFF
        return true
    }
}