package com.example.expenseai.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseai.model.Expense
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpenseListItem(
    expense: Expense
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = getCategoryEmoji(expense.category),
                    fontSize = 26.sp
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = expense.category,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (expense.merchant.isNotBlank()) {

                        Text(
                            text = expense.merchant,
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }

                }

                Text(
                    text = "₹${expense.amount}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (expense.note.isNotBlank()) {

                Text(
                    text = expense.note,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

            }

            HorizontalDivider()

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = formatDate(expense.expenseDate),
                style = MaterialTheme.typography.bodySmall
            )

        }

    }

}

private fun getCategoryEmoji(
    category: String
): String {

    return when (category.lowercase()) {

        "food" -> "🍔"

        "travel" -> "🚕"

        "shopping" -> "🛍"

        "fuel" -> "⛽"

        "medical" -> "🏥"

        "salary" -> "💰"

        "bills" -> "💡"

        "entertainment" -> "🎬"

        else -> "📦"

    }

}

private fun formatDate(
    millis: Long
): String {

    val formatter =
        SimpleDateFormat(
            "dd MMM yyyy  •  hh:mm a",
            Locale.getDefault()
        )

    return formatter.format(
        Date(millis)
    )

}