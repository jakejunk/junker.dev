package dev.junker

import dev.junker.components.drawer.*
import dev.junker.components.footer.renderFooterStyles
import dev.junker.components.footer.renderFooterTabletStyles
import dev.junker.components.main.renderMainContentStyles
import dev.junker.components.main.renderMainContentTabletStyles
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
        monospaceFont()
        backgroundColor = SiteColor.BackgroundMedium.color
        color = SiteColor.PrimaryText.color
        boxSizing = BoxSizing.borderBox
        height = 100.pct
        overflowX = Overflow.hidden
    }

    body {
        flexColumn()
        height = 100.pct
        margin(top = 0.px, right = 100.pct - 100.vw, bottom = 0.px, left = 0.px)
    }

    renderDrawerStyles()
    renderHeaderStyles()
    renderFooterStyles()
    renderMainContentStyles()

    // Make sure to render all query-dependent styles last.
    // CSS DSL tries to get clever and combine things, changing around order.
    renderDrawerTabletStyles()
    renderHeaderTabletStyles()
    renderFooterTabletStyles()
    renderMainContentTabletStyles()
}

fun CSSBuilder.monospaceFont() {
    fontFamily = "Source Code Pro, Courier New, Courier, monospace"
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
    property("text-shadow", "0 0 8px ${SiteColor.Primary.color.value}")
}

fun CSSBuilder.property(name: String, value: String) = put(name, value)
