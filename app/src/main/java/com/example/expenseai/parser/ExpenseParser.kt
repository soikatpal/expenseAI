package com.example.expenseai.parser

import com.example.expenseai.model.Expense

object ExpenseParser {

    fun parse(text: String): Expense {

        val cleanText = text.trim()
        val lower = cleanText.lowercase()

        // ---------------------------------------------------------
        // 1. Extract amount
        // ---------------------------------------------------------

        val amountRegex = Regex(
            """(?:₹|rs\.?|rupees?)?\s*(\d+(?:,\d{3})*(?:\.\d+)?)""",
            RegexOption.IGNORE_CASE
        )

        val amount = amountRegex
            .find(cleanText)
            ?.groupValues
            ?.get(1)
            ?.replace(",", "")
            ?.toDoubleOrNull()
            ?: 0.0

        // ---------------------------------------------------------
        // 2. Detect category
        // ---------------------------------------------------------

        val category = when {

            lower.contains("uber") ||
                    lower.contains("ola") ||
                    lower.contains("taxi") ||
                    lower.contains("cab") ||
                    lower.contains("auto") ||
                    lower.contains("rickshaw") ||
                    lower.contains("metro") ||
                    lower.contains("bus") ||
                    lower.contains("train") ||
                    lower.contains("petrol") ||
                    lower.contains("diesel") ||
                    lower.contains("fuel") ||
                    lower.contains("parking") ->
                "Transport"

            lower.contains("starbucks") ||
                    lower.contains("coffee") ||
                    lower.contains("restaurant") ||
                    lower.contains("pizza") ||
                    lower.contains("burger") ||
                    lower.contains("food") ||
                    lower.contains("lunch") ||
                    lower.contains("dinner") ||
                    lower.contains("breakfast") ||
                    lower.contains("snack") ||
                    lower.contains("zomato") ||
                    lower.contains("swiggy") ||
                    lower.contains("domino") ->
                "Food"

            lower.contains("amazon") ||
                    lower.contains("flipkart") ||
                    lower.contains("myntra") ||
                    lower.contains("shopping") ||
                    lower.contains("groceries") ||
                    lower.contains("clothes") ||
                    lower.contains("shoes") ->
                "Shopping"

            lower.contains("movie") ||
                    lower.contains("cinema") ||
                    lower.contains("netflix") ||
                    lower.contains("prime video") ||
                    lower.contains("spotify") ||
                    lower.contains("game") ||
                    lower.contains("concert") ->
                "Entertainment"

            lower.contains("doctor") ||
                    lower.contains("hospital") ||
                    lower.contains("medicine") ||
                    lower.contains("medical") ||
                    lower.contains("pharmacy") ||
                    lower.contains("apollo") ->
                "Medical"

            lower.contains("electricity") ||
                    lower.contains("electric bill") ||
                    lower.contains("water bill") ||
                    lower.contains("internet") ||
                    lower.contains("wifi") ||
                    lower.contains("mobile recharge") ||
                    lower.contains("recharge") ||
                    lower.contains("bill") ->
                "Bills"

            else ->
                "Other"
        }

        // ---------------------------------------------------------
        // 3. Extract merchant
        // ---------------------------------------------------------

        var merchant = ""

        val merchantRegex = Regex(
            """(?:at|in|on)\s+([A-Za-z0-9&.'-]+(?:\s+[A-Za-z0-9&.'-]+)*?)(?=\s+(?:today|yesterday|tomorrow|for|with|using|on)\b|$)""",
            RegexOption.IGNORE_CASE
        )

        val merchantMatch = merchantRegex.find(cleanText)

        if (merchantMatch != null) {

            merchant = merchantMatch
                .groupValues[1]
                .trim()

        }

        // ---------------------------------------------------------
        // 4. Common merchant detection
        // ---------------------------------------------------------

        if (merchant.isBlank()) {

            merchant = when {

                lower.contains("uber") ->
                    "Uber"

                lower.contains("ola") ->
                    "Ola"

                lower.contains("starbucks") ->
                    "Starbucks"

                lower.contains("amazon") ->
                    "Amazon"

                lower.contains("flipkart") ->
                    "Flipkart"

                lower.contains("myntra") ->
                    "Myntra"

                lower.contains("zomato") ->
                    "Zomato"

                lower.contains("swiggy") ->
                    "Swiggy"

                lower.contains("domino") ->
                    "Domino's"

                lower.contains("netflix") ->
                    "Netflix"

                lower.contains("spotify") ->
                    "Spotify"

                lower.contains("apollo") ->
                    "Apollo"

                lower.contains("petrol") ||
                        lower.contains("diesel") ||
                        lower.contains("fuel") ->
                    "Fuel"

                else ->
                    ""

            }

        }

        // ---------------------------------------------------------
        // 5. Create Expense
        // ---------------------------------------------------------

        return Expense(
            amount = amount,
            category = category,
            merchant = merchant,
            note = cleanText,
            expenseDate = System.currentTimeMillis()
        )
    }

    // -------------------------------------------------------------
    // Multiple expense parser
    // -------------------------------------------------------------

    fun parseMultiple(text: String): List<Expense> {

        val cleanText = text.trim()

        if (cleanText.isBlank()) {
            return emptyList()
        }

        val parts = cleanText
            .replace(
                Regex("""\s*,\s*"""),
                " and "
            )
            .split(
                Regex(
                    """\s+(?:and\s+then|then|and)\s+""",
                    RegexOption.IGNORE_CASE
                )
            )
            .map {
                it.trim()
            }
            .filter {
                it.isNotBlank()
            }

        if (parts.size == 1) {

            val expense = parse(parts.first())

            return if (expense.amount > 0.0) {
                listOf(expense)
            } else {
                emptyList()
            }
        }

        return parts
            .map {
                parse(it)
            }
            .filter {
                it.amount > 0.0
            }
    }
}