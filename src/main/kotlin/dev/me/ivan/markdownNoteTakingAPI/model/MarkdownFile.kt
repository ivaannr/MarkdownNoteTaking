package dev.me.ivan.markdownNoteTakingAPI.model

import java.util.UUID

data class MarkdownFile(
    val fileID: String = UUID.randomUUID().toString(),
    val fileName: String,
    val content: String
)