package dev.junker.components

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CSSBuilder.renderTerminalMainStyles() {
    rule(terminalMain.css) {
        flexGrow = 1.0
        overflowY = Overflow.auto
        paddingBottom = 8.rem
        wordBreak = WordBreak.breakWord
    }

    rule(terminalPrompt.css) {
        backgroundColor = SiteColor.BackgroundDark.color
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
        marginBottom = 1.rem
    }

    rule("${terminalPrompt.css}:before") {
        content = ">".quoted
        paddingRight = LinearDimension("1ch")
        primaryTextGlow()
    }

    rule(terminalOutput.css) {
        padding(vertical = 2.rem, horizontal = LinearDimension("2ch"))
    }

    rule(terminalOutputContent.css) {
        fontFamily = "Work Sans, sans-serif"
        lineHeight = 1.75.rem.lh
    }

    rule(".heading") {
        primaryTextGlow()
        fontWeight = FontWeight.normal
        margin(vertical = 2.rem)
    }

    rule(".heading:before") {
        content = "#".quoted
        opacity = 0.25
    }
}

fun CSSBuilder.renderTerminalMainTabletStyles() {
    tabletOrLarger {
        rule(terminalMain.css) {
//            borderTopRightRadius = 1.rem
//            borderBottomRightRadius = 1.rem
        }

        // For Firefox
        rule(terminalMain.css) {
            property("scrollbar-width", "thin")
            property("scrollbar-color", "${SiteColor.BackgroundDark.color.value} transparent")
        }

        rule("${terminalMain.css}::-webkit-scrollbar") {
            width = LinearDimension("2ch")
        }

        rule("${terminalMain.css}::-webkit-scrollbar-thumb") {
            backgroundColor = SiteColor.BackgroundMedium.color
            border = "solid 0.5ch ${SiteColor.BackgroundLight.color.value}"
            borderRadius = LinearDimension("1ch")
        }

        rule("${terminalMain.css}::-webkit-scrollbar-thumb:hover") {
            backgroundColor = SiteColor.BackgroundDark.color
        }

        rule(terminalPrompt.css) {
            borderRadius = LinearDimension("1ch")
            margin(1.rem)
        }

        rule(terminalOutput.css) {
            padding(vertical = 2.rem, horizontal = 1.rem + LinearDimension("2ch"))
        }
    }
}
