class BattleManager(
    private val warzone: String
) {
    fun startBattle(fighters: List<Character>) {
        println("Battle on $warzone")

        val activeFighters = fighters.filter { it.isAlive }
        val (lightSide, darkSide) = activeFighters.partition { it.side == Side.LIGHT }

        when {
            lightSide.isEmpty() -> {
                println("Battle cannot proceed. Light Side is defeated!")
                return
            }
            darkSide.isEmpty() -> {
                println("Battle cannot proceed. Dark Side is defeated!")
                return
            }
        }

        performTurn(lightSide, darkSide, "Light Side")
        performTurn(darkSide, lightSide, "Dark Side")
        performTurn(lightSide, darkSide, "Light Side")
        performTurn(darkSide, lightSide, "Dark Side")
    }

    fun performTurn(attacking: List<Character>, defending: List<Character>, sideName: String) {
        attacking.forEach { attacker ->
            defending.filter { it.isAlive }
                .randomOrNull()
                ?.let { attacker.attack(it) }
        }

        attacking.filterIsInstance<IForceUser>().forEach { attacker ->
            defending.filter { it.isAlive }
                .randomOrNull()
                ?.let { attacker.forceAttack(it) }
        }

        println("--- End of $sideName Turn ---\n")
    }
}
