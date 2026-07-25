package com.example.expenseai.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.expenseai.database.ExpenseDatabaseHelper
import com.example.expenseai.model.Expense
import com.example.expenseai.parser.ExpenseParser
import com.example.expenseai.ui.components.DetectedExpenseCard
import com.example.expenseai.ui.components.SpendingCard
import com.example.expenseai.ui.components.VoiceSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val context = LocalContext.current

    val database = remember {
        ExpenseDatabaseHelper(context)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    var recognizedSpeech by remember {
        mutableStateOf("Tap the microphone and speak")
    }

    var detectedExpense by remember {
        mutableStateOf<Expense?>(null)
    }

    var recentExpenses by remember {
        mutableStateOf(database.getAllExpenses())
    }

    val speechLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val matches =
                    result.data?.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS
                    )

                if (!matches.isNullOrEmpty()) {

                    recognizedSpeech = matches[0]

                    detectedExpense =
                        ExpenseParser.parse(recognizedSpeech)

                }

            }

        }

    Scaffold(

        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },

        topBar = {

            TopAppBar(
                title = {
                    Text("ExpenseAI")
                }
            )

        }

    ) { padding ->

        LazyColumn(

            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 32.dp
            ),

            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {

            item {

                SpendingCard()

            }

            item {

                VoiceSection(

                    recognizedSpeech = recognizedSpeech,

                    onMicClicked = {

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

                )

            }

            item {

                DetectedExpenseCard(
                    expense = detectedExpense
                )

            }

            item {

                Button(

                    onClick = {

                        detectedExpense?.let {

                            val id =
                                database.insertExpense(it)

                            if (id > 0) {

                                recentExpenses =
                                    database.getAllExpenses()

                                scope.launch {

                                    snackbarHostState.showSnackbar(
                                        "Expense Saved Successfully"
                                    )

                                }

                            }

                        }

                    },

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text("Save Expense")

                }

            }
            item {

                Button(

                    onClick = {

                        database.deleteAllExpenses()

                        recentExpenses =
                            database.getAllExpenses()

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "All expenses deleted"
                            )
                        }

                    },

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Text("Clear All (Testing)")

                }

            }

            item {

                Text(
                    text = "Recent Expenses"
                )

            }

            items(recentExpenses) { expense ->

                DetectedExpenseCard(
                    expense = expense
                )

            }

        }

    }

}
