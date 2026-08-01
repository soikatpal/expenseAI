package com.example.expenseai.ui.screens

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expenseai.model.Expense
import com.example.expenseai.parser.ExpenseParser
import com.example.expenseai.ui.components.DetectedExpenseCard
import com.example.expenseai.ui.components.SpendingCard
import com.example.expenseai.ui.components.VoiceSection
import com.example.expenseai.viewmodel.ExpenseViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val expenseViewModel: ExpenseViewModel = viewModel()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var recognizedSpeech by remember {
        mutableStateOf("Tap the microphone and speak")
    }

    var detectedExpense by remember {
        mutableStateOf<Expense?>(null)
    }

    var recentExpenses by remember {
        mutableStateOf<List<Expense>>(emptyList())
    }

    LaunchedEffect(Unit) {
        expenseViewModel.getExpenses {
            recentExpenses = it
        }
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
                    recognizedSpeech = matches[0]
                    detectedExpense = ExpenseParser.parse(recognizedSpeech)
                }
            }
        }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text("ExpenseAI") })
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item { SpendingCard() }

            item {
                VoiceSection(
                    recognizedSpeech = recognizedSpeech,
                    onMicClicked = {
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
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
                DetectedExpenseCard(expense = detectedExpense)
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        detectedExpense?.let { expense ->
                            expenseViewModel.saveExpense(expense)
                            expenseViewModel.getExpenses {
                                recentExpenses = it
                            }
                            scope.launch {
                                snackbarHostState.showSnackbar("Expense Saved Successfully")
                            }
                        }
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Expense")
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        expenseViewModel.deleteAllExpenses()
                        recentExpenses = emptyList()
                        scope.launch {
                            snackbarHostState.showSnackbar("All expenses deleted")
                        }
                    }
                ) {
                    Text("Clear All (Testing)")
                }
            }

            item {
                Text(
                    text = "Recent Expenses",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(recentExpenses) { expense ->
                DetectedExpenseCard(expense = expense)
            }
        }
    }
}
