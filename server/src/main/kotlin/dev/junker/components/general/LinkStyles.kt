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

    skipLink.selector {
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

    "${skipLink.selector}::before" {
        content = "↯ ".quoted
        fontWeight = FontWeight.bold
    }

    "${skipLink.selector}:focus" {
        transform.translateY(0.px)
    }

    "${externalLink.selector}::after" {
        content = " ↗".quoted
        lineHeight = 0.rem.lh
        verticalAlign = VerticalAlign.`super`
    }

    hiddenLink.selector {
        textWithShadow(Color.inherit, "none")
    }
}
