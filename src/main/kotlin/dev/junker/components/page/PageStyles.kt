package dev.junker.components.page

import dev.junker.components.*
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CSSBuilder.renderPageStyles() {
    rule("h1, h2") {
        monospaceFont()
        primaryTextGlow()
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
        margin(vertical = 2.rem)
    }

    rule("h1") {
        fontSize = 2.rem
        textAlign = TextAlign.center
    }

    rule("h1:before") {
        content = "/".quoted
        color = SiteColor.PrimaryText.color
    }

    rule("h1.error") {
        secondaryTextGlow()
    }

    rule("h1.error:before") {
        content = "!".quoted
    }

    rule("h2") {
        textAlign = TextAlign.center
    }

    rule("h2:before") {
        content = "#".quoted
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

fun CSSBuilder.renderPageTabletStyles() {
    tabletOrLarger {
        rule("h1") {
            fontSize = 3.rem
            textAlign = TextAlign.center
        }

        rule("h2") {
            fontSize = 2.rem
            textAlign = TextAlign.left
        }
    }
}
