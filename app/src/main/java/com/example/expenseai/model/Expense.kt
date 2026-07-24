package com.example.expenseai.model

data class Expense(

    val id: Long = 0,

    val amount: Double,

    val category: String,

    val merchant: String,

    val note: String,

    val expenseDate: Long,

    val createdAt: Long = System.currentTimeMillis()

)