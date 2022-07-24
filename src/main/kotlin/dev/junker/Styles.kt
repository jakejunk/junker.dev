package dev.junker

import dev.junker.components.*
import kotlinx.css.*

enum class SiteColor(value: String) {
    BackgroundDark("#06070A"),
    BackgroundMedium("#0B0E13"),
    BackgroundLight("#11151C"),

    BorderTop("#252F41"),
    BorderRight("#0B0E13"),
    BorderBottom("#07090D"),
    BorderLeft("#181F2A"),

    Primary("#1ABC9C"),
    PrimaryBright("#54E8CA"),

    Secondary("#ED6A5A"),
    SecondaryBright("#F29488"),
    
    PrimaryText("#F0E7D8");

    val color = Color(value)
}

fun CSSBuilder.renderStyles() {
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
        maxWidth = 1500.px
        overflow = Overflow.hidden
        width = 100.pct
    }

    renderTerminalDrawerStyles()
    renderTerminalHeaderStyles()
    renderTerminalMainStyles()

    // Make sure to render all query-dependent styles last.
    // CSS DSL tries to get clever and combine things, changing around order.
    renderTerminalDrawerTabletStyles()
    renderTerminalHeaderTabletStyles()
    renderTerminalMainTabletStyles()

    tabletOrLarger {
        rule(".terminal") {
            margin(top = 48.px, bottom = 48.px)
        }
    }
}

fun CSSBuilder.tabletOrLarger(block: RuleSet) = media("(min-width: 768px)", block)

fun CSSBuilder.beveledTerminalSurface() {
    backgroundColor = SiteColor.BackgroundLight.color
    borderTopColor = SiteColor.BorderTop.color
    borderRightColor = SiteColor.BorderRight.color
    borderBottomColor = SiteColor.BorderBottom.color
    borderLeftColor = SiteColor.BorderLeft.color
    borderStyle = BorderStyle.solid
    borderWidth = 4.px
}

fun CSSBuilder.glowingPixelatedBackgroundImage(url: String) {
    backgroundImage = Image("url($url)")
    backgroundSize = "contain"
    backgroundRepeat = BackgroundRepeat.noRepeat
    filter = "invert(74%) sepia(47%) saturate(462%) hue-rotate(115deg) brightness(98%) contrast(95%) drop-shadow(0px 0px 1ch ${SiteColor.Primary.color.value})"
    property("image-rendering", "pixelated")
}

fun CSSBuilder.flexColumn() {
    display = Display.flex
    flexDirection = FlexDirection.column
}

fun CSSBuilder.primaryTextGlow() {
    color = SiteColor.PrimaryBright.color
    property("text-shadow", "0 0 2ch ${SiteColor.Primary.color.value}")
}

fun CSSBuilder.property(name: String, value: String) = put(name, value)
