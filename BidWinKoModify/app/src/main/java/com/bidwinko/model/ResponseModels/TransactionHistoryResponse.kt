package com.bidwinko.model.ResponseModels

data class TransactionHistoryResponse(
    val message: String,
    val status: Int,
    val transactions: List<Transaction>
)
data class Transaction(
    val serialNo : String,
    val amount: String,
    val bids: String,
    val date: String,
    val time: String,
    val title: String
)