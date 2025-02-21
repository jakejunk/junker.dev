package dev.junker.syntax

import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxThemes
import kotlinx.browser.document
import kotlinx.html.CODE
import kotlinx.html.dom.create
import kotlinx.html.js.code
import kotlinx.html.span
import org.w3c.dom.HTMLPreElement
import org.w3c.dom.asList
import kotlin.time.measureTime

fun highlightCodeBlocks() {
    val codeBlocks = document.querySelectorAll("pre[data-proglang]")
    if (codeBlocks.length == 0) {
        return
    }

    val time = measureTime {
        for (codeBlock in codeBlocks.asList()) {
            with(codeBlock as HTMLPreElement) {
                val codeString = innerText
                val language = getAttribute("data-proglang") ?: ""
                val highlightedCode = document.create.code {
                    highlightCode(codeString, language)
                }

                textContent = ""
                append(highlightedCode)
            }
        }
    }

    println("Highlighted all code in $time")
}

fun CODE.highlightCode(code: String, language: String) {
    val sortedPhrases = computeAllPhrases(code, language)

    var i = 0
    for (phrase in sortedPhrases) {
        val start = phrase.start
        val end = phrase.end

        if (start < i) {
            continue
        }

        if (start > i) {
            +code.substring(i, start)
        }

        val highlightedCode = code.substring(start, end)
        val highlightClass = phrase.selector()

        span(classes = highlightClass.className) { +highlightedCode }

        i = end
    }

    if (i < code.length) {
        +code.subSequence(i, code.length).toString()
    }
}

private fun computeAllPhrases(code: String, language: String): List<Token> {
    val structure = Highlights.Builder()
        .code(code)
        .theme(SyntaxThemes.darcula(darkMode = true))
        .language(SyntaxLanguage.getByName(language) ?: SyntaxLanguage.DEFAULT)
        .build()
        .getCodeStructure()

    return with(structure) {
        val allPhrases = annotations.map(Token::Annotation) +
            keywords.map(Token::Keyword) +
            strings.map(Token::String) +
            literals.map(Token::Literal) +
            comments.map(Token::Comment) +
            multilineComments.map(Token::Comment)

        allPhrases.sortedBy { it.location.start }
    }
}
