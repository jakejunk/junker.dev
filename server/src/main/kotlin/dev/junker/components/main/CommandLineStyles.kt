package dev.junker.components.main

import dev.junker.components.SiteColor
import dev.junker.components.general.*
import kotlinx.css.*

fun CSSBuilder.commandLineStyles() {
    rule(cliContainer.selector) {
        flexColumn()
        margin(1.rem)
        alignItems = Align.center
        width = 100.pct
    }

    rule(cli.selector) {
        padding(vertical = 1.rem, horizontal = 2.ch)
        backgroundColor = SiteColor.BackgroundDarkish.color
        border = light2pxBorder
        borderRadius = cornerRadius
        width = 100.pct
    }

    rule("${cli.selector}::before") {
        primaryTextGlow()
        content = ">".quoted
        paddingRight = 1.ch
    }
}

fun CSSBuilder.commandLineTabletStyles() {
    rule(cliContainer.selector) {
        margin(2.rem)
        marginBottom = 1.rem
    }
}
