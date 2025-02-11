package dev.junker.components.general

import dev.junker.components.SiteColor
import kotlinx.css.*
import kotlinx.css.properties.LineHeight

fun CssBuilder.codeStyles() {
    code {
        monospaceFont(lineHeight = LineHeight.inherit)
        backgroundColor = SiteColor.BackgroundLight.color
        borderRadius = 3.px
        padding = Padding(2.px, 6.px)
    }

    pre {
        margin = Margin(vertical = 1.rem, horizontal = (-1).rem)
        overflowX = Overflow.auto

        code {
            backgroundColor = SiteColor.BackgroundDarkish.color
            border = light2pxBorder
            borderLeftStyle = BorderStyle.none
            borderRadius = 0.px
            borderRightStyle = BorderStyle.none
            display = Display.block
            minWidth = LinearDimension.minContent
            padding = Padding(1.rem)
        }
    }
}

fun CssBuilder.codeTabletStyles() {
    code {
        monospaceFont(lineHeight = LineHeight.inherit)
        backgroundColor = SiteColor.BackgroundLight.color
        borderRadius = 3.px
        padding = Padding(2.px, 6.px)
    }

    pre {
        margin = Margin(1.rem)

        code {
            borderLeftStyle = BorderStyle.solid
            borderRadius = cornerRadius
            borderRightStyle = BorderStyle.solid
        }
    }
}
