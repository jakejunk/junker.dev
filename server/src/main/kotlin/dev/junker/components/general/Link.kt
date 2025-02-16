package dev.junker.components.general

import dev.junker.components.main.main
import dev.junker.util.classSelector
import kotlinx.html.*

val skipLink = "skip-link".classSelector()
val externalLink = "external-link".classSelector()
val hiddenLink = "hidden-link".classSelector()

fun FlowOrInteractiveOrPhrasingContent.skipLink() {
    a(classes = skipLink.className, href = main.selector) {
        +"Skip to content"
    }
}

fun FlowOrInteractiveOrPhrasingContent.externalLink(text: String, href: String) {
    a(classes = externalLink.className, href = href) { +text }
}

fun FlowOrInteractiveOrPhrasingContent.hiddenLink(text: String, href: String) {
    a(classes = hiddenLink.className, href = href) {
        attributes["aria-label"] = text
        tabIndex = "-1"
        +text
    }
}
