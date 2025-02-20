package dev.junker.components.tagline

import dev.junker.classSelector
import kotlinx.html.FlowContent
import kotlinx.html.h3

val tagline = "tagline".classSelector()

fun FlowContent.tagline(text: String) {
    h3(classes = tagline.className) { +text }
}
