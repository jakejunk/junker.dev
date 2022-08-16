package dev.junker.components.drawer

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.*

fun CSSBuilder.renderHeaderStyles() {
    rule(mainHeader.css) {
        display = Display.flex
        flexGrow = 1.0
        alignItems = Align.center
    }

    rule(siteLogo.css) {
        glowingPixelatedBackgroundImage("/assets/images/16x16_logo_black.png")
        height = 64.px
        width = 64.px
        margin(24.px)
    }

    rule("${siteLogo.css}${error.css}") {
        filter = "invert(60%) sepia(30%) saturate(645%) hue-rotate(318deg) brightness(106%) contrast(90%) drop-shadow(0px 0px 1ch ${SiteColor.Secondary.color.value})"
    }

    rule(headerNav.css) {
        columnGap = ColumnGap("1ch")
        display = Display.flex
        flexWrap = FlexWrap.wrap
        rowGap = RowGap("1ch")
        width = 100.pct
    }

    rule(navLink.css) {
        backgroundColor = SiteColor.BackgroundMedium.color
        borderRadius = LinearDimension("1ch")
        color = SiteColor.PrimaryText.color
        padding(vertical = 1.rem, horizontal = LinearDimension("3ch"))
        textDecoration = TextDecoration.none
        whiteSpace = WhiteSpace.nowrap
    }

    rule("${navLink.css}:hover") {
        backgroundColor = SiteColor.BackgroundDark.color
    }

    rule("${navLink.css}.selected") {
        primaryTextGlow()
    }
}

fun CSSBuilder.renderHeaderTabletStyles() {
    tabletOrLarger {
        rule(mainHeader.css) {
            justifyContent = JustifyContent.spaceBetween
            width = 100.pct
            maxWidth = 1300.px
        }

        rule(headerNav.css) {
            rowGap = RowGap("1rem")
            width = LinearDimension.auto
        }

        rule(navLink.css) {
            padding(horizontal = LinearDimension("2ch"))
        }
    }
}
