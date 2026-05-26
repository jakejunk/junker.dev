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

        display = Display.grid
        gridTemplateColumns = GridTemplateColumns.repeat("var(--grid-cols), 1fr")
    }

    spliceCell.selector {
        border = lightBorder(1.px)
        containerType = ContainerType.size
        position = Position.relative

        before {
            property("content", "attr(data-value)")
            fontSize = clamp(5.px, NumericLinearDimension(40, "cqi"), 2.5.rem)
            height = 100.pct
            lineHeight = NumericLinearDimension(100, "cqh").lh
            position = Position.absolute
            textAlign = TextAlign.center
            textDecoration = TextDecoration(lines = setOf(TextDecorationLine.inherit))
            property("text-decoration-color", SiteColor.SubtleText.color.toString())
            width = 100.pct
        }

        after {
            borderRadius = 16.px
            bottom = 50.pct - 16.px
            color = SiteColor.SubtleText.color
            fontSize = clamp(5.px, NumericLinearDimension(40, "cqi"), 3.rem)
            fontWeight = FontWeight.bold
            height = 32.px
            lineHeight = 32.px.lh
            position = Position.absolute
            right = (-17).px
            textAlign = TextAlign.center
            width = 32.px
            zIndex = 1
        }

        "&${spliceLhsHorizontal.selector}" {
            "&[data-operator-h-rhs]" {
                after {
                    property("content", "attr(data-operator-h-rhs)")
                }
            }

            "&[data-operator-h-lhs]" {
                after {
                    property("content", "attr(data-operator-h-lhs)")
                }
            }
        }

        "&${spliceRhsHorizontal.selector}" {
            after {
                content = "=".quoted
            }
        }

        "&${spliceResultHorizontal.selector}" {
            textDecoration = TextDecoration(lines = setOf(TextDecorationLine.underline))
        }

        "&${spliceOutOfRange.selector}" {
            secondaryTextGlow()
        }

        "&${spliceNull.selector}" {
            val c = SiteColor.BackgroundLight.color

            color = SiteColor.SubtleText.color
            property("background", "repeating-linear-gradient(60deg, $c, $c 2px, transparent 2px, transparent 27px)")
        }

        "&${spliceSkip.selector}" {
            val c = SiteColor.BackgroundLight.color

            color = SiteColor.SubtleText.color
            property("background", "repeating-linear-gradient(60deg, $c, $c 2px, transparent 2px, transparent 27px)")
        }

        "&${spliceJump.selector}" {
            after {
                content = "↯".quoted
            }
        }

        "&${spliceJumpTarget.selector}" {
            primaryTextGlow()
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

    spliceOperations.selector {
        grid3x3(cellGap = 1.ch)
    }

    splicePossibleOperation.selector {
        monospaceFont(fontWeight = FontWeight.bold)
        property("-webkit-tap-highlight-color", "transparent")
        aspectRatio = AspectRatio(1, 1)
        backgroundImage = radialGradient {
            circle()
            at(RelativePosition.offset(65.pct, 45.pct))
            colorStop(hex(0xE0E0E0))
            colorStop(hex(0xB0B0B0))
        }
        border = Border.none
        borderRadius = 50.pct
        boxShadow += BoxShadowInset(
            color = hex(0xFFF).withAlpha(0.5),
            offsetY = 1.px,
            blurRadius = 2.px
        )
        boxShadow += BoxShadowInset(
            color = hex(0x000).withAlpha(0.3),
            offsetY = (-1).px,
            blurRadius = 2.px
        )
        boxShadow += BoxShadow(
            color = hex(0x000).withAlpha(0.4),
            offsetX = (-1).px,
            offsetY = 1.px,
            blurRadius = 2.px
        )
        color = hex(0x333)
        containerType = ContainerType.inlineSize
        cursor = Cursor.pointer
        flexGrow = 1
        position = Position.relative

        children("span") {
            fontSize = clamp(10.px, NumericLinearDimension(100, "cqi"), 32.px)
        }

        before {
            backgroundImage = radialGradient {
                ellipse()
                at(RelativePosition.center)
                colorStop(hex(0xFFF).withAlpha(0.4))
                colorStop(Color.transparent)
            }
            borderRadius = 50.pct
            height = 20.pct
            left = 55.pct
            position = Position.absolute
            top = 15.pct
            width = 35.pct
        }

        active {
            transform { translateY(2.px) }
            boxShadow += BoxShadowInset(
                color = hex(0x000).withAlpha(0.6),
                offsetX = 1.px,
                offsetY = 1.px,
                blurRadius = 3.px
            )
        }

        focusVisible {
            outlineColor = SiteColor.SubtleText.color
            outlineStyle = OutlineStyle.solid
            outlineWidth = 5.px
        }
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
