package dev.junker.components.grid

import dev.junker.components.SiteColor
import dev.junker.components.general.*
import kotlinx.css.*
import kotlinx.css.properties.*

fun CssBuilder.gridStyles() {
    notesGrid.selector {
        flexRow(rowGap = 1.ch)
        alignItems = Align.flexStart
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.center
    }

    gridCard.selector {
        frostedGlass(SiteColor.BackgroundLight.color, 0.0)
        backgroundImage = radialGradient {
            circle()
            at(RelativePosition.offset(85.pct, 5.pct))
            colorStop(SiteColor.PrimaryText.color.withAlpha(0.05))
            colorStop(Color.transparent)
        }
        border = light2pxBorder()
        borderRadius = cornerRadius
        color = SiteColor.PrimaryText.color
        display = Display.block
        padding = Padding(vertical = 1.rem, horizontal = 2.ch)
        textDecoration = TextDecoration.none
        textShadow = TextShadows.none
        transform { scale(0.95) }
        transition += Transition(
            property = "transform",
            duration = 0.15.s,
            timing = Timing.easeInOut
        )

        "&:hover, &:focus" {
            boxShadow += BoxShadow(
                color = Color.black.withAlpha(0.33),
                offsetY = 4.px,
                blurRadius = 5.px
            )
            transform { scale(1) }
            transition = Transitions.none
        }

        header {
            h2 {
                monospaceFont(fontSize = 1.25.rem, lineHeight = 1.75.rem.lh)
                primaryTextGlow(1.ch)
                margin = Margin(vertical = 0.5.rem)
                textAlign = TextAlign.left
            }

            time {
                color = SiteColor.SubtleText.color
                fontSize = 0.85.rem
            }
        }

        p {
            color = SiteColor.SubtleText.color
            fontStyle = FontStyle.italic
        }
    }
}

fun CssBuilder.gridTabletStyles() {
    gridCard.selector {
        maxWidth = 256.px
    }
}

// ====================================================================================================================
