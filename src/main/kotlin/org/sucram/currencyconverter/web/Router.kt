package org.sucram.currencyconverter.web

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.koin.core.component.KoinComponent
import org.sucram.currencyconverter.web.controllers.TransactionController

class Router(private val transactionController: TransactionController) : KoinComponent {

    fun register(app: Javalin) {
        app.routes {
            path("transactions") {
                post(transactionController::create)
                get(transactionController::findByUser)
            }
        }
    }
}