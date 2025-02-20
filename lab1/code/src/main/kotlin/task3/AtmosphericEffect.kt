package task3

import task3.enums.EffectType

abstract class AtmosphericEffect(
    open val id: Int,
    open var intensity: Int = 0,
    open val type: EffectType
) {
    abstract fun modify(character: Character)
}