package dev.junker.components

import dev.junker.*
import kotlinx.css.*

fun CSSBuilder.renderTerminalMainStyles() {
    rule(".terminal-main") {
        beveledTerminalSurface()
        flexGrow = 1.0
        overflowY = Overflow.auto
        paddingBottom = 8.rem
        wordBreak = WordBreak.breakWord
    }

    rule(".terminal-prompt") {
        backgroundColor = SiteColor.BackgroundDark.color
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
        marginBottom = 1.rem
    }

    rule(".terminal-prompt:before") {
        content = ">".quoted
        paddingRight = LinearDimension("1ch")
        primaryTextGlow()
    }

    rule(".terminal-output") {
        padding(vertical = 2.rem, horizontal = LinearDimension("2ch"))
    }
}

fun CSSBuilder.renderTerminalMainTabletStyles() {
    tabletOrLarger {
        rule(".terminal-main") {
            borderTopRightRadius = LinearDimension("1ch")
            borderBottomRightRadius = LinearDimension("1ch")
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
            borderRadius = LinearDimension("1ch")
            margin(1.rem)
        }

        rule(".terminal-output") {
            padding(vertical = 2.rem, horizontal = 1.rem + LinearDimension("2ch"))
        }
    }
}
