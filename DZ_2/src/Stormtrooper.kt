class Stormtrooper(name: String) : Character(name, 40, Side.DARK) {
    override fun attack(target: Character) {
        // Classic Stormtrooper aim
        val hit = (1..10).random() > 8
        if (hit && target is Jedi) {
            if((1..100).random() > 95){
                println("$name hit a Jedi ${target.name}")
                target.takeDamage(10)
            }else{
                println("${target.name} deflected bolts")
            }
        } else if(hit){
            println("Stormtrooper actually hit someone")
            target.takeDamage(10)
        } else{
            println("Stormtrooper missed")
        }
    }
}