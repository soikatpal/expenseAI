package com.example.expenseai.repository

import android.content.Context
import com.example.expenseai.database.ExpenseDatabaseHelper
import com.example.expenseai.model.Expense

class ExpenseRepository(context: Context) {

    private val database = ExpenseDatabaseHelper(context)

    fun saveExpense(expense: Expense): Long {
        return database.insertExpense(expense)
    }

    fun getAllExpenses(): List<Expense> {
        return database.getAllExpenses()
    }

}