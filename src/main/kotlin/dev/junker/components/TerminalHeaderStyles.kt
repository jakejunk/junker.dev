package dev.junker.components

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration

fun CSSBuilder.renderTerminalHeaderStyles() {
    rule(".terminal-header") {
        beveledTerminalSurface()
        flexColumn()
        alignItems = Align.center
        borderTopLeftRadius = LinearDimension("1ch")
        borderBottomLeftRadius = LinearDimension("1ch")
        height = 100.pct
    }

    rule(".site-logo-image") {
        backgroundImage = Image("url(/assets/images/16x16_logo_black.png)")
        backgroundSize = "contain"
        backgroundRepeat = BackgroundRepeat.noRepeat
        filter = "invert(74%) sepia(47%) saturate(462%) hue-rotate(115deg) brightness(98%) contrast(95%) drop-shadow(0px 0px 1ch ${SiteColor.Primary.color.value})"
        height = LinearDimension("8ch")
        property("image-rendering", "pixelated")
        width = LinearDimension("8ch")
        margin(2.rem, 2.rem, 2.rem, 2.rem)
    }

    rule(".site-logo-image.error") {
        filter = "invert(60%) sepia(30%) saturate(645%) hue-rotate(318deg) brightness(106%) contrast(90%) drop-shadow(0px 0px 1ch ${SiteColor.Secondary.color.value})"
    }

    rule(".terminal-nav") {
        flexColumn()
        alignItems = Align.flexEnd
        alignSelf = Align.flexEnd
        marginTop = 2.rem
        paddingLeft = 1.rem
        width = 100.pct
    }

    rule(".nav-link") {
        backgroundColor = SiteColor.BackgroundMedium.color
        borderTopLeftRadius = LinearDimension("1ch")
        borderBottomLeftRadius = LinearDimension("1ch")
        color = SiteColor.PrimaryText.color
        marginBottom = 1.rem
        padding(vertical = 1.rem, LinearDimension("3ch"))
        textDecoration = TextDecoration.none
        whiteSpace = WhiteSpace.nowrap
    }

    rule(".nav-link:hover") {
        backgroundColor = SiteColor.BackgroundDark.color
    }

    rule(".nav-link.selected") {
        primaryTextGlow()
    }

    tabletOrLarger {
        // TODO
    }
}