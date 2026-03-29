class SithLord(
    name: String,
    private val weapon: Weapon? = null,
    private val forcePower: (Character) -> Unit,
): Character(name,500, Side.DARK),IForceUser {
    override fun attack(target: Character){
        val damage = weapon?.damage ?: 5

        val weaponName = weapon?.type ?: "bare hands"
        println("$name attacked ${target.name} with $weaponName")
        target.takeDamage(damage)
    }

    override val forceSide: String = "Dark"

    override fun forceAttack(target: Character) {
        print("$name ")
        forcePower(target)
    }
}