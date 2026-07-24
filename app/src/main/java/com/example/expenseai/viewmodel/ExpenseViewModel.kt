package com.example.expenseai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.expenseai.model.Expense
import com.example.expenseai.repository.ExpenseRepository

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ExpenseRepository(application)

    fun saveExpense(expense: Expense): Long {
        return repository.saveExpense(expense)
    }

    fun getExpenses(): List<Expense> {
        return repository.getAllExpenses()
    }
}