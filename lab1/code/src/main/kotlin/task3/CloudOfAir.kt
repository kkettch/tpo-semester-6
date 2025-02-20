package task3

import task3.enums.CharacterState
import task3.enums.EffectType

data class CloudOfAir(
    override val id: Int,
    override var intensity: Int = 0,
    var smell: String = "undefined"
) : AtmosphericEffect(id, intensity, EffectType.AIR) {

    fun fillWithSmell(smell: String) {
        this.smell = smell
    }

    override fun modify(character: Character) {
        if (smell != "undefined" && intensity > 3) {
            character.changeState(CharacterState.INVISIBLE)
        }
    }
}