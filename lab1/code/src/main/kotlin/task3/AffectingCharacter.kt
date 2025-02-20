package task3

interface AffectingCharacter {
    fun affect(character: Character, intensity: Int, context: String)
}