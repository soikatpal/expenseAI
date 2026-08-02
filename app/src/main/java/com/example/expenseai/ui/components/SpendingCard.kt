package com.example.expenseai.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expenseai.model.Expense
import androidx.compose.foundation.layout.padding

@Composable
fun SpendingCard(

    todayTotal: Double,

    todayCount: Int,

    latestExpense: Expense?

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )

    ) {

        Column(

            modifier = Modifier.fillMaxWidth()

                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            Text(

                text = "Today's Spending",

                style = MaterialTheme.typography.titleMedium

            )

            Text(

                text = "₹${"%.2f".format(todayTotal)}",

                fontSize = 34.sp,

                fontWeight = FontWeight.Bold

            )

            Text(

                text = "Today's Expenses : $todayCount",

                style = MaterialTheme.typography.bodyLarge

            )

            HorizontalDivider()

            Text(

                text = "Latest Expense",

                style = MaterialTheme.typography.titleSmall

            )

            if (latestExpense == null) {

                Text(
                    text = "No expenses today."
                )

            } else {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column {

                        Text(
                            text = latestExpense.category,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = latestExpense.merchant
                        )

                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(

                        text = "₹${latestExpense.amount}",

                        fontWeight = FontWeight.Bold

                    )

                }

            }

        }

    }

}