package dev.junker.components.footer

import dev.junker.components.SiteColor
import dev.junker.components.drawer.drawerButtonHeight
import dev.junker.components.light2pxBorder
import dev.junker.components.property
import dev.junker.components.tabletOrLarger
import kotlinx.css.*

fun CSSBuilder.renderFooterStyles() {
    rule(mainFooter.selector) {
        borderTop = light2pxBorder
        display = Display.flex
        justifyContent = JustifyContent.center
        paddingBottom = 3.rem + drawerButtonHeight
    }

    rule(footerContent.selector) {
        display = Display.flex
        alignItems = Align.baseline
        justifyContent = JustifyContent.spaceBetween
        maxWidth = 1000.px
        width = 100.pct
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
    }

    rule(statusIndicator.selector) {
        color = SiteColor.SubtleText.color
        whiteSpace = WhiteSpace.nowrap
    }

    rule("${statusIndicator.selector}:before") {
        content = "● ".quoted
        color = SiteColor.SecondaryBright.color
        property("text-shadow", "0 0 2ch ${SiteColor.Secondary.color.value}")
    }

    rule(copyright.selector) {
        color = SiteColor.SubtleText.color
        whiteSpace = WhiteSpace.nowrap
    }
}

fun CSSBuilder.renderFooterTabletStyles() {
    tabletOrLarger {
        rule(mainFooter.selector) {
            borderTop = "none"
            paddingBottom = 0.px
        }
    }
}
