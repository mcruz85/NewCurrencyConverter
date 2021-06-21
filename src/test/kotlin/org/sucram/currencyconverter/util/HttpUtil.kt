package org.sucram.currencyconverter.util

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.ObjectMapper
import com.mashape.unirest.http.Unirest
import io.javalin.core.util.Header
import io.javalin.plugin.json.JavalinJson


class HttpUtil(port: Int) {

    private val json = "application/json"
    val headers = mutableMapOf(Header.ACCEPT to json, Header.CONTENT_TYPE to json)

    init {
        Unirest.setObjectMapper(object : ObjectMapper {
            override fun <T> readValue(value: String, valueType: Class<T>): T {
                return JavalinJson.fromJson(value, valueType)
            }

            override fun writeValue(value: Any): String {
                return JavalinJson.toJson(value)
            }
        })
    }

    @JvmField
    val origin: String = "http://localhost:$port"

    inline fun <reified T> post(path: String, body: Any) =
        Unirest.post(origin + path).headers(headers).body(body).asObject(T::class.java)

    inline fun <reified T> get(path: String) =
        Unirest.get(origin + path).headers(headers).asObject(T::class.java)

    fun createTransaction(body: Any): HttpResponse<Any> {
        val response = post<Any>("/transactions", body)
        return response
    }

    fun listTransactionsByUser(userId: String): HttpResponse<Any> {
        val response = get<Any>("/users/$userId/transactions")
        return response
    }
}