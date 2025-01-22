package dev.junker.components.main

import dev.junker.components.SiteColor
import dev.junker.components.general.ch
import dev.junker.components.general.cornerRadius
import dev.junker.components.general.primaryTextGlow
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CSSBuilder.mainContentStyles() {
    rule(mainContainer.selector) {
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
        padding(vertical = 1.rem, horizontal = 2.ch)
    }

    rule("${terminalPrompt.selector}:before") {
        content = ">".quoted
        paddingRight = 1.ch
        primaryTextGlow()
    }

    rule(outputContainer.selector) {
        padding(1.rem)
    }

    rule(output.selector) {
        fontFamily = "Work Sans, sans-serif"
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
    }
}

fun CSSBuilder.mainContentTabletStyles() {
    rule(mainContainer.selector) {
        display = Display.flex
        justifyContent = JustifyContent.center
    }

    rule(terminalPrompt.selector) {
        borderRadius = cornerRadius
        margin(1.rem)
    }

    rule(outputContainer.selector) {
        padding(2.rem)
    }
}
