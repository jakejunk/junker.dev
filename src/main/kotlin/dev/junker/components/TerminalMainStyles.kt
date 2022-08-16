package dev.junker.components

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CSSBuilder.renderTerminalMainStyles() {
    rule(mainContentContainer.css) {
        flexGrow = 1.0
        paddingBottom = 8.rem
        wordBreak = WordBreak.breakWord
    }

    rule(mainContent.css) {
        width = 100.pct
        maxWidth = 1100.px
    }

    rule(terminalPrompt.css) {
        backgroundColor = SiteColor.BackgroundLight.color
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
        marginBottom = 1.rem
    }

    rule("${terminalPrompt.css}:before") {
        content = ">".quoted
        paddingRight = LinearDimension("1ch")
        primaryTextGlow()
    }

    rule(terminalOutputContainer.css) {
        padding(vertical = 2.rem, horizontal = LinearDimension("2ch"))
    }

    rule("${terminalOutput.css} p") {
        fontFamily = "Work Sans, sans-serif"
        lineHeight = 1.75.rem.lh
    }

    rule(".heading") {
        primaryTextGlow()
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
        margin(vertical = 2.rem)
    }

    rule("h2.heading") {
        textAlign = TextAlign.center
    }

    rule("h2.heading:before") {
        content = "#".quoted
        color = SiteColor.PrimaryText.color
    }

    rule("h1.heading") {
        textAlign = TextAlign.center
    }

    rule("h1.heading:before") {
        content = "/".quoted
        color = SiteColor.PrimaryText.color
    }
}

fun CSSBuilder.renderTerminalMainTabletStyles() {
    tabletOrLarger {
        rule(mainContentContainer.css) {
            display = Display.flex
            justifyContent = JustifyContent.center
        }

        rule(terminalPrompt.css) {
            borderRadius = LinearDimension("1ch")
            margin(1.rem)
        }

        rule(terminalOutputContainer.css) {
            padding(vertical = 2.rem, horizontal = 1.rem + LinearDimension("2ch"))
        }

        rule("h1.heading") {
            fontSize = 3.rem
            textAlign = TextAlign.initial
        }

        rule("h2.heading") {
            fontSize = 2.rem
        }
    }
}
