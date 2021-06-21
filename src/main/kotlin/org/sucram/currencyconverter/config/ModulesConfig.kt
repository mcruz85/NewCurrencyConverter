package org.sucram.currencyconverter.config

import org.koin.dsl.module
import org.sucram.currencyconverter.client.exchangeratesapi.ExchangeRatesAPIService
import org.sucram.currencyconverter.domain.service.ExchangeRatesService
import org.sucram.currencyconverter.domain.Rateable
import org.sucram.currencyconverter.domain.repository.TransactionRepository
import org.sucram.currencyconverter.domain.service.CurrencyConverterService
import org.sucram.currencyconverter.domain.service.TransactionService
import org.sucram.currencyconverter.web.controllers.Router
import org.sucram.currencyconverter.web.controllers.TransactionController

object ModulesConfig {

    private val configModule = module {
        single { Router(get()) }
        single { AppConfig() }
        single {
            DBConfig(getProperty("jdbc.url"), getProperty("db.username"), getProperty("db.password")).getDataSource()
        }

    }

    private val currencyConverterModule = module {
        single { ExchangeRatesAPIService() }
        single { ExchangeRatesService(get()) as Rateable }
        single { CurrencyConverterService(get()) }
    }

    private val transactionModule = module {
        single { TransactionService(get(), get()) }
        single { TransactionRepository(get()) }
        single { TransactionController(get()) }
    }

    internal val allModules = listOf(configModule, currencyConverterModule, transactionModule)

}
