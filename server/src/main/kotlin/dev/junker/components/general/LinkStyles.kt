package dev.junker.components.general

import dev.junker.components.SiteColor
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.lh
import kotlinx.css.properties.translateY

fun CssBuilder.linkStyles() {
    a {
        primaryTextGlow()
        property("text-decoration-thickness", "from-font")
    }

    rule(skipLink.selector) {
        secondaryTextGlow()
        alignSelf = Align.center
        backgroundColor = SiteColor.BackgroundMedium.color
        border = light2pxBorder
        borderTopStyle = BorderStyle.none
        borderBottomLeftRadius = cornerRadius
        borderBottomRightRadius = cornerRadius
        padding = Padding(1.rem)
        position = Position.absolute
        textDecoration = TextDecoration.none
        transform.translateY((-100).pct)
        zIndex = 1
    }

    rule("${skipLink.selector}::before") {
        content = "↯ ".quoted
        fontWeight = FontWeight.bold
    }

    rule("${skipLink.selector}:focus") {
        transform.translateY(0.px)
    }

    rule("${externalLink.selector}::after") {
        content = " ↗".quoted
        lineHeight = 0.rem.lh
        verticalAlign = VerticalAlign.`super`
    }

    rule(hiddenLink.selector) {
        textWithShadow(Color.inherit, "none")
    }
}
