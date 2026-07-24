package com.example.expenseai.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.expenseai.model.Expense

class ExpenseDatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {

    companion object {

        private const val DATABASE_NAME = "expense_ai.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_EXPENSE = "expenses"

        private const val COL_ID = "id"
        private const val COL_AMOUNT = "amount"
        private const val COL_CATEGORY = "category"
        private const val COL_MERCHANT = "merchant"
        private const val COL_NOTE = "note"
        private const val COL_EXPENSE_DATE = "expense_date"
        private const val COL_CREATED_AT = "created_at"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = """
            CREATE TABLE $TABLE_EXPENSE (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_AMOUNT REAL NOT NULL,
                $COL_CATEGORY TEXT NOT NULL,
                $COL_MERCHANT TEXT,
                $COL_NOTE TEXT,
                $COL_EXPENSE_DATE INTEGER,
                $COL_CREATED_AT INTEGER
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSE")
        onCreate(db)
    }

    fun insertExpense(expense: Expense): Long {

        val values = ContentValues().apply {
            put(COL_AMOUNT, expense.amount)
            put(COL_CATEGORY, expense.category)
            put(COL_MERCHANT, expense.merchant)
            put(COL_NOTE, expense.note)
            put(COL_EXPENSE_DATE, expense.expenseDate)
            put(COL_CREATED_AT, expense.createdAt)
        }

        val db = writableDatabase
        val id = db.insert(TABLE_EXPENSE, null, values)
        db.close()

        return id
    }

    fun getAllExpenses(): List<Expense> {

        val expenses = mutableListOf<Expense>()

        val db = readableDatabase

        val cursor = db.query(
            TABLE_EXPENSE,
            null,
            null,
            null,
            null,
            null,
            "$COL_ID DESC"
        )

        while (cursor.moveToNext()) {

            expenses.add(
                Expense(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)),
                    amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)),
                    merchant = cursor.getString(cursor.getColumnIndexOrThrow(COL_MERCHANT)),
                    note = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE)),
                    expenseDate = cursor.getLong(cursor.getColumnIndexOrThrow(COL_EXPENSE_DATE)),
                    createdAt = cursor.getLong(cursor.getColumnIndexOrThrow(COL_CREATED_AT))
                )
            )
        }

        cursor.close()
        db.close()

        return expenses
    }

    fun deleteAllExpenses() {

        val db = writableDatabase
        db.delete(TABLE_EXPENSE, null, null)
        db.close()
    }
}