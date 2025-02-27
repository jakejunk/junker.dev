package dev.junker.components.general

import dev.junker.classSelector
import kotlinx.html.FlowContent
import kotlinx.html.SECTION
import kotlinx.html.div
import kotlinx.html.img

val imageBanner = "img-banner".classSelector()
val imageRight = "img-right".classSelector()

fun FlowContent.rightFloatImage(
    alt: String,
    src: String,
    exclamation: String? = null
) {
    div(classes = imageRight.className) {
        if (exclamation != null) {
            attributes["data-exclamation"] = exclamation
        }
        img(alt = alt, src = src)
    }
}
