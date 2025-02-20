package task3

import task3.enums.CharacterState
import task3.enums.EffectType

data class Hum(
    override val id: Int,
    override var intensity: Int = 0
) : AtmosphericEffect(id, intensity, EffectType.HUM) {

    fun increase() {
        intensity += 1
    }

    fun decrease() {
        intensity -= 1
    }

    override fun modify(character: Character) {
        if (intensity > 5) {
            character.changeState(CharacterState.DISTRACTED)
        }
    }
}