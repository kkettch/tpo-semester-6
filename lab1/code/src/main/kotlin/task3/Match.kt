package task3

import task3.enums.LightState

class Match : Lightable {
    private var burnTime: Int = 5
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
        burnTime = 0
        return true
    }

    fun burn(): Boolean {
        if (state == LightState.ON) {
            burnTime -= 1
            if (burnTime <= 0) {
                turnOff()
            }
            return true
        }
        return false
    }
}