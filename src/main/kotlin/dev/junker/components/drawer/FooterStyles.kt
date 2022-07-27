package dev.junker.components.drawer

import dev.junker.*
import kotlinx.css.*

fun CSSBuilder.renderFooterStyles() {
    rule(".drawer-footer") {
        beveledTerminalSurface()
        display = Display.flex
        justifyContent = JustifyContent.spaceBetween
        padding(vertical = 1.rem, horizontal = LinearDimension("2ch"))
    }

    rule(".totally-real-status-indicator") {
        engravedText()
    }

    rule(".totally-real-status-indicator:before") {
        content = "● ".quoted
        color = SiteColor.SecondaryBright.color
        property("text-shadow", "0 0 2ch ${SiteColor.Secondary.color.value}")
    }

    rule(".copyright") {
        color = SiteColor.SubtleText.color
    }
}

fun CSSBuilder.renderFooterTabletStyles() {
    tabletOrLarger {
        rule(".drawer-footer") {
            borderBottomLeftRadius = 1.rem
            flexDirection = FlexDirection.column
        }

        rule(".copyright") {
            marginTop = 2.rem
        }
    }
}

private fun CSSBuilder.engravedText() {
    color = SiteColor.BorderBottom.color
    fontWeight = FontWeight.bold
    property("text-shadow", "0 1px ${SiteColor.BorderTop.color.value}")
}
