package dev.junker.components.main

import dev.junker.components.general.flexColumn
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CssBuilder.mainContentStyles() {
    rule(mainContainer.selector) {
        flexColumn()
        alignItems = Align.center
        alignSelf = Align.center
        flexGrow = 1.0
        maxWidth = 1100.px
        padding = Padding(horizontal = 1.rem)
        paddingBottom = 8.rem
        width = 100.pct
        wordBreak = WordBreak.breakWord
    }

    rule(main.selector) {
        padding = Padding(vertical = 1.rem)
        width = 100.pct
    }

    rule(mainContent.selector) {
        flexColumn()
        fontFamily = "Work Sans, sans-serif"
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
    }
}

fun CssBuilder.mainContentTabletStyles() {
    rule(mainContainer.selector) {
        padding = Padding(horizontal = 2.rem)
    }

    rule(main.selector) {
        padding = Padding(2.rem)
    }
}
