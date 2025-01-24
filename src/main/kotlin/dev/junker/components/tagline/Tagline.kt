package dev.junker.components.tagline

import dev.junker.util.asClass
import kotlinx.html.FlowContent
import kotlinx.html.h3

val tagline = "tagline".asClass()

fun FlowContent.tagline(text: String) {
    h3(classes = tagline.className) { +text }
}
