package org.sucram.currencyconverter.client.exchangeratesapi

import org.sucram.currencyconverter.client.ExchangeRatesAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class ExchangeRatesAPIService {

    private val BASE_URL = "http://api.exchangeratesapi.io/v1/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
        .create(ExchangeRatesAPI::class.java)


    fun getLatest(symbols: String ="BRL,USD,EUR,JPY"): Call<LatestRateResponseDTO> {
        return api.getLatest(symbols= symbols)
    }
}