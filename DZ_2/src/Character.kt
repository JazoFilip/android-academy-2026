import kotlin.math.max

abstract class Character(
    val name: String,
    protected var health: Int,
    val side: Side,
) {
    abstract fun attack(target: Character)

    fun takeDamage(damage: Int) {
        health = max(0, health - damage)
        if(!isAlive){
            println("$name is defeated in battle")
        }else{
            println("$name took $damage damage")
        }
    }

    val isAlive: Boolean
        get() = health > 0
}