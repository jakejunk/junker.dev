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
        }

        hover {
            backgroundColor = SiteColor.BackgroundLight.color.withAlpha(0.5)
        }

        "&${sudokuSelected.selector}" {
            backgroundColor = SiteColor.BackgroundLight.color
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

        media("(pointer: fine)") {
            "&:hover::after" {
                backgroundColor = SiteColor.BackgroundLight.color
                color = Color.unset
            }
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
        transition += Transition("transform", duration = 0.1.s, timing = Timing.ease)
        transition += Transition("box-shadow", duration = 0.1.s, timing = Timing.ease)

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
                frostedGlass(Color.transparent)
                appearance = Appearance.none
                backgroundColor = Color.transparent
                border = light2pxBorder()
                borderRadius = cornerRadius
                color = Color.unset
                fontSize = 2.rem
                margin = Margin(0.px)
                padding = Padding(8.px)

                before {
                    display = Display.block
                    lineHeight = 1.618.rem.lh
                    textAlign = TextAlign.center
                }

                hover {
                    backgroundColor = SiteColor.BackgroundLight.color
                    cursor = Cursor.pointer
                }

                "&${sudokuActionMark.selector}" {
                    before {
                        content = "✏".quoted
                    }

                    checked {
                        backgroundColor = SiteColor.BackgroundLight.color
                        before {
                            content = "✎".quoted
                        }
                    }
                }

                "&${sudokuActionDelete.selector}" {
                    before {
                        content = "✖".quoted
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
