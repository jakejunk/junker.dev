package dev.junker.syntax

import dev.junker.*
import dev.snipme.highlights.model.PhraseLocation

sealed interface Token {
    val location: PhraseLocation

    val start: Int
        get() = location.start

    val end: Int
        get() = location.end

    fun selector(): Selector.Class {
        return when (this) {
            is Annotation -> annotation
            is Keyword -> keyword
            is String -> string
            is Literal -> literal
            is Comment -> comment
        }
    }

    value class Annotation(
        override val location: PhraseLocation
    ) : Token

    value class Keyword(
        override val location: PhraseLocation
    ) : Token

    value class String(
        override val location: PhraseLocation
    ) : Token

    value class Literal(
        override val location: PhraseLocation
    ) : Token

    value class Comment(
        override val location: PhraseLocation
    ) : Token
}