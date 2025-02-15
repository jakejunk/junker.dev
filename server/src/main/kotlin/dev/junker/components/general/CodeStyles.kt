package dev.junker.components.general

import dev.junker.components.SiteColor
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.lh

fun CssBuilder.codeStyles() {
    code {
        monospaceFont(lineHeight = LineHeight.inherit)
        backgroundColor = SiteColor.BackgroundLight.color
        borderRadius = 3.px
        padding = Padding(2.px, 6.px)
    }

    pre {
        monospaceFont()
        property("scrollbar-color", "${SiteColor.BackgroundLight.color} ${SiteColor.BackgroundDarkish.color}")
        margin = Margin(
            top = 1.rem,
            right = (-1).rem,
            bottom = 2.rem,
            left = (-1).rem
        )
        overflowX = Overflow.auto

        code {
            frostedGlass(SiteColor.BackgroundLight, 0.0)
            border = light2pxBorder
            borderLeftStyle = BorderStyle.none
            borderRadius = 0.px
            borderRightStyle = BorderStyle.none
            display = Display.block
            fontSize = 0.85.rem
            lineHeight = 1.25.rem.lh
            minWidth = LinearDimension.minContent
            padding = Padding(vertical = 1.rem, horizontal = 2.ch)
        }
    }
}

fun CssBuilder.codeTabletStyles() {
    pre {
        margin = Margin(
            top = 1.rem,
            right = 0.px,
            bottom = 2.rem,
            left = 0.px
        )

        code {
            borderLeftStyle = BorderStyle.solid
            borderRadius = cornerRadius
            borderRightStyle = BorderStyle.solid
            padding = Padding(vertical = 1.5.rem, horizontal = 3.ch)
        }
    }
}
