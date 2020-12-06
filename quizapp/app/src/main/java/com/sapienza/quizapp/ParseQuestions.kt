package com.sapienza.quizapp

data class ParseQuestions(
    val id: String,
    val question: String,
    val category: String,
    val img: String,
    val choices1: ParseChoices,
    val choices2: ParseChoices,
    val choices3: ParseChoices,
    val choices4: ParseChoices
)