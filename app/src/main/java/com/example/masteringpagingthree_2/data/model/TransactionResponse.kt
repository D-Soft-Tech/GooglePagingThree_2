package com.example.masteringpagingthree_2.data.model

data class TransactionResponse(
    var amount: Int?,
    var depositorAccountName: String?,
    var depositorBankName: String?,
    var transactionDateTime: String?
)