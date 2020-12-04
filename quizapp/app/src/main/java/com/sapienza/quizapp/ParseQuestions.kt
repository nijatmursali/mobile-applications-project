package com.sapienza.quizapp

data class ParseQuestions (
    val id: Int,
    val question: String,
    val category: String,
    val img: Int,
    val choices1: ParseChoices,
    val choices2: ParseChoices,
    val choices3: ParseChoices,
    val choices4: ParseChoices
)