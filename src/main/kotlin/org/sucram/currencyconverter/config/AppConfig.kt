package org.sucram.currencyconverter.config

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import org.eclipse.jetty.server.Server
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.sucram.currencyconverter.web.TransactionController


class AppConfig :KoinComponent {

    fun setup(): Javalin {

        startKoin {
            printLogger()
            fileProperties()
        }

        val port: Int = System.getenv("PORT")?.toIntOrNull() ?: 7002
        val app = Javalin.create { config ->
            config.apply { server { Server(port) } }
        }

        app.get("/") { ctx -> ctx.result("Hello World") }

        val transactionController = TransactionController()

        app.routes {
            ApiBuilder.path("transactions") {
                ApiBuilder.post(transactionController::create)
                ApiBuilder.get(transactionController::findByUser)
            }
        }

       return app


    }
}

