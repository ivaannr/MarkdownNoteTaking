package dev.me.ivan.markdownNoteTakingAPI.model

data class GrammarError(
    val language: String,
    val text: String,
    val error: String
)
