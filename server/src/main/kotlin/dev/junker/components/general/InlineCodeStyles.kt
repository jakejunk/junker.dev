package dev.junker.components.general

import dev.junker.components.SiteColor
import kotlinx.css.*
import kotlinx.css.properties.LineHeight

fun CssBuilder.inlineCodeStyles() {
    rule("code${inlineCode.selector}") {
        monospaceFont(lineHeight = LineHeight.inherit)
        backgroundColor = SiteColor.BackgroundLight.color
        borderRadius = 3.px
        padding = Padding(2.px, 6.px)
    }
}
