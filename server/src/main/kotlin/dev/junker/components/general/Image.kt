package dev.junker.components.general

import dev.junker.util.classSelector
import kotlinx.html.SECTION
import kotlinx.html.div
import kotlinx.html.img

val imageRight = "img-right".classSelector()

fun SECTION.rightFloatImage(alt: String, src: String) {
    div(classes = imageRight.className) {
        img(alt = alt, src = src)
    }
}
