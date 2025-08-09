
package dev.me.ivan.markdownNoteTakingAPI.controllers
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
@RequestMapping("/render")
class RenderController(private val service: MarkdownFileService) {

    @GetMapping("/{fileID}", produces = [MediaType.TEXT_HTML_VALUE])
    fun renderMarkdownFile(@PathVariable("fileID") fileID: String): ResponseEntity<String> {
        val allFiles = service.getMarkdown()
        val markdownFile = allFiles.find { it.fileID == fileID }

        return if (markdownFile != null) {
            val html = Tools.toHtml(markdownFile.content)
            ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("<h1>File not found</h1>")
        }
    }


    @GetMapping
    fun listAvailableFiles(): ResponseEntity<String> {
        val htmlList = service.getMarkdown().joinToString("<br>") {
            "<a href=\"/render/${it.fileID}\">${it.fileName}</a>"
        }

        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_HTML)
            .body("<h1>Available files</h1><p>$htmlList</p>")
    }

}