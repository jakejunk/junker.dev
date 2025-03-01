package dev.junker.components.footer

import dev.junker.components.SiteColor
import dev.junker.components.drawer.drawerButtonHeight
import dev.junker.components.general.light2pxBorder
import kotlinx.css.*
import kotlinx.css.properties.BoxShadow

fun CssBuilder.footerStyles() {
    mainFooter.selector {
        borderTop = light2pxBorder()
        display = Display.flex
        justifyContent = JustifyContent.center
        paddingBottom = 3.rem + drawerButtonHeight
    }

    footerContent.selector {
        display = Display.flex
        alignItems = Align.baseline
        justifyContent = JustifyContent.spaceBetween
        maxWidth = 1000.px
        width = 100.pct
        padding = Padding(vertical = 1.rem, horizontal = 2.ch)
    }

    statusIndicator.selector {
        alignItems = Align.baseline
        color = SiteColor.SubtleText.color
        display = Display.inlineFlex
        whiteSpace = WhiteSpace.nowrap
    }

    "${statusIndicator.selector}::before" {
        backgroundColor = SiteColor.SecondaryBright.color
        borderRadius = 1.em
        boxShadow += BoxShadow(SiteColor.Secondary.color, 0.px, 0.px, 2.ch, 0.px)
        content = "".quoted
        display = Display.inlineBlock
        height = 1.ch
        marginRight = 1.ch
        width = 1.ch
    }

    "${statusIndicator.selector}::after" {
        content = "POWER".quoted
    }

    copyright.selector {
        color = SiteColor.SubtleText.color
        whiteSpace = WhiteSpace.nowrap
    }
}

fun CssBuilder.footerTabletStyles() {
    mainFooter.selector {
        borderTopStyle = BorderStyle.none
        paddingBottom = 0.px
    }
}
