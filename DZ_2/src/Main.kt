//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val luke = Jedi("Luke Skywalker",Weapon("Green Lightsaber",150))
    val yoda = Jedi("Master Yoda",Weapon("Green Lightsaber",150))

    val forceChoke: (Character) -> Unit = {target ->
        println("force chocked ${target.name}")
        target.takeDamage(100)
    }
    val forceLightning: (Character) -> Unit = {target ->
        println("used force lightning on ${target.name}")
        target.takeDamage(150)
    }

    val vader = SithLord("Darth Vader",Weapon("Red Lightsaber",150),forceChoke )
    val darthSidius = SithLord("Darth Sidius",Weapon("Red Lightsaber",150),forceLightning )

    val trooper1 = Stormtrooper("TK-421")
    val trooper2 = Stormtrooper("TK-422")
    val trooper3 = Stormtrooper("TK-423")

    val allFighters = listOf(luke, yoda, vader,darthSidius, trooper1, trooper2,trooper3)
    val manager = BattleManager("Death Star")

    // Run the battle turn
    manager.startBattle(allFighters)

    // Check results
    val survivors = allFighters.filter { it.isAlive }.map { it.name }
    println("Survivors: $survivors")
}