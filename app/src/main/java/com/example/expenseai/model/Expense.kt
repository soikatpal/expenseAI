package com.example.expenseai.model

data class Expense(
    val amount: Double,
    val category: String,
    val merchant: String,
    val description: String,
    val date: String
)