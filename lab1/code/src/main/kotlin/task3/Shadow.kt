package task3

import task3.enums.CharacterState
import task3.enums.EffectType
import task3.enums.ShadowState
import task3.exceptions.PathBlockedException

data class Shadow(
    override val id: Int,
    override var intensity: Int = 0,
    var state: ShadowState = ShadowState.NOT_IDENTIFIED,
    var position: String = "not identified"
) : AtmosphericEffect(id, intensity, EffectType.SHADOW) {

    fun swing() {
        state = ShadowState.BRIGHT
        intensity += 5
        position = "moving"
    }

    fun still() {
        state = ShadowState.DIM
        intensity -= 1
        position = "unmoving"
    }

    fun flicker() {
        state = ShadowState.FLICKERING
        intensity += 3
        position = "flickers"
    }

    override fun modify(character: Character) {
        when (state) {
            ShadowState.BRIGHT -> character.changeState(CharacterState.SCARED)
            ShadowState.DIM -> character.changeState(CharacterState.VISIBLE)
            ShadowState.FLICKERING -> character.changeState(CharacterState.DISTRACTED)
            else -> character.changeState(CharacterState.NORMAL)
        }
    }

    fun blockPath(character: Character) {
        if (intensity > 5) {
            throw PathBlockedException("The shadow is blocking character’s way!")
        }
        character.changeState(CharacterState.INVISIBLE)
    }
}
