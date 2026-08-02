package com.example.expenseai.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseai.database.ExpenseDatabase
import com.example.expenseai.model.Expense
import com.example.expenseai.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val dao =
        ExpenseDatabase
            .getDatabase(application)
            .expenseDao()

    private val repository =
        ExpenseRepository(dao)

    fun saveExpense(
        expense: Expense
    ) {

        viewModelScope.launch {

            repository.saveExpense(expense)

        }

    }

    fun getExpenses(
        onResult: (List<Expense>) -> Unit
    ) {

        viewModelScope.launch {

            val expenses =
                repository.getAllExpenses()

            onResult(expenses)

        }

    }

    fun deleteAllExpenses() {

        viewModelScope.launch {

            repository.deleteAllExpenses()

        }

    }

    fun getTodayTotal(
        onResult: (Double) -> Unit
    ) {

        viewModelScope.launch {

            val total =
                repository.getTodayTotal()

            onResult(total)

        }

    }

    fun getTodayExpenseCount(
        onResult: (Int) -> Unit
    ) {

        viewModelScope.launch {

            val count =
                repository.getTodayExpenseCount()

            onResult(count)

        }

    }

    fun getLatestExpense(
        onResult: (Expense?) -> Unit
    ) {

        viewModelScope.launch {

            val expense =
                repository.getLatestExpense()

            onResult(expense)

        }

    }

}