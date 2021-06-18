package org.sucram.currencyconverter.domain

class RateableImp : Rateable {
    override fun getRates(from: String, to: String): Map<String, Double> {
        return mapOf( "BRL" to 0.19, "USD" to 1.00 )
    }
}