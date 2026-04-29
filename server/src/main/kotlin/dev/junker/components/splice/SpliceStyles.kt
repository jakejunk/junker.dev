package dev.junker.components.splice

import dev.junker.*
import dev.junker.components.SiteColor
import dev.junker.components.general.*
import kotlinx.css.*
import kotlinx.css.properties.*

private const val gridWidth = 576
private const val controlWidth = 256
private const val gridControlsGapWidth = 32

fun CssBuilder.spliceStyles() {
    spliceGridStyles()
    spliceControlStyles()
}

private fun CssBuilder.spliceGridStyles() {
    keyframes("fadeIn") {
        0 {
            opacity = 0
        }
        100 {
            opacity = 1
        }
    }

    splicePlaceholder.selector {
        secondaryTextGlow()
        animation += Animation(
            name = "fadeIn",
            duration = 0.75.s,
            timing = Timing.easeInOut,
            fillMode = FillMode.forwards
        )
        height = gridWidth.px
        margin = Margin(vertical = 2.rem, horizontal = LinearDimension.auto)
        maxWidth = gridWidth.px
        opacity = 0
        textAlign = TextAlign.center

        transform {
            opacity = 1
        }
    }

    splice.selector {
        wrappingRow(
            rowGap = 16.px,
            columnGap = gridControlsGapWidth.px
        )
        containerType = ContainerType.inlineSize
        justifyContent = JustifyContent.center
        margin = Margin(2.rem, (-1).rem + 5.px)
    }

    spliceGrid.selector {
        frostedGlass(Color.transparent)
        monospaceFont()
        aspectRatio = AspectRatio(1, 1)
        border = lightBorder(3.px)
        flexBasis = 100.pct.basis
        flexGrow = 1
        maxWidth = gridWidth.px
    }

    spliceCell.selector {
        border = lightBorder(1.px)
        containerType = ContainerType.size
        position = Position.relative

        before {
            property("content", "attr(data-value)")
            fontSize = clamp(5.px, NumericLinearDimension(90, "cqi"), 2.5.rem)
            height = 100.pct
            lineHeight = NumericLinearDimension(100, "cqh").lh
            position = Position.absolute
            textAlign = TextAlign.center
            width = 100.pct
        }

        "&${spliceSelected.selector}" {
            backgroundColor = SiteColor.BackgroundLight.color
            fontWeight = FontWeight.normal
        }
    }

    // User has mouse
    media("(pointer: fine)") {
        spliceCell.selector {
            hover {
                backgroundColor = SiteColor.BackgroundLight.color.withAlpha(0.5)
            }
        }
    }
}

private fun CssBuilder.spliceControlStyles() {
    spliceControls.selector {
        flexColumn(columnGap = 16.px)
        height = LinearDimension.fitContent
        width = controlWidth.px
    }

    spliceActions.selector {
        flexRow(rowGap = 1.ch)

        label {
            flexColumn()
            flexBasis = FlexBasis("0")
            flexGrow = 1
            textAlign = TextAlign.center

            spliceAction.selector {
                property("-webkit-tap-highlight-color", "transparent")
                appearance = Appearance.none
                backgroundColor = Color.transparent
                border = Border.none
                borderRadius = cornerRadius
                color = Color.unset
                margin = Margin(0.px)
                padding = Padding(8.px)
                textAlign = TextAlign.center

                before {
                    pixelatedBackgroundImage("assets/images/16x16_sudoku_icons.png", size = 64.px)
                    display = Display.inlineBlock
                    height = 32.px
                    width = 32.px
                }

                hover {
                    cursor = Cursor.pointer
                }

                active {
                    backgroundColor = SiteColor.BackgroundLight.color
                }

                "&${spliceActionMark.selector}" {
                    before {
                        transition += Transition(
                            property = "transform",
                            duration = 0.33.s,
                            timing = cubicBezier(0.33, 2.5, 0.66, 0.25)
                        )
                    }

                    checked {
                        backgroundColor = SiteColor.BackgroundLight.color
                        before {
                            transform {
                                rotateZ((-45).deg)
                            }
                        }
                    }
                }

                "&${spliceActionErase.selector}" {
                    before {
                        backgroundPosition = RelativePosition("-32px 0px")
                    }
                }

                "&${spliceActionUndo.selector}" {
                    before {
                        backgroundPosition = RelativePosition("0px -32px")
                    }
                }

                "&.twitch" {
                    before {
                        animation += Animation(
                            name = "twitch",
                            duration = 1.s,
                            timing = Timing.easeInOut
                        )
                    }
                }
            }
        }
    }



    // When the splce area becomes too narrow, move controls underneath grid
    container("(max-width: ${gridWidth + controlWidth + gridControlsGapWidth - 1}.9px)") {
        spliceControls.selector {
            maxWidth = gridWidth.px
            width = 100.pct
        }
    }
}

// ====================================================================================================================

fun CssBuilder.spliceTabletStyles() {
    splice.selector {
        marginLeft = 0.px
        marginRight = 0.px
    }
}
