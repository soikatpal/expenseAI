package com.example.expenseai.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.expenseai.ui.components.SpendingCard
import com.example.expenseai.ui.components.VoiceButton
import com.example.expenseai.parser.ExpenseParser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val context = LocalContext.current

    var spokenText by remember {
        mutableStateOf("Tap the microphone and speak")
    }

    val speechLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val matches = result.data?.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                if (!matches.isNullOrEmpty()) {

                    spokenText = matches[0]

                    val expense =
                        ExpenseParser.parse(spokenText)

                    spokenText =
                        """
Amount : ₹${expense.amount}

Category : ${expense.category}

Merchant : ${expense.merchant}

Date : ${expense.date}
        """.trimIndent()

                }

            }

        }

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

                    val intent = Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH
                    )

                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                    )

                    intent.putExtra(
                        RecognizerIntent.EXTRA_PROMPT,
                        "Speak your expense..."
                    )

                    speechLauncher.launch(intent)

                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(spokenText)

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