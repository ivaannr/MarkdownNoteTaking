package dev.me.ivan.markdownNoteTakingAPI.controllers

import dev.me.ivan.markdownNoteTakingAPI.model.MarkdownFile
import dev.me.ivan.markdownNoteTakingAPI.services.MarkdownFileService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/files")
class MarkdownFileController(private val service: MarkdownFileService) {

    @GetMapping
    fun getMarkdownFile(): List<MarkdownFile> {
        return service.getMarkdown()
    }
    @DeleteMapping("/{fileID}", produces = [MediaType.TEXT_HTML_VALUE])
    fun deleteMarkdownFile(@PathVariable fileID: String): ResponseEntity<String> {
        return try {
            service.delete(fileID)
            ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body("<h4>Markdown deleted</h4>")
        } catch (ex: NoSuchElementException) {
            ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_HTML)
                .body("<h4>File not found</h4>")
        } catch (ex: Exception) {
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.TEXT_HTML)
                .body("<h4>Internal server error</h4>")
        }
    }

    @PostMapping
    fun createMarkdownFile(@RequestBody file: MarkdownFile): MarkdownFile {
        return service.createMarkdown(file)
    }

    @PatchMapping
    fun patchMarkdownFile(@RequestBody file: MarkdownFile): MarkdownFile {
        return service.patchMarkdown(file)
    }

}