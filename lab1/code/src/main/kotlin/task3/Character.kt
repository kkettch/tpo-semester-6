package task3

import task3.enums.CharacterState

data class Character(
    val name: String,
    val lungs: Lungs,
    var state: CharacterState = CharacterState.NOT_IDENTIFIED,
    var isStanding: Boolean = false
) {

    fun standUp() {
        isStanding = true
        changeState(CharacterState.VISIBLE)
    }

    fun sitDown() {
        isStanding = false
    }

    fun changeState(newState: CharacterState) {
        state = newState
    }

    fun inhaleSmell(smell: String) {
        lungs.inhale(smell)
    }

    fun exhaleSmell() {
        lungs.exhale()
    }

    fun lightMatch(match: Match): Boolean {
        return match.turnOn()
    }

    fun putOutMatch(match: Match): Boolean {
        return match.turnOff()
    }

    fun toggleSwitch(switch: Switch, match: Match): Boolean {
        return switch.toggle(match)
    }
}