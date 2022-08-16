package dev.junker.components.drawer

import dev.junker.*
import kotlinx.css.*

fun CSSBuilder.renderFooterStyles() {
    rule(drawerFooter.css) {
        backgroundColor = SiteColor.BackgroundMedium.color
        display = Display.flex
        alignItems = Align.baseline
        justifyContent = JustifyContent.spaceBetween
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
    }

    rule(statusIndicator.css) {
        engravedText()
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
        rule(drawerFooter.css) {
//            borderBottomLeftRadius = 1.rem
//            flexDirection = FlexDirection.column
        }

        rule(copyright.css) {
//            marginTop = 2.rem
        }
    }
}

private fun CSSBuilder.engravedText() {
    color = SiteColor.BorderBottom.color
    fontWeight = FontWeight.bold
    property("text-shadow", "0 1px ${SiteColor.BorderTop.color.value}")
}
