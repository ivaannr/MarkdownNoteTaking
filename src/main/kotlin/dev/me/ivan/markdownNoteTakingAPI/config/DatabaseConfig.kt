package dev.me.ivan.markdownNoteTakingAPI.config

import dev.me.ivan.markdownNoteTakingAPI.database.MarkdownFiles
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class DatabaseConfig {

    @PostConstruct
    fun init() {
        Database.connect("jdbc:sqlite:myDB.db", driver = "org.sqlite.JDBC")

        transaction {
            SchemaUtils.create(MarkdownFiles)
        }
    }
}