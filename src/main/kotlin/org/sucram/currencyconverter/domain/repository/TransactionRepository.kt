package org.sucram.currencyconverter.domain.repository

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.jodatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.sucram.currencyconverter.domain.Transaction
import javax.sql.DataSource

internal object Transactions : LongIdTable() {

    val userId: Column<Long> = long("user_id")
    val originCurrency: Column<String> = varchar("origin_currency", 3)
    val originAmount: Column<Double> = double("origin_amount")
    val destinationCurrency: Column<String> = varchar("destination_currency", 3)
    val destinationAmount: Column<Double> = double("destination_amount")
    val exchangeRate: Column<Double> = double("exchange_rate")
    val createdAt: Column<DateTime> = date("created_at")


    fun toDomain(row: ResultRow): Transaction {
        return Transaction(
            id = row[id].value,
            userId = row[userId],
            originCurrency = row[originCurrency],
            originAmount = row[originAmount],
            destinationCurrency =row[destinationCurrency],
            destinationAmount =row[destinationAmount],
            exchangeRate =row[exchangeRate],
            createdAt =row[createdAt].toDate()
        )
    }
}



class TransactionRepository(private val dataSource: DataSource) {

    init {
        transaction(Database.connect(dataSource))
        {
            SchemaUtils.create(Transactions)
        }
    }

    fun create(transaction: Transaction): Long {
        return transaction(Database.connect(dataSource))
        {
            addLogger(StdOutSqlLogger)

           Transactions.insertAndGetId { row ->
                row[Transactions.userId] = transaction.userId
                row[Transactions.originCurrency] = transaction.originCurrency
                row[Transactions.originAmount] = transaction.originAmount
                row[Transactions.destinationCurrency] = transaction.destinationCurrency
                row[Transactions.destinationAmount] = transaction.destinationAmount
                row[Transactions.exchangeRate] = transaction.exchangeRate
                row[Transactions.createdAt] = DateTime()
            }
        }.value


    }

    fun findByUserId(id: Long): List<Transaction> {
        return transaction(Database.connect(dataSource))
        {
            Transactions.select { Transactions.userId eq id }
                .map { Transactions.toDomain(it) }
        }
    }
}