package dev.junker.components.background

import dev.junker.components.general.pixelatedBackgroundImage
import kotlinx.css.*

fun CSSBuilder.backgroundStyles() {
    rule(mainBackground.selector) {
        pixelatedBackgroundImage("assets/images/256x128_bg.png", BackgroundRepeat.repeatX)
        backgroundPosition = "center bottom"
        bottom = 0.px
        display = Display.block
        position = Position.fixed
        height = 512.px
        minWidth = 1024.px
        width = 100.pct
        opacity = 0.1
        zIndex = -1
    }
}
