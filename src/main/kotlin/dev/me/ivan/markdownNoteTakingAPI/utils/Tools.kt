package dev.me.ivan.markdownNoteTakingAPI.utils

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dev.me.ivan.markdownNoteTakingAPI.model.GrammarError
import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.jsoup.Jsoup
import org.languagetool.JLanguageTool
import org.languagetool.language.*
import org.languagetool.rules.RuleMatch
import org.jetbrains.exposed.sql.Table

object Tools {

    private val parser: Parser = Parser.builder().build()
    private val renderer: HtmlRenderer = HtmlRenderer.builder().build()

    fun toHtml(markdown: String): String {
        val document = parser.parse(markdown)
        return renderer.render(document)
    }

    fun markdownToPlainText(markdown: String): String = Jsoup.parse(toHtml(markdown)).text()

    fun plainTextToMarkdownCodeBlock(text: String): String {
       return """
            ```
            $text
            ```
        """.trimIndent()
    }


    fun checkGrammar(text: String): List<GrammarError> {
        val detectedCode = detectLanguage(text).lowercase()

        val languageInstance = when (detectedCode) {
            "es", "an", "ast" -> Spanish()
            "en" -> BritishEnglish()
            "fr" -> French()
            "de" -> GermanyGerman()
            "it" -> Italian()
            "pt" -> Portuguese()
            "ru" -> Russian()
            "nl" -> Dutch()
            else -> throw IllegalArgumentException("Unsupported language: $detectedCode")
        }

        val tool = JLanguageTool(languageInstance)
        val matches: List<RuleMatch> = tool.check(text)

        return matches.map { match ->
            val errorMessage = buildString {
                append(match.message)
                if (match.suggestedReplacements.isNotEmpty()) {
                    append(" → Suggestions: ${match.suggestedReplacements.joinToString()}")
                }
                append(" (position ${match.fromPos}-${match.toPos})")
            }

            GrammarError(
                language = languageInstance.name,
                text = text.substring(match.fromPos, match.toPos),
                error = errorMessage
            )
        }
    }

    private fun detectLanguage(text: String): String {
        val detector = OptimaizeLangDetector().loadModels()
        val result = detector.detect(text)
        return result.language
    }

    fun grammarErrorsToJson(errors: List<GrammarError>): String {
        val mapper = jacksonObjectMapper().apply {
            registerKotlinModule()
            enable(SerializationFeature.INDENT_OUTPUT)
        }
        return mapper.writeValueAsString(errors)
    }

}



