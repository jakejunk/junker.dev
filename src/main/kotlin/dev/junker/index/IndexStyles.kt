package dev.junker.index

import dev.junker.SiteColor
import kotlinx.css.*

fun CSSBuilder.property(name: String, value: String) = put(name, value)

fun CSSBuilder.primaryTextGlow() {
    color = SiteColor.PrimaryBright.color
    property("text-shadow", "0 0 2ch ${SiteColor.Primary.color.value}")
}

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
        backgroundColor = SiteColor.BackgroundLight.color
        borderRadius = 12.px
        display = Display.flex
        height = 100.pct
        margin(top = 48.px, bottom = 48.px)
        maxWidth = 1400.px
        overflow = Overflow.hidden
        width = 100.pct
    }

    rule(".terminal > header") {
        alignItems = Align.center
        borderRight = "solid 4px ${SiteColor.BackgroundMedium.color.value}"
        display = Display.flex
        flexDirection = FlexDirection.column
        height = 100.pct
        padding(vertical = 2.rem, horizontal = LinearDimension("4ch"))
    }

    rule(".site-logo") {
        alignItems = Align.center
        display = Display.flex
        flexDirection = FlexDirection.column
        primaryTextGlow()
    }

    rule(".site-logo-image") {
        backgroundImage = Image("url(/assets/images/16x16_logo_black.png)")
        backgroundSize = "contain"
        backgroundRepeat = BackgroundRepeat.noRepeat
        filter = "invert(74%) sepia(47%) saturate(462%) hue-rotate(115deg) brightness(98%) contrast(95%) drop-shadow(0px 0px 1ch ${SiteColor.Primary.color.value})"
        height = LinearDimension("8ch")
        property("image-rendering", "pixelated")
        marginBottom = 1.rem;
        width = LinearDimension("8ch")
    }

    rule(".terminal-main") {
        flexGrow = 1.0
        padding(vertical = 2.rem, horizontal = LinearDimension("4ch"))
    }

    rule(".terminal-prompt") {
        backgroundColor = SiteColor.BackgroundDark.color
        borderRadius = LinearDimension("1ch")
        width = 100.pct
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
        marginBottom = LinearDimension("4ch")
    }

    rule(".terminal-prompt:before") {
        content = ">".quoted
        paddingRight = LinearDimension("1ch")
        primaryTextGlow()
    }
}
