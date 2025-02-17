package dev.junker.syntax

import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxThemes
import kotlinx.html.CODE
import kotlinx.html.span

fun CODE.highlightCode(code: String) {
    val sortedPhrases = computeAllPhrases(code)

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

private fun computeAllPhrases(code: String): List<Token> {
    val structure = Highlights.Builder()
        .code(code)
        .theme(SyntaxThemes.darcula(darkMode = true))
        .language(SyntaxLanguage.KOTLIN)
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
