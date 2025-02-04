package dev.junker.components.background

import dev.junker.util.asClass
import kotlinx.html.BODY
import kotlinx.html.div

val mainBackground = "main-bg".asClass()

fun BODY.background() {
    div(classes = mainBackground.className)
}
