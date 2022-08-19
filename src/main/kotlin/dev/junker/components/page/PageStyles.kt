package dev.junker.components.page

import dev.junker.components.*
import kotlinx.css.*
import kotlinx.css.Float
import kotlinx.css.properties.lh

fun CSSBuilder.renderPageStyles() {
    rule("h1, h2") {
        monospaceFont()
        primaryTextGlow()
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
        margin(vertical = 2.rem)
    }

    h1 {
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

    h2 {
        textAlign = TextAlign.center
    }

    rule("h2:before") {
        content = "#".quoted
        color = SiteColor.PrimaryText.color
    }

    a {
        primaryTextGlow()
        property("text-decoration-thickness", "from-font")
    }

    i {
        color = SiteColor.SubtleText.color
    }

    hr {
        backgroundColor = SiteColor.BackgroundLight.color
        borderColor = SiteColor.BackgroundLight.color
        borderStyle = BorderStyle.solid
        maxWidth = 128.px
        height = 2.px
        margin(vertical = 3.rem, horizontal = LinearDimension.auto)
    }

    sup {
        lineHeight = 0.rem.lh
    }

    rule(".footnotes") {
        color = SiteColor.SubtleText.color
        fontStyle = FontStyle.italic
    }

    rule(".ascii-banner-container") {
        display = Display.flex
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.center
        overflowX = Overflow.hidden
    }

    rule(".ascii-banner-group") {
        display = Display.flex
    }

    rule(".ascii-banner") {
        monospaceFont()
        fontSize = 2.25.vw
        lineHeight = 2.8.vw.lh
        fontWeight = FontWeight.normal
        whiteSpace = WhiteSpace.pre
    }

    rule(".ascii-banner.junker") {
        primaryTextGlow()
    }

    rule(".ascii-banner.dot") {
        secondaryTextGlow()
    }

    rule(".ascii-banner.dev") {
        color = SiteColor.PrimaryText.color
    }

    rule(".img-right") {
        display = Display.block
        border = light2pxBorder
        borderRadius = 1.rem
        width = 200.px
        height = 200.px
        padding(8.px)
        margin(left = LinearDimension.auto, right = LinearDimension.auto, top = 0.px, bottom = 2.rem)
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

        rule(".ascii-banner") {
            fontSize = LinearDimension.inherit
            lineHeight = 1.25.rem.lh
        }

        rule(".img-right") {
            width = 256.px
            height = 256.px
            float = Float.right
            marginLeft = 2.rem
        }
    }
}
