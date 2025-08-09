package dev.me.ivan.markdownNoteTakingAPI.controllers

import dev.me.ivan.markdownNoteTakingAPI.model.GrammarError
import dev.me.ivan.markdownNoteTakingAPI.services.MarkdownFileService
import dev.me.ivan.markdownNoteTakingAPI.utils.Tools
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/grammar")
class GrammarCheckerController(private val service: MarkdownFileService) {

    @GetMapping("/{fileID}")
    fun getGrammaticalErrors(@PathVariable("fileID") fileID: String): ResponseEntity<String> {
        return try {
            val allFiles = service.getMarkdown()
            val markdownFile = allFiles.find { it.fileID == fileID }

            if (markdownFile != null) {
                val text = Tools.markdownToPlainText(markdownFile.content)
                val errors = Tools.checkGrammar(text)

                ResponseEntity.ok()
                    .contentType(MediaType.TEXT_MARKDOWN)
                    .body(Tools.grammarErrorsToJson(errors))
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("""
                    ```
                    File not found!
                    ```
                """.trimIndent()
                    )
            }
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("""
                ```
                Internal Server Error:
                ${ex.message}
                ```
            """.trimIndent())
        }
    }

}