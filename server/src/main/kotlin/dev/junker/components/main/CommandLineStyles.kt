package dev.junker.components.main

import dev.junker.components.SiteColor
import dev.junker.components.general.*
import kotlinx.css.*

fun CssBuilder.commandLineStyles() {
    rule(cliContainer.selector) {
        flexColumn()
        alignItems = Align.center
        // TODO: Frosted glass blurs everything
        backgroundColor = SiteColor.BackgroundDark.color.changeAlpha(0.33)
        border = light2pxBorder
        borderRadius = cornerRadius
        margin = Margin(1.rem)
        overflowX = Overflow.auto
        width = 100.pct
    }

    rule(cli.selector) {
        padding = Padding(vertical = 1.rem, horizontal = 2.ch)
        whiteSpace = WhiteSpace.nowrap
        width = 100.pct
    }

    rule("${cli.selector}::before") {
        primaryTextGlow()
        content = ">".quoted
        paddingRight = 1.ch
    }
}

fun CssBuilder.commandLineTabletStyles() {
    rule(cliContainer.selector) {
        margin = Margin(2.rem)
        marginBottom = 1.rem
    }
}
