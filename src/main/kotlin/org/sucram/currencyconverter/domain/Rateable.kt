package org.sucram.currencyconverter.domain

interface Rateable {
    fun getRates(from: String, to: String): Map<String, Double>
}