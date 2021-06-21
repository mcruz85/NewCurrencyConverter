package org.sucram.currencyconverter.web.controllers

import io.javalin.http.Context
import io.javalin.plugin.openapi.annotations.*
import org.eclipse.jetty.http.HttpStatus
import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.domain.Transaction
import org.sucram.currencyconverter.domain.TransactionRequest
import org.sucram.currencyconverter.domain.TransactionsResponse
import org.sucram.currencyconverter.domain.service.TransactionService
import org.sucram.currencyconverter.web.ErrorResponse

class TransactionController(private val transactionService: TransactionService) {

    private val logger = LoggerFactory.getLogger(this::class.java.name)


    @OpenApi(
        summary = "Calculates currency conversion",
        operationId = "createTransaction",
        tags = ["Transaction"],
        method = HttpMethod.POST,
        requestBody = OpenApiRequestBody([OpenApiContent(TransactionRequest::class)], true, "A"),
        responses = [
            OpenApiResponse("201", [OpenApiContent(Transaction::class)]),
            OpenApiResponse("400", [OpenApiContent(ErrorResponse::class)])
        ]
    )
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


    @OpenApi(
        summary = "Retrieves all transactions performed by user",
        operationId = "getTransactionsByUser",
        tags = ["Transaction"],
        pathParams = [OpenApiParam("user", Int::class, "The user ID", false, required = true)],
        responses = [
            OpenApiResponse("200", [OpenApiContent(TransactionsResponse::class)]),
            OpenApiResponse("400", [OpenApiContent(ErrorResponse::class)])
        ]
    )
    fun findByUser(ctx: Context) {
        logger.info("Received request for transactions by user ${ctx.url()}")
        val userId = ctx.queryParam<Long>("user").get()
        val transactions = transactionService.findByUser(userId)
        ctx.json(TransactionsResponse(transactions))
    }


}
