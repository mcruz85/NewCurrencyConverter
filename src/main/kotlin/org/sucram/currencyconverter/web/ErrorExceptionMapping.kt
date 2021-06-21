package org.sucram.currencyconverter.web

import io.javalin.Javalin
import io.javalin.http.*
import org.eclipse.jetty.http.HttpStatus
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.domain.exception.BusinessException


internal data class ErrorResponse(val errors: Map<String, List<String?>>)

object ErrorExceptionMapping {
    private val LOG = LoggerFactory.getLogger(ErrorExceptionMapping::class.java)

    fun register(app: Javalin) {

        app.exception(Exception::class.java) { e, ctx ->
            LOG.error("Exception occurred for req -> ${ctx.url()}", e)
            val error = ErrorResponse(mapOf("Unknow Error" to listOf(e.message ?: "Error occurred!")))
            ctx.json(error).status(HttpStatus.INTERNAL_SERVER_ERROR_500)
        }
        app.exception(ExposedSQLException::class.java) { e, ctx ->
            LOG.error("Exception occurred for req -> ${ctx.url()}", e)
            val error = ErrorResponse(mapOf("Unknow Error" to listOf("Error occurred!")))
            ctx.json(error).status(HttpStatus.INTERNAL_SERVER_ERROR_500)
        }
        app.exception(BadRequestResponse::class.java) { _, ctx ->
            LOG.warn("BadRequestResponse occurred for req -> ${ctx.url()}")
            val error = ErrorResponse(mapOf("body" to listOf("can't be empty or invalid")))
            ctx.json(error).status(HttpStatus.BAD_REQUEST_400)
        }

        app.exception(NotFoundResponse::class.java) { _, ctx ->
            LOG.warn("NotFoundResponse occurred for req -> ${ctx.url()}")
            val error = ErrorResponse(mapOf("body" to listOf("Resource can't be found to fulfill the request.")))
            ctx.json(error).status(HttpStatus.NOT_FOUND_404)
        }
        app.exception(HttpResponseException::class.java) { e, ctx ->
            LOG.warn("HttpResponseException occurred for req -> ${ctx.url()}")
            val error = ErrorResponse(mapOf("body" to listOf(e.message)))
            ctx.json(error).status(e.status)
        }

        app.exception(BusinessException::class.java) { e, ctx ->
            LOG.error("Exception occurred for req -> ${ctx.url()}", e)
            val error = ErrorResponse(mapOf("body" to listOf(e.message ?: "Error occurred!")))
            ctx.json(error).status(HttpStatus.BAD_REQUEST_400)
        }
    }
}