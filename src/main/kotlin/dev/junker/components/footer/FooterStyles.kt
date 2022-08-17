package dev.junker.components.footer

import dev.junker.components.SiteColor
import dev.junker.components.light2pxBorder
import dev.junker.components.property
import dev.junker.components.tabletOrLarger
import kotlinx.css.*

fun CSSBuilder.renderFooterStyles() {
    rule(mainFooter.css) {
        borderTop = light2pxBorder
        display = Display.flex
        justifyContent = JustifyContent.center
        paddingBottom = LinearDimension("8ch")
    }

    rule(footerContent.css) {
        display = Display.flex
        alignItems = Align.baseline
        justifyContent = JustifyContent.spaceBetween
        maxWidth = 1000.px
        width = 100.pct
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
    }

    rule(statusIndicator.css) {
        color = SiteColor.SubtleText.color
        whiteSpace = WhiteSpace.nowrap
    }

    rule("${statusIndicator.css}:before") {
        content = "● ".quoted
        color = SiteColor.SecondaryBright.color
        property("text-shadow", "0 0 2ch ${SiteColor.Secondary.color.value}")
    }

    rule(copyright.css) {
        color = SiteColor.SubtleText.color
        whiteSpace = WhiteSpace.nowrap
    }
}

fun CSSBuilder.renderFooterTabletStyles() {
    tabletOrLarger {
        rule(mainFooter.css) {
            borderTop = "none"
            paddingBottom = 0.px
        }
    }
}
