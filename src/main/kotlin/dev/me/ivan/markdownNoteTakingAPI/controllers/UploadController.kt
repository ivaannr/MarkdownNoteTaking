package dev.me.ivan.markdownNoteTakingAPI.controllers

import dev.me.ivan.markdownNoteTakingAPI.model.MarkdownFile
import dev.me.ivan.markdownNoteTakingAPI.services.MarkdownFileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin(origins = ["http://localhost:5500"])
@RestController
@RequestMapping("/upload")
class UploadController(private val service: MarkdownFileService) {

    @PostMapping
    fun handleMarkdownUpload(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        return try {
            if (file.isEmpty || !file.originalFilename.orEmpty().endsWith(".md")) {
                return ResponseEntity.badRequest().body("Only .md files are allowed")
            }
            val filename = file.originalFilename!!
            val markdownContent = file.inputStream.bufferedReader().use { it.readText() }

            service.createMarkdown(
                MarkdownFile(
                    fileName = filename,
                    content = markdownContent
                )
            )

            ResponseEntity.ok("File $filename has been uploaded correctly")
        } catch (ex: Exception) {
            ResponseEntity.status(500).body("Error processing the file: ${ex.message}")
        }
    }


    @PostMapping("/json")
    fun handleMarkdownJson(@RequestBody markdownFile: MarkdownFile): ResponseEntity<String> {
        return try {
            if (!markdownFile.fileName.endsWith(".md")) {
                return ResponseEntity.badRequest().body("Only .md files are allowed")
            }
            service.createMarkdown(markdownFile)
            ResponseEntity.ok("File ${markdownFile.fileName} has been uploaded correctly")
        } catch (ex: Exception) {
            ResponseEntity.status(500).body("Error processing the file: ${ex.message}")
        }
    }
}