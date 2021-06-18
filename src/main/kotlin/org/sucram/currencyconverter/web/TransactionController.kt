package org.sucram.currencyconverter.web

import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus
import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.domain.Transaction
import org.sucram.currencyconverter.domain.TransactionRequest
import java.util.*

class TransactionController {

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    fun create(ctx: Context) {
        logger.info("Received request for create transaction ${ctx.body()}")

        ctx.bodyValidator<TransactionRequest>()
            .check({ !it.from.isBlank() }, "'from' must not be null or blank")
            .check({ !it.to.isBlank() }, "'to' must not be null or blank")
            .check({ !it.amount.isNaN() }, "'amount' must not be null or blank")
            .check({ it.userId != null}, "'userId' must not be null")
            .get().also { transactionRequest ->

                ctx.json(Transaction(id = 1, userId = 100, originCurrency = "BRL", originAmount = 100.00, destinationCurrency = "USD", destinationAmount = 19.19, exchangeRate = 0.51, createdAt = Date())).status(HttpStatus.CREATED_201)
            }
    }

    fun findByUser(ctx: Context) {
        logger.info("Received request for transactions by user ${ctx.url()}")
        val userId = ctx.queryParam<Long>("user").get()
        ctx.json(listOf(Transaction(id = 1, userId = 100, originCurrency = "BRL", originAmount = 100.00, destinationCurrency = "USD", destinationAmount = 19.19, exchangeRate = 0.51, createdAt = Date())))
    }


}
