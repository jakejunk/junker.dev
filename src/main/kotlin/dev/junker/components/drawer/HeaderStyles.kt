package dev.junker.components.drawer

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.*

fun CSSBuilder.renderHeaderStyles() {
    rule(drawerHeader.css) {
        beveledTerminalSurface()
        flexColumn()
        flexGrow = 1.0
        alignItems = Align.center
    }

    rule(siteLogo.css) {
        glowingPixelatedBackgroundImage("/assets/images/16x16_logo_black.png")
        height = LinearDimension("8ch")
        width = LinearDimension("8ch")
        margin(2.rem, 2.rem, 2.rem, 2.rem)
    }

    rule("${siteLogo.css}${error.css}") {
        filter = "invert(60%) sepia(30%) saturate(645%) hue-rotate(318deg) brightness(106%) contrast(90%) drop-shadow(0px 0px 1ch ${SiteColor.Secondary.color.value})"
    }

    rule(headerNav.css) {
        alignSelf = Align.flexEnd
        columnGap = ColumnGap("1ch")
        display = Display.flex
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.center
        marginBottom = 1.rem
        paddingLeft = 1.rem
        paddingRight = 1.rem
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
        rule(drawerHeader.css) {
            borderTopLeftRadius = 1.rem
        }

        rule(headerNav.css) {
            alignItems = Align.stretch
            flexDirection = FlexDirection.column
            margin(top = 1.rem, bottom = 2.rem)
            paddingRight = 0.px
            rowGap = RowGap("1rem")
        }

        rule(navLink.css) {
            borderTopRightRadius = 0.px
            borderBottomRightRadius = 0.px
            padding(horizontal = LinearDimension("2ch"))
        }
    }
}
