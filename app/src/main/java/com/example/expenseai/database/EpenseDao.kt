package com.example.expenseai.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(
        expense: ExpenseEntity
    )

    @Query(
        "SELECT * FROM expenses ORDER BY expenseDate DESC"
    )
    suspend fun getAllExpenses(): List<ExpenseEntity>

    @Delete
    suspend fun deleteExpense(
        expense: ExpenseEntity
    )

    @Query("DELETE FROM expenses")
    suspend fun deleteAllExpenses()

    @Query(
        """
        SELECT COALESCE(SUM(amount),0)
        FROM expenses
        WHERE date(expenseDate/1000,'unixepoch','localtime')
            =
            date('now','localtime')
        """
    )
    suspend fun getTodayTotal(): Double

}