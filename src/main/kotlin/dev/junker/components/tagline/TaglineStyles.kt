package dev.junker.components.tagline

import dev.junker.components.SiteColor
import dev.junker.components.general.ch
import kotlinx.css.*

fun CSSBuilder.taglineStyles() {
    rule(tagline.selector) {
        color = SiteColor.SubtleText.color
        fontSize = 1.rem
        fontStyle = FontStyle.italic;
        fontWeight = FontWeight.lighter
        margin(2.ch, 0.px, 0.px, LinearDimension.auto)
        paddingRight = 1.ch
        textAlign = TextAlign.right
    }
}

fun CSSBuilder.taglineTabletStyles() {
    rule(tagline.selector) {
        margin(2.ch, 0.px, 0.px, 0.px)
        textAlign = TextAlign.center
    }
}
