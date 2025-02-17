package dev.junker.components.tagline

import dev.junker.components.SiteColor
import kotlinx.css.*

fun CssBuilder.taglineStyles() {
    tagline.selector {
        color = SiteColor.SubtleText.color
        fontSize = 1.rem
        fontStyle = FontStyle.italic;
        fontWeight = FontWeight.lighter
        margin = Margin(2.ch, 0.px, 0.px, LinearDimension.auto)
        paddingRight = 1.ch
        textAlign = TextAlign.right
    }
}

fun CssBuilder.taglineTabletStyles() {
    tagline.selector {
        margin = Margin(2.ch, 0.px, 0.px, 0.px)
        textAlign = TextAlign.center
    }
}
