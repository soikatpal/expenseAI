package com.example.expenseai.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.expenseai.ui.components.RecentExpensesSection
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

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),

            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {

            SpendingCard()

            VoiceSection(

                recognizedSpeech = recognizedSpeech,

                onMicClicked = {

                    val intent =
                        Intent(
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

            DetectedExpenseCard(
                expense = detectedExpense
            )

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
                    modifier = Modifier.height(0.dp)
                )

                Text(" Save Expense")

            }

            Button(

                onClick = {

                    database.deleteAllExpenses()

                    recentExpenses =
                        database.getAllExpenses()

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Clear All (Testing)")

            }

            RecentExpensesSection(
                expenses = recentExpenses
            )

        }

    }

}