package dev.junker.components.main

import dev.junker.components.SiteColor
import dev.junker.components.general.ch
import dev.junker.components.general.cornerRadius
import dev.junker.components.general.light2pxBorder
import dev.junker.components.general.primaryTextGlow
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CSSBuilder.mainContentStyles() {
    rule(mainContainer.selector) {
        alignItems = Align.center
        alignSelf = Align.center
        display = Display.flex
        flexDirection = FlexDirection.column
        flexGrow = 1.0
        justifyContent = JustifyContent.center
        margin(horizontal = 1.rem)
        maxWidth = 1100.px
        paddingBottom = 8.rem
        wordBreak = WordBreak.breakWord
    }

    rule(main.selector) {
        width = 100.pct
        padding(vertical = 1.rem)
    }

    rule(commandLine.selector) {
        backgroundColor = SiteColor.BackgroundDarkish.color
        border = light2pxBorder
        borderRadius = cornerRadius
        margin(1.rem)
        padding(vertical = 1.rem, horizontal = 2.ch)
        width = 100.pct
    }

    rule("${commandLine.selector}:before") {
        content = ">".quoted
        paddingRight = 1.ch
        primaryTextGlow()
    }

    rule(mainContent.selector) {
        display = Display.flex
        flexDirection = FlexDirection.column
        fontFamily = "Work Sans, sans-serif"
        fontWeight = FontWeight.lighter
        lineHeight = 1.75.rem.lh
    }
}

fun CSSBuilder.mainContentTabletStyles() {
    rule(mainContainer.selector) {
        margin(horizontal = 2.rem)
    }

    rule(main.selector) {
        padding(2.rem)
    }
}
