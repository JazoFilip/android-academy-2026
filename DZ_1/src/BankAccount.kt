import kotlin.math.max

class BankAccount(
    val number: String
) {

    init {
        addAccount()
    }

    var balance: Double = 0.0

    fun deposit(amount: Double) {
        balance += amount
        TransactionLogger.log("Deposited $amount$, new balance: $balance$")
    }

    fun withdraw(amount: Double) {
        if ( amount > balance) {
           TransactionLogger.log("Not enough balance")
        }else{
            balance -= amount
            TransactionLogger.log("withdrawn $amount$, new balance: $balance$")
        }
    }

    companion object {
        var numberOfAccounts = 0
            private set

        fun addAccount(){
            numberOfAccounts++
        }
    }
}