package dev.junker.components.sudoku

import dev.junker.*
import dev.junker.components.SiteColor
import dev.junker.components.general.*
import kotlinx.css.*
import kotlinx.css.properties.*

private const val gridWidth = 576
private const val controlWidth = 256
private const val gridControlsGapWidth = 32

fun CssBuilder.sudokuStyles() {
    sudokuGridStyles()
    sudokuControlStyles()
}

private fun CssBuilder.sudokuGridStyles() {
    keyframes("fadeIn") {
        0 {
            opacity = 0
        }
        100 {
            opacity = 1
        }
    }

    sudokuPlaceholder.selector {
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

    sudoku.selector {
        wrappingRow(
            rowGap = 16.px,
            columnGap = gridControlsGapWidth.px
        )
        containerType = ContainerType.inlineSize
        justifyContent = JustifyContent.center
        margin = Margin(2.rem, (-1).rem + 5.px)
    }

    sudokuGrid.selector {
        frostedGlass(Color.transparent)
        grid3x3()
        monospaceFont()
        aspectRatio = AspectRatio(1, 1)
        border = lightBorder(3.px)
        flexBasis = 100.pct.basis
        flexGrow = 1
        maxWidth = gridWidth.px
    }

    sudokuBox.selector {
        grid3x3()
        border = lightBorder(2.px)
    }

    sudokuCell.selector {
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

        val highlightSelectors = List(9) { i -> i + 1 }
            .joinToString(",") { i ->
                // AKA: Cell with `data-value=i` inside grid with `data-highlight=i`
                // Using `:where()` to lower specificity
                "${sudokuGrid.selector}[data-highlight='$i'] :where(&[data-value='$i'])"
            }

        highlightSelectors {
            backgroundColor = SiteColor.BackgroundDarkish.color
            fontWeight = FontWeight.bold
        }

        "&${sudokuSelected.selector}" {
            backgroundColor = SiteColor.BackgroundLight.color
            fontWeight = FontWeight.normal
        }
    }

    sudokuCellMarks.selector {
        grid3x3()
        height = 100.pct
        pointerEvents = PointerEvents.none
        width = 100.pct

        // When the parent container is in a "precise marking" state,
        // allow pointer events on the cell marks element
        "${sudokuMarking.selector}${sudokuPreciseMarking.selector} &" {
            pointerEvents = PointerEvents.unset
        }
    }

    "${sudokuCell.selector}[data-value] ${sudokuCellMarks.selector}" {
        display = Display.none
    }

    sudokuCellMark.selector {
        fontSize = min(1.rem, NumericLinearDimension(33, "cqi"))
        lineHeight = NumericLinearDimension(35, "cqh").lh
        position = Position.relative

        after {
            property("content", "attr(data-value)")
            borderRadius = 50.pct
            color = Color.transparent
            position = Position.absolute
            textAlign = TextAlign.center
            width = 100.pct
        }

        "&${sudokuMarked.selector}::after" {
            color = Color.unset
        }

        val highlightSelectors = List(9) { i -> i + 1 }
            .joinToString(",") { i ->
                // AKA: Mark with `data-value=i` inside grid with `data-highlight=i`
                "${sudokuGrid.selector}[data-highlight='$i'] &[data-value='$i']"
            }

        highlightSelectors {
            fontWeight = FontWeight.bold
        }
    }

    // User has mouse
    media("(pointer: fine)") {
        sudokuCell.selector {
            hover {
                backgroundColor = SiteColor.BackgroundLight.color.withAlpha(0.5)
            }
        }

        sudokuCellMark.selector {
            hover {
                // Because using `after()` overwrites `content`
                "&::after" {
                    backgroundColor = SiteColor.BackgroundLight.color
                    color = Color.unset
                }
            }
        }
    }
}

private fun CssBuilder.sudokuControlStyles() {
    sudokuControls.selector {
        flexColumn(columnGap = 16.px)
        height = LinearDimension.fitContent
        width = controlWidth.px
    }

    sudokuNumpad.selector {
        grid3x3(cellGap = 1.ch)
    }

    sudokuPossibleValue.selector {
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

    sudokuActions.selector {
        flexRow(rowGap = 1.ch)

        label {
            flexColumn()
            flexBasis = FlexBasis("0")
            flexGrow = 1
            textAlign = TextAlign.center

            sudokuAction.selector {
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

                "&${sudokuActionMark.selector}" {
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

                "&${sudokuActionErase.selector}" {
                    before {
                        backgroundPosition = RelativePosition("-32px 0px")
                    }
                }

                "&${sudokuActionUndo.selector}" {
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

    sudokuToggles.selector {
        flexColumn()
        frostedGlass(Color.transparent)
        border = light2pxBorder()
        borderRadius = cornerRadius
        padding = Padding(1.rem)
    }

    // When the sudoku area becomes too narrow, move controls underneath grid
    container("(max-width: ${gridWidth + controlWidth + gridControlsGapWidth - 1}.9px)") {
        sudokuControls.selector {
            maxWidth = gridWidth.px
            width = 100.pct
        }

        sudokuNumpad.selector {
            display = Display.flex
            gap = 5.px
        }

        sudokuToggles.selector {
            padding = Padding(5.px)
        }
    }
}

// ====================================================================================================================

fun CssBuilder.sudokuTabletStyles() {
    sudoku.selector {
        marginLeft = 0.px
        marginRight = 0.px
    }
}
