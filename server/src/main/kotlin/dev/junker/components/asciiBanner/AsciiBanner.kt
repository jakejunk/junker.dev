package dev.junker.components.asciiBanner

import dev.junker.classSelector
import kotlinx.html.*

val asciiBannerContainer = "ascii-banner-container".classSelector()
val asciiBannerGroup = "ascii-banner-group".classSelector()
val asciiBanner = "ascii-banner".classSelector()

/*
attributes["aria-label"] = trimmedText

for (char in trimmedText) {
    when (char) {
        ' ' -> +char.toString()
        else -> span { +char.toString() }
    }
}
*/

fun FlowContent.asciiBanner() {
    div(classes = asciiBannerContainer.className) {
        role = "heading"
        attributes["aria-level"] = "1"
        attributes["aria-label"] = "junker.dev"
        span(classes = "${asciiBanner.className} junker")
        span(classes = asciiBannerGroup.className) {
            span(classes = "${asciiBanner.className} dot")
            span(classes = "${asciiBanner.className} dev")
        }
    }
}
