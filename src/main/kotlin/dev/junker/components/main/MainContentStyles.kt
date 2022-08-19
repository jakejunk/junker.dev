package dev.junker.components.main

import dev.junker.components.*
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CSSBuilder.renderMainContentStyles() {
    rule(mainContentContainer.selector) {
        flexGrow = 1.0
        paddingBottom = 8.rem
        wordBreak = WordBreak.breakWord
    }

    rule(mainContent.selector) {
        width = 100.pct
        maxWidth = 1100.px
    }

    rule(terminalPrompt.selector) {
        backgroundColor = SiteColor.BackgroundLight.color
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
        marginBottom = 1.rem
    }

    rule("${terminalPrompt.selector}:before") {
        content = ">".quoted
        paddingRight = LinearDimension("1ch")
        primaryTextGlow()
    }

    rule(terminalOutputContainer.selector) {
        padding(1.rem)
    }

    rule(terminalOutput.selector) {
        fontFamily = "Work Sans, sans-serif"
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
    }
}

fun CSSBuilder.renderMainContentTabletStyles() {
    tabletOrLarger {
        rule(mainContentContainer.selector) {
            display = Display.flex
            justifyContent = JustifyContent.center
        }

        rule(terminalPrompt.selector) {
            borderRadius = LinearDimension("1ch")
            margin(1.rem)
        }

        rule(terminalOutputContainer.selector) {
            padding(2.rem)
        }
    }
}
