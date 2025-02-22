package dev.junker.components.background

import dev.junker.classSelector
import kotlinx.html.BODY
import kotlinx.html.div

val mainBackground = "main-bg".classSelector()

fun BODY.background() {
    div(classes = mainBackground.className)
}
