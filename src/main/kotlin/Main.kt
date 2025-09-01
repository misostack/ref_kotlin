package net.jsbase.helloworld

import net.jsbase.helloworld.domains.TransactionType
import net.jsbase.helloworld.models.Transaction
import net.jsbase.helloworld.services.BalanceService
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Currency
import java.util.Locale
import java.util.UUID

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

fun main(args: Array<String>) {

    // helloWorld(args)
    balanceMananagement()
}