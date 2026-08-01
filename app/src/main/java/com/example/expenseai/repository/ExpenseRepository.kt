package com.example.expenseai.repository

import com.example.expenseai.database.ExpenseDao
import com.example.expenseai.mapper.toEntity
import com.example.expenseai.mapper.toExpense
import com.example.expenseai.model.Expense

class ExpenseRepository(
    private val dao: ExpenseDao
) {

    suspend fun saveExpense(
        expense: Expense
    ) {

        dao.insertExpense(
            expense.toEntity()
        )

    }

    suspend fun getAllExpenses(): List<Expense> {

        return dao
            .getAllExpenses()
            .map {
                it.toExpense()
            }

    }

    suspend fun deleteExpense(
        expense: Expense
    ) {

        dao.deleteExpense(
            expense.toEntity()
        )

    }

    suspend fun deleteAllExpenses() {

        dao.deleteAllExpenses()

    }

    suspend fun getTodayTotal(): Double {

        return dao.getTodayTotal()

    }

}