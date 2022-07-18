package dev.junker.index

import dev.junker.SiteColor
import kotlinx.css.*

fun CSSBuilder.renderIndexStyles() {
    rule("*, :after, :before") {
        boxSizing = BoxSizing.inherit
    }

    html {
        boxSizing = BoxSizing.borderBox
        color = SiteColor.PrimaryText.color
        fontFamily = "monospace"
        fontSize = 110.pct
        height = 100.pct
    }

    body {
        backgroundColor = SiteColor.BackgroundDark.color
        display = Display.flex
        flexDirection = FlexDirection.column
        height = 100.pct
        margin(0.px)
    }

    rule(".terminal") {
        alignSelf = Align.center
        borderRadius = 12.px
        display = Display.flex
        height = 100.pct
        margin(top = 48.px, bottom = 48.px)
        maxWidth = 1400.px
        overflow = Overflow.hidden
        width = 100.pct
    }

    rule(".terminal > header") {
        backgroundColor = SiteColor.TerminalHeader.color
        height = 100.pct
        padding(vertical = 2.rem, horizontal = LinearDimension("4ch"))
    }

    rule(".terminal-main") {
        backgroundColor = SiteColor.TerminalBackground.color
        flexGrow = 1.0
        padding(vertical = 2.rem, horizontal = LinearDimension("4ch"))
    }
}
