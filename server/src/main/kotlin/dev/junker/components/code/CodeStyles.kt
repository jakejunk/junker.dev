package dev.junker.components.code

import dev.junker.*
import dev.junker.components.SiteColor
import dev.junker.components.general.*
import kotlinx.css.*
import kotlinx.css.properties.*

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
            frostedGlass(SiteColor.BackgroundLight.color, 0.0)
            border = light2pxBorder()
            borderLeftStyle = BorderStyle.none
            borderRadius = 0.px
            borderRightStyle = BorderStyle.none
            display = Display.block
            fontSize = 0.85.rem
            fontWeight = FontWeight.normal
            lineHeight = 1.25.rem.lh
            minWidth = LinearDimension.minContent
            padding = Padding(vertical = 1.rem, horizontal = 2.ch)
        }
    }

    annotation.selector {
        color = hex(0xBBB529)
    }

    keyword.selector {
        color = hex(0xFFAB66)
    }

    string.selector {
        color = hex(0x8AD161)
    }

    literal.selector {
        color = hex(0x66BDFF)
    }

    comment.selector {
        color = hex(0xA1A1A1)
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
