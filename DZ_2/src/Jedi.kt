class Jedi (
    name : String,
    private val weapon: Weapon? = null,
): Character(name,400, Side.LIGHT),IForceUser{

    override fun attack(target: Character){
        val damage = weapon?.damage ?: 5


        val weaponName = weapon?.type ?: "bare hands"
        println("$name attacked ${target.name} with $weaponName")
        target.takeDamage(damage)
    }

    override val forceSide: String = "Light"

    override fun forceAttack(target: Character) {
        println("$name used Force attack on ${target.name}")
        target.takeDamage(100)
    }

}