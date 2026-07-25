package com.example.expenseai.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expenseai.model.Expense

@Composable
fun RecentExpensesSection(
    expenses: List<Expense>
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Recent Expenses",
                style = MaterialTheme.typography.titleLarge
            )

            if (expenses.isEmpty()) {

                Text(
                    text = "No expenses yet."
                )

            } else {

                expenses.forEachIndexed { index, expense ->

                    ExpenseRow(expense)

                    if (index != expenses.lastIndex) {
                        HorizontalDivider()
                    }

                }

            }

        }

    }

}

@Composable
private fun ExpenseRow(
    expense: Expense
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Text(
            text = "₹${expense.amount}",
            style = MaterialTheme.typography.titleMedium
        )

        Text("Category : ${expense.category}")

        if (expense.merchant.isNotBlank()) {
            Text("Merchant : ${expense.merchant}")
        }

    }

}