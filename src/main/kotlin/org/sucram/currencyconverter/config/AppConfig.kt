package org.sucram.currencyconverter.config

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.eclipse.jetty.server.Server
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.sucram.currencyconverter.web.controllers.Router
import java.text.SimpleDateFormat


class AppConfig : KoinComponent {

    private val router: Router by inject()

    fun setup(): Javalin {

        startKoin {
            printLogger()
            modules(ModulesConfig.allModules)
            fileProperties()
        }
        this.configureMapper()
        val port: Int = System.getenv("PORT")?.toIntOrNull() ?: 7002
        val app = Javalin.create { config ->
            config.apply { server { Server(port) } }
        }

        router.register(app)

        return app
    }

    private fun configureMapper() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        JavalinJackson.configure(
            jacksonObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(dateFormat)
                .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, true)
        )
    }
}

