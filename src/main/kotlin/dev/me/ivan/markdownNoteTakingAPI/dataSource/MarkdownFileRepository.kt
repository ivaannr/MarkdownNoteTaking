package dev.me.ivan.markdownNoteTakingAPI.dataSource

import dev.me.ivan.markdownNoteTakingAPI.database.MarkdownFiles
import dev.me.ivan.markdownNoteTakingAPI.model.MarkdownFile
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

@Repository
class MarkdownFileRepository {

    fun getMarkdown(): List<MarkdownFile> = transaction {
        MarkdownFiles.selectAll().map {
            MarkdownFile(
                fileID = it[MarkdownFiles.fileID],
                fileName = it[MarkdownFiles.fileName],
                content = it[MarkdownFiles.content]
            )
        }
    }

    fun deleteFile(fileID: String) = transaction {
        val deletedCount = MarkdownFiles.deleteWhere { MarkdownFiles.fileID eq fileID }
        if (deletedCount == 0) {
            throw NoSuchElementException("No markdown file found in the database.")
        }
    }

    fun addFile(file: MarkdownFile): MarkdownFile = transaction {
        val exists = MarkdownFiles.select { MarkdownFiles.fileID eq file.fileID }.count() > 0
        if (exists) {
            throw IllegalArgumentException("Cannot add note with ID ${file.fileID}, it already exists.")
        }
        MarkdownFiles.insert {
            it[fileID] = file.fileID
            it[fileName] = file.fileName
            it[content] = file.content
        }
        file
    }

    fun patchFile(file: MarkdownFile): MarkdownFile = transaction {
        val updated = MarkdownFiles.update({ MarkdownFiles.fileID eq file.fileID }) {
            it[fileName] = file.fileName
            it[content] = file.content
        }
        if (updated == 0) {
            throw NoSuchElementException("No note found in the database with ID ${file.fileID}.")
        }
        file
    }


    fun findById(fileID: String): MarkdownFile? = transaction {
        MarkdownFiles.select { MarkdownFiles.fileID eq fileID }
            .map {
                MarkdownFile(
                    it[MarkdownFiles.fileID],
                    it[MarkdownFiles.fileName],
                    it[MarkdownFiles.content]
                )
            }.singleOrNull()
    }

}
