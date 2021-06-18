package org.sucram.currencyconverter.config

import io.javalin.Javalin
import org.eclipse.jetty.server.Server
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin


class AppConfig :KoinComponent {

    fun setup(): Javalin {

        startKoin {
            printLogger()
            fileProperties()
        }

        val port: Int = System.getenv("PORT")?.toIntOrNull() ?: 7001
        val app = Javalin.create { config ->
            config.apply { server { Server(port) } }
        }

        app.get("/") { ctx -> ctx.result("Hello World") }

       return app


    }
}

