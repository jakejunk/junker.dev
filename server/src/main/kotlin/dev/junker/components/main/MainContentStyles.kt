package dev.junker.components.main

import dev.junker.components.general.flexColumn
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CSSBuilder.mainContentStyles() {
    rule(mainContainer.selector) {
        flexColumn()
        padding(horizontal = 1.rem)
        alignItems = Align.center
        alignSelf = Align.center
        flexGrow = 1.0
        maxWidth = 1100.px
        paddingBottom = 8.rem
        width = 100.pct
        wordBreak = WordBreak.breakWord
    }

    rule(main.selector) {
        padding(vertical = 1.rem)
        width = 100.pct
    }

    rule(mainContent.selector) {
        flexColumn()
        fontFamily = "Work Sans, sans-serif"
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
    }
}

fun CSSBuilder.mainContentTabletStyles() {
    rule(mainContainer.selector) {
        padding(horizontal = 2.rem)
    }

    rule(main.selector) {
        padding(2.rem)
    }
}
