package dev.junker.components.general

import dev.junker.util.asClass
import kotlinx.html.P
import kotlinx.html.code

val inlineCode = "inline-code".asClass()

fun P.inlineCode(value: String) {
    code(classes = inlineCode.className) { +value }
}
