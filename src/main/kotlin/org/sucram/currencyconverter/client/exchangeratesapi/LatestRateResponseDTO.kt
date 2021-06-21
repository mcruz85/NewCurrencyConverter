package org.sucram.currencyconverter.client.exchangeratesapi

import com.fasterxml.jackson.annotation.JsonProperty

data class LatestRateResponseDTO(

    @JsonProperty("success")
    var success: Boolean,

    @JsonProperty("timestamp")
    var timestamp: Long,

    @JsonProperty("base")
    var base: String,

    @JsonProperty("date")
    var date: String,

    @JsonProperty("rates")
    var rates: Map<String, Double>
)