package org.sucram.currencyconverter.domain.service

import org.slf4j.LoggerFactory
import org.sucram.currencyconverter.client.exchangeratesapi.ExchangeRatesAPIService
import org.sucram.currencyconverter.domain.BusinessException
import org.sucram.currencyconverter.domain.Rateable

class ExchangeRatesService(private val exchangeRatesAPIService: ExchangeRatesAPIService) : Rateable {

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    override fun getRates(from: String, to: String): Map<String, Double> {
        logger.debug("from: $from, to: $to")
        val response = exchangeRatesAPIService.getLatest(symbols= "$from,$to").execute()
        logger.debug("response: $response")

        if (!response.isSuccessful) { throw BusinessException("External api unsuccessful call. $response") }

        val body = response.body()

        if (body == null || !body.success) { throw BusinessException("External api unsuccessful call. $response") }

        return body.rates
    }
}