package dev.junker

import dev.junker.components.*
import dev.junker.components.drawer.*
import kotlinx.css.*

enum class SiteColor(value: String) {
    BackgroundDark("#06070A"),
    BackgroundMedium("#1D2434"),
    BackgroundLight("#2E3952"),
    ButtonColor("#161B27"),
    Primary("#1ABC9C"),
    PrimaryBright("#54E8CA"),
    Secondary("#ED6A5A"),
    SecondaryBright("#F29488"),
    PrimaryText("#F0E7D8"),
    SubtleText("#798BB4");

    val color = Color(value)
}

fun CSSBuilder.renderStyles() {
    rule("*, :after, :before") {
        boxSizing = BoxSizing.inherit
    }

    html {
        fontFamily = "Source Code Pro, Courier New, Courier, monospace"
        backgroundColor = SiteColor.BackgroundMedium.color
        color = SiteColor.PrimaryText.color
        boxSizing = BoxSizing.borderBox
        height = 100.pct
    }

    body {
        flexColumn()
        height = 100.pct
        margin(0.px)
    }

    renderDrawerStyles()
    renderHeaderStyles()
    renderFooterStyles()
    renderTerminalMainStyles()

    // Make sure to render all query-dependent styles last.
    // CSS DSL tries to get clever and combine things, changing around order.
    renderDrawerTabletStyles()
    renderHeaderTabletStyles()
    renderFooterTabletStyles()
    renderTerminalMainTabletStyles()
}

fun CSSBuilder.tabletOrLarger(block: RuleSet) = media("(min-width: 768px)", block)

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
