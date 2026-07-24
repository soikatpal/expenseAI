package com.example.expenseai.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expenseai.model.Expense

@Composable
fun DetectedExpenseCard(
    expense: Expense?
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
                text = "Detected Expense",
                style = MaterialTheme.typography.titleLarge
            )

            if (expense == null) {

                Text(
                    text = "Speak something to detect an expense."
                )

            } else {

                Text("💰 Amount : ₹${expense.amount}")

                Text("📂 Category : ${expense.category}")

                Text("🏪 Merchant : ${expense.merchant}")

                Text("📝 Note : ${expense.note}")

            }

        }

    }

}