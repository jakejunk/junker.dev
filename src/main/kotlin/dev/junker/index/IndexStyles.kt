package dev.junker.index

import dev.junker.SiteColor
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration

fun CSSBuilder.property(name: String, value: String) = put(name, value)

fun CSSBuilder.flexColumn() {
    display = Display.flex
    flexDirection = FlexDirection.column
}

fun CSSBuilder.primaryTextGlow() {
    color = SiteColor.PrimaryBright.color
    property("text-shadow", "0 0 2ch ${SiteColor.Primary.color.value}")
}

fun CSSBuilder.beveledTerminalSurface() {
    backgroundColor = SiteColor.BackgroundLight.color
    borderTopColor = SiteColor.BorderTop.color
    borderRightColor = SiteColor.BorderRight.color
    borderBottomColor = SiteColor.BorderBottom.color
    borderLeftColor = SiteColor.BorderLeft.color
    borderStyle = BorderStyle.solid
    borderWidth = 4.px
}

fun CSSBuilder.renderIndexStyles() {
    rule("*, :after, :before") {
        boxSizing = BoxSizing.inherit
    }

    html {
        boxSizing = BoxSizing.borderBox
        color = SiteColor.PrimaryText.color
        fontFamily = "Lucida Console, monospace"
        height = 100.pct
    }

    body {
        backgroundColor = SiteColor.BackgroundDark.color
        flexColumn()
        height = 100.pct
        margin(0.px)
    }

    rule(".terminal") {
        alignSelf = Align.center
        display = Display.flex
        height = 100.pct
        margin(top = 48.px, bottom = 48.px)
        maxWidth = 1400.px
        overflow = Overflow.hidden
        width = 100.pct
    }

    rule(".terminal-header") {
        alignItems = Align.center
        beveledTerminalSurface()
        borderTopLeftRadius = LinearDimension("1ch")
        borderBottomLeftRadius = LinearDimension("1ch")
        flexColumn()
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
        margin(2.rem, 2.rem, 4.rem, 2.rem)
    }

    rule(".terminal-nav") {
        alignItems = Align.flexEnd
        alignSelf = Align.flexEnd
        flexColumn()
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
    }

    rule(".nav-link:hover") {
        backgroundColor = SiteColor.BackgroundDark.color
    }

    rule(".nav-link.selected") {
        primaryTextGlow()
    }

    rule(".terminal-main") {
        beveledTerminalSurface()
        borderTopRightRadius = LinearDimension("1ch")
        borderBottomRightRadius = LinearDimension("1ch")
        flexGrow = 1.0
    }

    rule(".terminal-prompt") {
        backgroundColor = SiteColor.BackgroundDark.color
        borderRadius = LinearDimension("1ch")
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
        margin(1.rem)
    }

    rule(".terminal-output") {
        padding(vertical = 2.rem, horizontal = 1.rem + LinearDimension("2ch"))
    }

    rule(".terminal-prompt:before") {
        content = ">".quoted
        paddingRight = LinearDimension("1ch")
        primaryTextGlow()
    }
}
