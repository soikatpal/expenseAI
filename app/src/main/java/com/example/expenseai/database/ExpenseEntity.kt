package com.example.expenseai.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val amount: Double,

    val category: String,

    val merchant: String,

    val note: String,

    val expenseDate: Long,

    val createdAt: Long = System.currentTimeMillis()

)