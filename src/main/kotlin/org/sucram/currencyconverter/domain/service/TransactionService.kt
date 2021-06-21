package org.sucram.currencyconverter.domain.service

import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.domain.Transaction
import org.sucram.currencyconverter.domain.TransactionRequest
import org.sucram.currencyconverter.domain.repository.TransactionRepository
import java.util.*

class TransactionService(private val currencyConverterService: CurrencyConverterService, private val transactionRepository: TransactionRepository) {

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    fun convert(transactionRequest: TransactionRequest): Transaction {

        val result =
            currencyConverterService.convert(transactionRequest.from, transactionRequest.to, transactionRequest.amount)

        val transaction = Transaction(
            userId = transactionRequest.userId!!,
            originCurrency = transactionRequest.from,
            originAmount = transactionRequest.amount,
            destinationCurrency = transactionRequest.to,
            destinationAmount = result.result,
            exchangeRate = result.exchangeRate,
            createdAt = Date()
        )

        return transaction.copy(id = transactionRepository.create(transaction))
    }

    fun findByUser(userId: Long): List<Transaction> {
        logger.info("findByUser userId: $userId")
        return transactionRepository.findByUserId(userId)
    }
}