package task3

import task3.enums.LightState

interface Lightable {
    var state: LightState
    fun turnOn(): Boolean
    fun turnOff(): Boolean
}