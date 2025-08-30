package net.jsbase.helloworld.services

import net.jsbase.helloworld.models.Transaction
import net.jsbase.helloworld.domains.TransactionType
import java.util.Currency

class BalanceService(
    val initialBalance: Double = 0.0,
    val currency: Currency,
    val transactions: ArrayList<Transaction> = arrayListOf(),
) {
    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }

    fun removeTransaction(transactionId: String): Boolean {
        return transactions.removeIf { it.id == transactionId }
    }

    fun getBalance(): Double {
        var balance = initialBalance
        for (transaction in transactions) {
            when (transaction.type) {
                TransactionType.INCOME -> balance += transaction.amount
                TransactionType.EXPENSE -> balance -= transaction.amount
            }
        }
        return balance
    }

    fun getIncomeTotal(): Double {
        return transactions.filter { it.type == TransactionType.INCOME }
            .sumOf { it.amount }
    }

    fun getExpenseTotal(): Double {
        return transactions.filter { it.type == TransactionType.EXPENSE }
            .sumOf { it.amount }
    }
}