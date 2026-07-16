package com.example.expenseai.parser


import com.example.expenseai.model.Expense

object ExpenseParser {

    fun parse(text: String): Expense {

        val amountRegex = Regex("""\d+(\.\d+)?""")

        val amount = amountRegex
            .find(text)
            ?.value
            ?.toDoubleOrNull()
            ?: 0.0

        val lower = text.lowercase()

        val category = when {

            lower.contains("uber") ||
                    lower.contains("ola") ||
                    lower.contains("taxi") ||
                    lower.contains("petrol") ||
                    lower.contains("fuel") ->
                "Transport"

            lower.contains("starbucks") ||
                    lower.contains("coffee") ||
                    lower.contains("restaurant") ||
                    lower.contains("pizza") ||
                    lower.contains("burger") ||
                    lower.contains("food") ->
                "Food"

            lower.contains("amazon") ||
                    lower.contains("flipkart") ->
                "Shopping"

            lower.contains("movie") ||
                    lower.contains("netflix") ->
                "Entertainment"

            else ->
                "Other"

        }

        val merchantRegex =
            Regex("""(?:at|in)\s+([A-Za-z ]+?)(?=\s+(today|yesterday|tomorrow|on|for|$)|$)""",
                RegexOption.IGNORE_CASE)

        val merchant =
            merchantRegex
                .find(text)
                ?.groupValues
                ?.get(1)
                ?.trim()
                ?: ""

        return Expense(
            amount = amount,
            category = category,
            merchant = merchant,
            description = text,
            date = "Today"
        )

    }

}