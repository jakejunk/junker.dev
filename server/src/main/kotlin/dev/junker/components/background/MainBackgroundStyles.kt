package dev.junker.components.background

import dev.junker.components.general.pixelatedBackgroundImage
import kotlinx.css.*

fun CssBuilder.backgroundStyles() {
    mainBackground.selector {
        pixelatedBackgroundImage("assets/images/256x128_bg.png", BackgroundRepeat.repeatX)
        backgroundPosition = RelativePosition.centerBottom
        bottom = 0.px
        display = Display.block
        height = 512.px
        minWidth = 1024.px
        opacity = 0.1
        position = Position.fixed
        width = 100.pct
        zIndex = -1
    }
}
