package com.example.expenseai.mapper

import com.example.expenseai.database.ExpenseEntity
import com.example.expenseai.model.Expense

fun Expense.toEntity(): ExpenseEntity {

    return ExpenseEntity(
        id = id,
        amount = amount,
        category = category,
        merchant = merchant,
        note = note,
        expenseDate = expenseDate,
        createdAt = createdAt
    )

}

fun ExpenseEntity.toExpense(): Expense {

    return Expense(
        id = id,
        amount = amount,
        category = category,
        merchant = merchant,
        note = note,
        expenseDate = expenseDate,
        createdAt = createdAt
    )

}