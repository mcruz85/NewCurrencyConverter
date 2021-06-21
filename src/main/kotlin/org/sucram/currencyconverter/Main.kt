package org.sucram.currencyconverter

import org.koin.core.component.KoinApiExtension
import org.sucram.currencyconverter.config.AppConfig


@KoinApiExtension
fun main() {
    AppConfig().setup().start()
}


