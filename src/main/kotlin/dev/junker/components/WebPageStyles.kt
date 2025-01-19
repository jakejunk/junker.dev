package dev.junker.components

import dev.junker.components.background.renderBackgroundStyles
import dev.junker.components.drawer.*
import dev.junker.components.footer.renderFooterStyles
import dev.junker.components.footer.renderFooterTabletStyles
import dev.junker.components.main.renderMainContentStyles
import dev.junker.components.main.renderMainContentTabletStyles
import dev.junker.components.page.renderPageStyles
import dev.junker.components.page.renderPageTabletStyles
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.translateY

val light2pxBorder = "solid 2px ${SiteColor.BackgroundLight.color.value}"
val cornerRadius = 9.px
val cornerRadiusRounder = 16.px

fun CSSBuilder.renderWebPageStyles() {
    rule("*, ::after, ::before") {
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

    rule(skipLink.selector) {
        position = Position.absolute
        zIndex = 1
        alignSelf = Align.center
        transform.translateY((-100).pct)
        backgroundColor = SiteColor.ButtonColor.color
        borderBottomLeftRadius = cornerRadius
        borderBottomRightRadius = cornerRadius
        secondaryTextGlow()
        textDecoration = TextDecoration.none
        padding(1.rem)
    }

    rule("${skipLink.selector}::before") {
        content = "↯ ".quoted
        fontWeight = FontWeight.bold
    }

    rule("${skipLink.selector}:focus") {
        transform.translateY(0.px)
    }

    renderBackgroundStyles()
    renderDrawerStyles()
    renderHeaderStyles()
    renderFooterStyles()
    renderMainContentStyles()
    renderPageStyles()

    // Make sure to render all query-dependent styles last.
    // CSS DSL tries to get clever and combine things, changing around order.
    renderDrawerTabletStyles()
    renderHeaderTabletStyles()
    renderFooterTabletStyles()
    renderMainContentTabletStyles()
    renderPageTabletStyles()
}

fun CSSBuilder.tabletOrLarger(block: RuleSet) = media("(min-width: 768px)", block)

fun CSSBuilder.monospaceFont() {
    fontFamily = "Source Code Pro, Courier New, Courier, monospace"
}

fun CSSBuilder.glowingPixelatedBackgroundImage(url: String) {
    pixelatedBackgroundImage(url, BackgroundRepeat.noRepeat)
    filter = "invert(74%) sepia(47%) saturate(462%) hue-rotate(115deg) brightness(98%) contrast(95%) drop-shadow(0px 0px 1ch ${SiteColor.Primary.color.value})"
}

fun CSSBuilder.pixelatedBackgroundImage(url: String, repeat: BackgroundRepeat) {
    backgroundImage = Image("url($url)")
    backgroundSize = "contain"
    backgroundRepeat = repeat
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

fun CSSBuilder.secondaryTextGlow() {
    color = SiteColor.SecondaryBright.color
    property("text-shadow", "0 0 8px ${SiteColor.Secondary.color.value}")
}

fun CSSBuilder.property(name: String, value: String) = put(name, value)

val Number.ch get() = LinearDimension("${this}ch")
