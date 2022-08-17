package dev.junker.components.main

import dev.junker.*
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
        padding(vertical = 2.rem, horizontal = LinearDimension("2ch"))
    }

    rule(terminalOutput.css) {
        fontFamily = "Work Sans, sans-serif"
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
    }

    rule(".heading") {
        monospaceFont()
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

    rule("a") {
        primaryTextGlow()
        property("text-decoration-thickness", "from-font")
    }

    rule("i") {
        color = SiteColor.SubtleText.color
    }

    rule("hr") {
        backgroundColor = SiteColor.BackgroundLight.color
        borderColor = SiteColor.BackgroundLight.color
        borderStyle = BorderStyle.solid
        maxWidth = 128.px
        height = 2.px
        margin(vertical = 3.rem, horizontal = LinearDimension.auto)
    }

    rule("sup") {
        lineHeight = 0.rem.lh
    }

    rule(".footnotes") {
        color = SiteColor.SubtleText.color
        fontStyle = FontStyle.italic
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
            padding(vertical = 2.rem, horizontal = 1.rem + LinearDimension("2ch"))
        }

        rule("h1.heading") {
            fontSize = 3.rem
            textAlign = TextAlign.center
        }

        rule("h2.heading") {
            fontSize = 2.rem
            textAlign = TextAlign.left
        }
    }
}
