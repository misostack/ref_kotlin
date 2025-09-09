package net.jsbase.helloworld

import net.jsbase.helloworld.domains.TransactionType
import net.jsbase.helloworld.models.Transaction
import net.jsbase.helloworld.services.BalanceService
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Currency
import java.util.Locale
import java.util.UUID
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.Random

@Serializable
data class Topic(
    val id: Long,
    val name: String
)

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

fun helloWorld(args: Array<String>) {
    val defaultName = "Kotlin"
    var output = if (args.isNotEmpty()) args[0] else defaultName

    output = "Hello, $output!"

    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println(output)

    // February 15, 2016
    val kotlinFirstReleaseDate = LocalDate.parse("2016-02-15")
    val yearsSinceKotlinFirstRelease: Int = LocalDate.now().year - kotlinFirstReleaseDate.year

    println("The first version of Kotlin was released $yearsSinceKotlinFirstRelease years ago at ${kotlinFirstReleaseDate.toString()}.")

    for (i in 1..5) {
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
    }
}

fun jsonDemo(){
    // Serialization
    val json_data = Json.encodeToString(Topic(1, "Kotlin"))
    println(json_data)
    // Deserialization
    val topic = Json.decodeFromString<Topic>(json_data)
    println("id:${topic.id}\nname:${topic.name}")
}

fun newTransaction(amount: Int, date: String, type: TransactionType, description: String = ""): Transaction {
    return Transaction(
        id = UUID.randomUUID().toString(),
        amount = amount.toDouble(),
        date = java.sql.Date.valueOf(date),
        type = type,
        description = description
    )
}

fun toJSONString(transaction: Transaction): String {
    return """
        {
            "id": "${transaction.id}",
            "amount": ${transaction.amount.toBigDecimal().toPlainString()},
            "date": "${transaction.date}",
            "type": "${transaction.type.value}",
            "description": "${transaction.description}"
        }
    """.trimIndent()
}

fun formatCurrencyWithCommas(number: Number, currency: Currency): String {
    // Pick default locale from currency (fallback US if unknown)
    val locale = Locale.getAvailableLocales().firstOrNull {
        try {
            Currency.getInstance(it) == currency
        } catch (_: Exception) {
            false
        }
    } ?: Locale.US

    val formatter = NumberFormat.getCurrencyInstance(locale)

    return formatter.format(number)
}

fun balanceMananagement() {
    var currentLocale = Locale.Builder().setLanguage("vi").setRegion("VN").build()
    println("Vietnam Locale: ${currentLocale.displayName} - ${currentLocale.language} - ${currentLocale.country}")
    // currentLocale = Locale.JAPAN
    val balanceService = BalanceService(initialBalance = 0.0, currency = Currency.getInstance(currentLocale))

    // 01/01/2023 - Income - Salary - 30,000,000
    balanceService.transactions.add(newTransaction(
        30_000_000,
        "2023-01-01",
        TransactionType.INCOME,
        "Salary")
    )
    // 02/01/2023 - Expense - Rent - 5,000,000
    balanceService.transactions.add(newTransaction(
        5_000_000,
        "2023-01-02",
        TransactionType.EXPENSE,
        "Rent")
    )
    // 03/01/2023 - Expense - Groceries - 2,000,000
    balanceService.transactions.add(newTransaction(
        2_000_000,
        "2023-01-03",
        TransactionType.EXPENSE,
        "Groceries")
    )

    // test
    for (transaction in balanceService.transactions) {
        println(toJSONString(transaction))
    }

    println("Total Income: ${formatCurrencyWithCommas(balanceService.getIncomeTotal(), balanceService.currency)}")
    println("Total Expense: ${formatCurrencyWithCommas(balanceService.getExpenseTotal(), balanceService.currency)}")
    println("Balance: ${formatCurrencyWithCommas(balanceService.getBalance(), balanceService.currency)}")
}

fun sayHello(){

    var name: String = ""
    while (name.isBlank()) {
        println("What is your name?")
        name = readln()
    }
    println("Hello, $name!")
    println("Please enter your age:")
    val age = readln().toIntOrNull()
    if (age != null) {
        println("You are $age years old.")
    } else {
        println("Invalid age input.")
    }
    val fruits = listOf("Apple", "Banana", "Orange", "Grapes")
    println("Which fruit do you like?")
    for ((index, fruit) in fruits.withIndex()) {
        println("${index + 1}. $fruit")
    }
    val choice = readln().toIntOrNull()
    if (choice != null && choice in 1..fruits.size) {
        println("You like ${fruits[choice - 1]}.")
    }
    when (choice) {
        1 -> println("Apple is good for health.")
        2 -> println("Banana is rich in potassium.")
        3 -> println("Orange is a good source of vitamin C.")
        4 -> println("Grapes are high in antioxidants.")
        else -> println("No information available.")
    }
    // random a number between 1 and 100
    val randomFruit = Random().nextInt(fruits.size)
    println("How good ${fruits.elementAt(randomFruit)} is?")
    val answer = readln()
    println("You rated $randomFruit with $answer.")
    var odds = ""
    for (i in 1..10 step 2) {
        odds = "$odds $i"
    }
    println(odds)
    fruits.map { it.lowercase() }.forEach { println(it) }
    for (c in 'A' ..'Z') {
        print("$c ")
    }
}

data class Customer(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String,
){
    var address: String? = null
}

fun idioms() {
    // dtos: data transfer objects
    // pojos: plain old java objects
    // pocos: plain old old classes

    val customer = Customer(
        id=1,
        name="miso",
        email="misostack@gmail.com",
        phone="0123456789",
    )
    customer.address = "123 Main St, City, Country"
    println(customer)
    println(customer.address)

    val cloneCustomer = customer.copy(id=2,name="john doe", email = "kotlin@example.org")
    println(cloneCustomer)
    cloneCustomer.address = "321 Main St, City, Country"
    println(cloneCustomer.address)
    println(customer.address)

    // comparing two objects
    data class Point(val x: Int, val y: Int)
    val point1 = Point(1,2)
    val point2 = Point(y=2, x=1)
    println(point1.equals(point2))
    println(point1.hashCode() == point2.hashCode())
    println(point1.toString() == point2.toString())
    // destructuring
    val (id, name, email) = customer
    println("id: $id, name: $name, email: $email")

    fun getPoint(): Point {
        return Point(10, 20)
    }
    val (x, y) = getPoint()
    println("x: $x, y: $y")
    // collection of data
    val months = Array<Int>(12) { 0}
    for (i in months.indices) {
        months[i] = i + 1
    }
    months.forEach { month -> println(month) }
    val monthNames = arrayOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    monthNames.forEachIndexed { index, month -> println("${index + 1}: $month") }

    val numbers = MutableList<Int>(0){0}
    println(numbers.size)
    numbers.add(0)
    println(numbers.size)

    val daysInMonths = mapOf(
        "January" to 31,
        "February" to 28,
        "March" to 31,
        "April" to 30,
        "May" to 31,
        "June" to 30,
        "July" to 31,
        "August" to 31,
        "September" to 30,
        "October" to 31,
        "November" to 30,
        "December" to 31
    )
    for ((month, days) in daysInMonths) {
        println("$month has $days days")
    }

    val votes : MutableMap<String, Int> = HashMap()
    votes["Alice"] = 0
    votes["Bob"] = 0
    votes["Charlie"] = 0

    votes["Alice"] = votes["Alice"]?.plus(1) ?: 0
    votes["Bob"] = 30
    votes["Charlie"] = votes["Charlie"]?.plus(1) ?: 0

    println(votes)
    for((name, vote) in votes) {
        println("$name has $vote votes")
    }

    val gifts:  MutableMap<String, String> = LinkedHashMap()
    gifts["Alice"] = "Book"
    gifts["Bob"] = "Game"
    gifts["Charlie"] = "Pen"
    println(gifts)
    for ((name, _) in gifts) {
        println(name)
    }

    // filters, maps, reduces
    val randomNumbers = List(100) { Random().nextInt(1000) }
    val evenNumbers = randomNumbers.filter { it % 2 == 0 }
    println("Even numbers: $evenNumbers")
    val squaredNumbers = randomNumbers.map { it * it }
    println("Squared numbers: $squaredNumbers")
    val sum = randomNumbers.reduce { acc, i -> acc + i }
    println("Sum of numbers: $sum")
}

fun main(args: Array<String>) {
    // jsonDemo()
    // helloWorld(args)
    // balanceMananagement()
    // sayHello()
    idioms()
}