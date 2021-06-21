package org.sucram.currencyconverter.domain.service

import org.sucram.currencyconverter.domain.exception.BusinessException
import org.sucram.currencyconverter.domain.Rateable
import org.sucram.currencyconverter.domain.Symbol

data class Conversion(val exchangeRate: Double, val result: Double)

class CurrencyConverterService(private val rateable: Rateable) {

    fun convert(from: String, to: String, amount: Double): Conversion {

        validate(from, to)

        val rates = rateable.getRates(from, to)

        val exchangeRate = rates[to]?.div(rates[from]!!)
        val result = exchangeRate?.times(amount)!!

        return Conversion(exchangeRate, result)
    }

    private fun validate(from: String,  to: String) {
        isValidSymbol(from, "from")
        isValidSymbol(to, "to")
        if (from == to) throw BusinessException("from and to fields may not be equals")
    }

    private fun isValidSymbol(symbol: String, field: String): Boolean {
        return try {
            Symbol.valueOf(symbol)
            true
        } catch (ex: IllegalArgumentException) {
            throw BusinessException("$field=$symbol is not a valid symbol")
        }
    }
}