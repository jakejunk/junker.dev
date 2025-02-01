package dev.junker.components.general

import kotlinx.html.SECTION
import kotlinx.html.div
import kotlinx.html.img

fun SECTION.rightFloatImage(alt: String, src: String) {
    div(classes = "img-right") {
        img(alt = alt, src = src)
    }
}
