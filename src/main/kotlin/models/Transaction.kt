package net.jsbase.helloworld.models

import net.jsbase.helloworld.domains.TransactionType
import java.util.Date

data class Transaction(
    val id: String,
    val amount: Double,
    val date: Date,
    val type: TransactionType,
    val description: String

) {

}