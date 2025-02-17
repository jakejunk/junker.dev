package dev.junker.syntax

import dev.junker.util.Selector
import dev.junker.util.classSelector
import dev.snipme.highlights.model.PhraseLocation
import kotlin.jvm.JvmInline

val annotation = "annotation".classSelector()
val keyword = "keyword".classSelector()
val string = "string".classSelector()
val literal = "literal".classSelector()
val comment = "comment".classSelector()

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

    @JvmInline
    value class Annotation(
        override val location: PhraseLocation
    ) : Token

    @JvmInline
    value class Keyword(
        override val location: PhraseLocation
    ) : Token

    @JvmInline
    value class String(
        override val location: PhraseLocation
    ) : Token

    @JvmInline
    value class Literal(
        override val location: PhraseLocation
    ) : Token

    @JvmInline
    value class Comment(
        override val location: PhraseLocation
    ) : Token
}