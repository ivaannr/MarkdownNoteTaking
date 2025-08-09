package dev.me.ivan.markdownNoteTakingAPI.services

import dev.me.ivan.markdownNoteTakingAPI.dataSource.MarkdownFileRepository
import dev.me.ivan.markdownNoteTakingAPI.model.MarkdownFile
import org.springframework.stereotype.Service

@Service
class MarkdownFileService(private val repository: MarkdownFileRepository) {

    fun getMarkdown(): List<MarkdownFile> {
        return repository.getMarkdown()
    }

    fun delete(fileID: String) {
        repository.deleteFile(fileID)
    }

    fun createMarkdown(file: MarkdownFile): MarkdownFile {
        return repository.addFile(file)
    }

    fun patchMarkdown(file: MarkdownFile): MarkdownFile {
        return repository.patchFile(file)
    }
}