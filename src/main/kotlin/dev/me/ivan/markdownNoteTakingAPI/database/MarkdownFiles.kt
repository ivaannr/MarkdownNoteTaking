package dev.me.ivan.markdownNoteTakingAPI.database
import org.jetbrains.exposed.sql.Table

object MarkdownFiles : Table("markdown_files") {
    val fileID = varchar("fileID", 50)
    val fileName = varchar("fileName", 255)
    val content = text("content")

    override val primaryKey = PrimaryKey(fileID)
}