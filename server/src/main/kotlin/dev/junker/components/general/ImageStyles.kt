package dev.junker.components.general

import dev.junker.components.SiteColor
import kotlinx.css.*
import kotlinx.css.Float
import kotlinx.css.properties.deg
import kotlinx.css.properties.rotateZ
import kotlinx.css.properties.transform

fun CssBuilder.imageStyles() {
    imageBanner.selector {
        border = light2pxBorder()
        borderLeftStyle = BorderStyle.none
        borderRightStyle = BorderStyle.none
        margin = Margin(
            top = 0.px,
            right = (-1).rem,
            bottom = 2.rem,
            left = (-1).rem
        )
        display = Display.block
        paddingTop = 8.px
        paddingBottom = 8.px
        position = Position.relative
        width = LinearDimension.fitContent

        img {
            display = Display.block
            maxWidth = 100.pct
        }
    }

    imageRight.selector {
        border = light2pxBorder()
        borderRadius = cornerRadiusRounder
        display = Display.block
        height = 200.px
        margin = Margin(left = LinearDimension.auto, right = LinearDimension.auto, top = 0.px, bottom = 2.rem)
        padding = Padding(8.px)
        position = Position.relative
        width = 200.px
        zIndex = -1

        "&[data-exclamation]" {
            after {
                pixelFont()
                transform { rotateZ(15.deg) }
                property("content", "attr(data-exclamation)")
                position = Position.absolute
                whiteSpace = WhiteSpace.nowrap
                top = (-14).px
                right = (-10).px
            }
        }
    }

    "${imageRight.selector} img" {
        borderRadius = cornerRadius
        width = 100.pct
    }
}

fun CssBuilder.imageTabletStyles() {
    imageBanner.selector {
        borderLeftStyle = BorderStyle.solid
        borderRadius = cornerRadiusRounder
        borderRightStyle = BorderStyle.solid
        display = Display.block
        margin = Margin(
            top = 0.px,
            right = LinearDimension.auto,
            bottom = 3.rem,
            left = LinearDimension.auto
        )
        paddingLeft = 8.px
        paddingRight = 8.px

        img {
            borderRadius = cornerRadius
        }
    }

    imageRight.selector {
        float = Float.right
        height = 256.px
        marginLeft = 2.rem
        width = 256.px
    }
}

// ====================================================================================================================

fun CssBuilder.glowingPixelatedBackgroundImage(url: String) {
    pixelatedBackgroundImage(url)
    filter = "invert(74%) sepia(47%) saturate(462%) hue-rotate(115deg) brightness(98%) contrast(95%) drop-shadow(0px 0px 1ch ${SiteColor.Primary.color.value})"
}

fun CssBuilder.pixelatedBackgroundImage(
    url: String,
    repeat: BackgroundRepeat = BackgroundRepeat.noRepeat,
    size: LinearDimension? = null
) {
    property("image-rendering", "pixelated")
    backgroundImage = Image("url($url)")
    backgroundSize = "contain"
    backgroundRepeat = repeat
    size?.also { backgroundSize = it.toString() }
}
