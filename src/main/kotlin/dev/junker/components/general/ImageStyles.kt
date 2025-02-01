package dev.junker.components.general

import dev.junker.components.SiteColor
import kotlinx.css.*
import kotlinx.css.Float

fun CSSBuilder.imageStyles() {
    rule(imageRight.selector) {
        border = light2pxBorder
        borderRadius = cornerRadiusRounder
        display = Display.block
        height = 200.px
        margin(left = LinearDimension.auto, right = LinearDimension.auto, top = 0.px, bottom = 2.rem)
        padding(8.px)
        position = Position.relative
        width = 200.px
        zIndex = -1

        after {
            pixelFont()
            content = "Jake".quoted
            position = Position.absolute
            whiteSpace = WhiteSpace.nowrap
            top = (-14).px
            right = (-10).px
            property("transform", "rotateZ(15deg);")
        }
    }

    rule("${imageRight.selector} img") {
        borderRadius = cornerRadius
        width = 100.pct
    }
}

fun CSSBuilder.imageTabletStyles() {
    rule(imageRight.selector) {
        float = Float.right
        height = 256.px
        marginLeft = 2.rem
        width = 256.px
    }
}

// ====================================================================================================================

fun CSSBuilder.glowingPixelatedBackgroundImage(url: String) {
    pixelatedBackgroundImage(url, BackgroundRepeat.noRepeat)
    filter = "invert(74%) sepia(47%) saturate(462%) hue-rotate(115deg) brightness(98%) contrast(95%) drop-shadow(0px 0px 1ch ${SiteColor.Primary.color.value})"
}

fun CSSBuilder.pixelatedBackgroundImage(url: String, repeat: BackgroundRepeat) {
    backgroundImage = Image("url($url)")
    backgroundSize = "contain"
    backgroundRepeat = repeat
    property("image-rendering", "pixelated")
}
