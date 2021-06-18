package org.sucram.currencyconverter.domain

import java.util.*


data class TransactionRequest(
    var from: String,
    val to: String,
    val amount: Double,
    val userId: Long? = null
)

data class Transaction(
    val id: Long?=null,
    val userId: Long,
    val originCurrency: String,
    val originAmount: Double,
    val destinationCurrency: String,
    val destinationAmount: Double,
    val exchangeRate: Double,
    val createdAt: Date = Date()
)