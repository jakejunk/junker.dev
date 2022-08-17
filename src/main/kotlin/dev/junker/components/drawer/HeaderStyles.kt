package dev.junker.components.drawer

import dev.junker.components.SiteColor
import dev.junker.components.glowingPixelatedBackgroundImage
import dev.junker.components.primaryTextGlow
import dev.junker.components.tabletOrLarger
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration

fun CSSBuilder.renderHeaderStyles() {
    rule(mainHeader.css) {
        display = Display.flex
        alignItems = Align.center
        flexDirection = FlexDirection.column
        flexGrow = 1.0
    }

    rule(siteLogo.css) {
        glowingPixelatedBackgroundImage("/assets/images/16x16_logo_black.png")
        margin(24.px)
        height = 64.px
        width = 64.px
    }

    rule("${siteLogo.css}${error.css}") {
        filter = "invert(60%) sepia(30%) saturate(645%) hue-rotate(318deg) brightness(106%) contrast(90%) drop-shadow(0px 0px 1ch ${SiteColor.Secondary.color.value})"
    }

    rule(headerNav.css) {
        display = Display.flex
        justifyContent = JustifyContent.center
        flexWrap = FlexWrap.wrap
        columnGap = ColumnGap("1ch")
        rowGap = RowGap("1ch")
        width = 100.pct
        padding(left = 24.px, right = 24.px, bottom = 24.px)
    }

    rule(navLink.css) {
        backgroundColor = SiteColor.ButtonColor.color
        color = SiteColor.PrimaryText.color
        borderRadius = LinearDimension("1ch")
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
            flexDirection = FlexDirection.row
            justifyContent = JustifyContent.spaceBetween
            maxWidth = 1300.px
            width = 100.pct
        }

        rule(headerNav.css) {
            rowGap = RowGap("1rem")
            width = LinearDimension.auto
            padding(24.px)
        }

        rule(navLink.css) {
            padding(horizontal = LinearDimension("2ch"))
        }
    }
}
