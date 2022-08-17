package dev.junker.components.main

import dev.junker.components.*
import dev.junker.components.page.*
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CSSBuilder.renderMainContentStyles() {
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
        padding(1.rem)
    }

    rule(terminalOutput.css) {
        fontFamily = "Work Sans, sans-serif"
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
    }
}

fun CSSBuilder.renderMainContentTabletStyles() {
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
            padding(2.rem)
        }
    }
}
