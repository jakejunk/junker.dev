package dev.junker.components.main

import dev.junker.components.SiteColor
import dev.junker.components.general.*
import kotlinx.css.*

fun CssBuilder.commandLineStyles() {
    rule(cliContainer.selector) {
        flexColumn()
        alignItems = Align.center
        margin = Margin(1.rem)
        width = 100.pct
    }

    rule(cli.selector) {
        backgroundColor = SiteColor.BackgroundDarkish.color
        border = light2pxBorder
        borderRadius = cornerRadius
        padding = Padding(vertical = 1.rem, horizontal = 2.ch)
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
