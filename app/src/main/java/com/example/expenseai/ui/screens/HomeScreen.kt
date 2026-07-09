package com.example.expenseai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expenseai.ui.components.SpendingCard
import com.example.expenseai.ui.components.VoiceButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("ExpenseAI")
                }
            )

        }

    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            item {

                SpendingCard()

                Spacer(modifier = Modifier.height(30.dp))

                VoiceButton {

                }

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(Icons.Default.Add, null)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Add Expense")

                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    "Recent Expenses",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("No expenses added yet.")

            }

        }

    }

}