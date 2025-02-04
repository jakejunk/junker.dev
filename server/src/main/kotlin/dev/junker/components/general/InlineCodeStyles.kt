package dev.junker.components.general

import dev.junker.components.SiteColor
import kotlinx.css.*
import kotlinx.css.properties.LineHeight

fun CSSBuilder.inlineCodeStyles() {
    rule("code${inlineCode.selector}") {
        backgroundColor = SiteColor.BackgroundLight.color
        borderRadius = 3.px
        monospaceFont(lineHeight = LineHeight.inherit)
        padding(2.px, 6.px)
    }
}
