package dev.junker.components.quote

import dev.junker.components.SiteColor
import dev.junker.components.general.pixelFont
import kotlinx.css.*

fun CssBuilder.quoteStyles() {
    blockquote {
        color = SiteColor.SubtleText.color
        margin = Margin(vertical = 0.px, horizontal = LinearDimension.auto)
        maxWidth = 620.px
        padding = Padding(vertical = 1.rem, horizontal = 2.ch)
        textAlign = TextAlign.center
        width = LinearDimension.fitContent

        p {
            firstChild {
                position = Position.relative

                before {
                    pixelFont()
                    content = "“".quoted
                    position = Position.absolute
                    left = (-1).ch
                    top = 0.px
                }

                after {
                    pixelFont()
                    content = "”".quoted
                    position = Position.absolute
                    right = (-1).ch
                    top = 0.px
                }
            }
        }
    }
}
