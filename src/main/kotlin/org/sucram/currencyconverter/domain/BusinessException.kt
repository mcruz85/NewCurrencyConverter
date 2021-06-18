package org.sucram.currencyconverter.domain

import java.lang.RuntimeException

class BusinessException(message: String?) : RuntimeException(message) {}