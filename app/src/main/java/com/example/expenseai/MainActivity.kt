package com.example.expenseai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.expenseai.ui.screens.HomeScreen
import com.example.expenseai.ui.theme.ExpenseAITheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExpenseAITheme {
                HomeScreen()
            }
        }
    }
}