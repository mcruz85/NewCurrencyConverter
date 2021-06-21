package org.sucram.currencyconverter.web.controllers

import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus
import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.domain.TransactionRequest
import org.sucram.currencyconverter.domain.TransactionsResponse
import org.sucram.currencyconverter.domain.service.TransactionService

class TransactionController(private val transactionService: TransactionService) {

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    fun create(ctx: Context) {
        logger.info("Received request for create transaction ${ctx.body()}")

        ctx.bodyValidator<TransactionRequest>()
            .check({ !it.from.isBlank() }, "'from' must not be null or blank")
            .check({ !it.to.isBlank() }, "'to' must not be null or blank")
            .check({ !it.amount.isNaN() }, "'amount' must not be null or blank")
            .check({ it.userId != null}, "'userId' must not be null")
            .get().also { transactionRequest ->
                transactionService.convert(transactionRequest).apply {
                    ctx.json(this).status(HttpStatus.CREATED_201)
                }
            }
    }

    fun findByUser(ctx: Context) {
        logger.info("Received request for transactions by user ${ctx.url()}")
        val userId = ctx.queryParam<Long>("user").get()
        val transactions = transactionService.findByUser(userId)
        ctx.json(TransactionsResponse(transactions))
    }


}
