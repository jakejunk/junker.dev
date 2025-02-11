package dev.junker.components.general

import kotlinx.html.FlowContent
import kotlinx.html.P
import kotlinx.html.code
import kotlinx.html.pre

fun FlowContent.inlineCode(value: String) {
    code { +value }
}

fun FlowContent.codeBlock(value: String) {
    pre { code { +value } }
}
