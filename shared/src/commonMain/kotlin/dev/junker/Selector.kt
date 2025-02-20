package dev.junker

import kotlin.jvm.JvmInline

// Syntax highlighting
val annotation = "annotation".classSelector()
val keyword = "keyword".classSelector()
val string = "string".classSelector()
val literal = "literal".classSelector()
val comment = "comment".classSelector()

sealed interface Selector {
    val selector: String

    @JvmInline
    value class Class(
        val className: String
    ) : Selector {
        override val selector get() = ".$className"
    }

    @JvmInline
    value class Id(
        val id: String
    ) : Selector {
        override val selector get() = "#$id"
    }
}

fun String.classSelector() = Selector.Class(this)

fun String.idSelector() = Selector.Id(this)
