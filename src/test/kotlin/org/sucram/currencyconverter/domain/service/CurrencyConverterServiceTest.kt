package org.sucram.currencyconverter.domain.service

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.sucram.currencyconverter.domain.BusinessException
import org.sucram.currencyconverter.domain.RateableImp
import org.sucram.currencyconverter.domain.Symbol

class CurrencyConverterServiceTest {

    private val rate = mockk<RateableImp>()

    private lateinit var currencyConverterService: CurrencyConverterService

    @Before
    fun setUp() {
        every { rate.getRates(any(), any()) } returns mapOf(Symbol.USD.toString() to 1.191748, Symbol.BRL.toString() to 5.968991, Symbol.JPY.toString() to 131.301434, Symbol.EUR.toString() to 1.0)

        currencyConverterService = CurrencyConverterService(rate)
    }

    @Test
    fun `should convert currency when all parameters are valid`() {
        val fromBRL = Symbol.USD.toString()
        val toUSD = Symbol.BRL.toString()
        val amount = 1.5

        val resultConversion = currencyConverterService.convert(fromBRL, toUSD, amount)

        val rates = rate.getRates(fromBRL, toUSD)
        val exchangeRate = rates[toUSD]?.div(rates[fromBRL]!!)
        val result = exchangeRate?.times(amount)!!

        assertNotNull(result)
        assertEquals(exchangeRate, resultConversion.exchangeRate, 0.0)
        assertEquals(result, resultConversion.result, 0.0)
    }

    @Test(expected = BusinessException::class)
    fun `should return an exception when from Symbol is invalid`() {
        currencyConverterService.convert("AAA", Symbol.USD.toString(), 10.00)
    }

    @Test(expected = BusinessException::class)
    fun `should return an exception when when to symbol is invalid`() {
        currencyConverterService.convert(Symbol.BRL.toString(), "BBB", 10.00)
    }

    @Test(expected = BusinessException::class)
    fun `should return an exception when from symbol is blank`() {
        currencyConverterService.convert("", Symbol.USD.toString(), 10.00)
    }

    @Test(expected = BusinessException::class)
    fun `should return an exception when to symbol is blank`() {
        currencyConverterService.convert(Symbol.BRL.toString(), "", 10.00)
    }

    @Test(expected = BusinessException::class)
    fun `should return an exception when to and from currency symbols are equls`() {
        currencyConverterService.convert(Symbol.BRL.toString(), Symbol.BRL.toString(), 10.00)
    }

}