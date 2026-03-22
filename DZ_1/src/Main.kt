import kotlin.math.abs

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val name : String
    val surname : String
    var email : String? = null
    var age : Int? = 20

    println("Duljina emaila:")
    println(email?.length ?: 0)

    email = "jeff@gmail.com"
    println("Duljina emaila:")
    println(email?.length ?: 0)

    var drinkCode : Int = 2
    var drinkPrice : Double = 2.8
    var money : Double = 2.5

    var drink : String = when(drinkCode){
        1 -> "Voda"
        2 -> "Cola"
        3 -> "Sok"
        else -> "Kava"
    }

    if (money >= drinkPrice){
        println("$drink je natočena, ostalo je ${"%.2f".format(money - drinkPrice)} eura")
    }else{
        println("Nedostaje vam još ${"%.2f".format(abs(money - drinkPrice))} eura")
    }

    val data : List<Int> = listOf(4500,12000,8000,15000,3000,11000,9500)

    var totalSteps : Int = 0
    for(steps in data){
        totalSteps += steps
    }
    println("Broj koraka: ${totalSteps}")

    var i = 0

    while(i < data.size){
        if(data[i] > 10000) break
        i++
    }

    println("Prvi dan kada je korisnik premašio 10 000 koraka: ${i + 1} dan")

    var usernames = mutableListOf<String>("jeff_023", "MARin23_4","  fest345", "Gambatr*_34  ")

    for (username in usernames){
        var name = username
        name = filterUsername(name)
        val isUsernameValid = checUsername(name)

        println("$name is ${if(isUsernameValid) "valid" else "not valid"}")
    }


    val bankAccount1 = BankAccount("HR34534232352")
    val bankAccount2 = BankAccount("HR97538532354")
    val bankAccount3 = BankAccount("HR35534233552")

    bankAccount1.deposit(300.56)
    bankAccount2.deposit(1000.0)
    bankAccount3.deposit(10000.58)
    bankAccount3.deposit(1230.58)
    bankAccount1.withdraw(500.0)
    bankAccount2.withdraw(2000.0)
    bankAccount3.withdraw(3000.0)

    println("ukupno kreiranih racuna: ${BankAccount.numberOfAccounts}")




}

fun filterUsername(name: String) : String {
    return name.trim().lowercase()
}

fun checUsername(name : String) : Boolean {
    if (name.isEmpty()) return false

    return (name.length >= 5 || name.length <= 15) &&
            name[0].isLetter() &&
            name.all { it.isLetterOrDigit() || it == '_' } &&
            !name.contains(" ")

}