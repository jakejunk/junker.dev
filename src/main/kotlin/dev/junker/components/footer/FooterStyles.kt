package dev.junker.components.footer

import dev.junker.components.*
import dev.junker.components.drawer.drawerButtonHeight
import kotlinx.css.*
import kotlinx.css.properties.BoxShadow

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
        padding(vertical = 1.rem, horizontal = 2.ch)
    }

    rule(statusIndicator.selector) {
        alignItems = Align.baseline
        color = SiteColor.SubtleText.color
        display = Display.inlineFlex
        whiteSpace = WhiteSpace.nowrap
    }

    rule("${statusIndicator.selector}::before") {
        backgroundColor = SiteColor.SecondaryBright.color
        borderRadius = 1.em
        boxShadow += BoxShadow(false, 0.px, 0.px, 2.ch, 0.px, SiteColor.Secondary.color)
        content = "".quoted
        display = Display.inlineBlock
        height = 1.ch
        marginRight = 1.ch
        width = 1.ch
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
