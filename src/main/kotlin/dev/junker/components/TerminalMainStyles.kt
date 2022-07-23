package dev.junker.components

import dev.junker.SiteColor
import dev.junker.beveledTerminalSurface
import dev.junker.primaryTextGlow
import dev.junker.property
import kotlinx.css.*

fun CSSBuilder.renderTerminalMainStyles() {
    rule(".terminal-main") {
        beveledTerminalSurface()
        borderTopRightRadius = LinearDimension("1ch")
        borderBottomRightRadius = LinearDimension("1ch")
        flexGrow = 1.0
        overflowY = Overflow.auto
        paddingBottom = 8.rem
    }

    // For Firefox
    rule(".terminal-main") {
        property("scrollbar-width", "thin")
        property("scrollbar-color", "${SiteColor.BackgroundDark.color.value} transparent")
    }

    rule(".terminal-main::-webkit-scrollbar") {
        width = LinearDimension("2ch")
    }

    rule(".terminal-main::-webkit-scrollbar-thumb") {
        backgroundColor = SiteColor.BackgroundMedium.color
        border = "solid 0.5ch ${SiteColor.BackgroundLight.color.value}"
        borderRadius = LinearDimension("1ch")
    }

    rule(".terminal-main::-webkit-scrollbar-thumb:hover") {
        backgroundColor = SiteColor.BackgroundDark.color
    }

    rule(".terminal-prompt") {
        backgroundColor = SiteColor.BackgroundDark.color
        borderRadius = LinearDimension("1ch")
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
        margin(1.rem)
    }

    rule(".terminal-prompt:before") {
        content = ">".quoted
        paddingRight = LinearDimension("1ch")
        primaryTextGlow()
    }

    rule(".terminal-output") {
        padding(vertical = 2.rem, horizontal = 1.rem + LinearDimension("2ch"))
    }
}
