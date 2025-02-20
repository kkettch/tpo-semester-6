package task3

import task3.enums.EffectType

abstract class AtmosphericEffect(
    open val id: Int,
    open var intensity: Int = 0,
    open val type: EffectType
) : AffectingCharacter {

    abstract fun modify(character: Character)

    override fun affect(character: Character, intensity: Int, context: String) {
        this.intensity = intensity
        modify(character)
    }
}