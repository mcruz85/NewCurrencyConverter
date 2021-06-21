package org.sucram.currencyconverter.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

class DBConfig(jdbcUrl: String, userName: String, password: String) {
    private val dataSource: DataSource

    init {
        dataSource = HikariConfig().let { config ->
            config.jdbcUrl = jdbcUrl
            config.username = userName
            config.password = password
            config.maximumPoolSize = 10
            HikariDataSource(config)
        }
    }

    fun getDataSource(): DataSource {
        return dataSource
    }
}