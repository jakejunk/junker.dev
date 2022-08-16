package dev.junker.components.drawer

import dev.junker.*
import kotlinx.css.*

fun CSSBuilder.renderFooterStyles() {
    rule(mainFooter.css) {
        backgroundColor = SiteColor.ButtonColor.color
        display = Display.flex
        justifyContent = JustifyContent.center
        paddingBottom = LinearDimension("8ch")
    }

    rule(footerContent.css) {
        display = Display.flex
        alignItems = Align.baseline
        justifyContent = JustifyContent.spaceBetween
        width = 100.pct
        maxWidth = 900.px
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
            backgroundColor = Color.unset
            paddingBottom = 0.px
        }
    }
}
