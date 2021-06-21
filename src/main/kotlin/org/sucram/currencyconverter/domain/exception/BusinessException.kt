package org.sucram.currencyconverter.domain.exception

import java.lang.RuntimeException

class BusinessException(message: String?) : RuntimeException(message)