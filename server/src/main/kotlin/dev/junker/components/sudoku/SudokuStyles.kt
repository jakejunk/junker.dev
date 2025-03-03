package dev.junker.components.sudoku

import dev.junker.*
import dev.junker.components.SiteColor
import dev.junker.components.general.*
import kotlinx.css.*
import kotlinx.css.properties.*

fun CssBuilder.sudokuStyles() {
    sudokuGridStyles()
    sudokuControlStyles()
}

private fun CssBuilder.sudokuGridStyles() {
    sudoku.selector {
        display = Display.flex
        columnGap = 32.px
        containerType = ContainerType.inlineSize
        flexDirection = FlexDirection.row
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.center
        margin = Margin(2.rem, (-1).rem + 5.px)
        rowGap = 16.px
    }

    sudokuGrid.selector {
        frostedGlass(Color.transparent)
        grid3x3()
        monospaceFont()
        aspectRatio = AspectRatio(1, 1)
        border = lightBorder(3.px)
        flexBasis = 100.pct.basis
        flexGrow = 1
        maxWidth = 576.px
    }

    sudokuBox.selector {
        grid3x3()
        border = lightBorder(2.px)
    }

    sudokuCell.selector {
        border = lightBorder(1.px)
        containerType = ContainerType.size
        position = Position.relative

        "&${sudokuSelected.selector}" {
            backgroundColor = SiteColor.BackgroundLight.color.withAlpha(0.5)
        }
    }

    sudokuCellMarks.selector {
        property("align-content", "space-between")
        display = Display.flex
        flexWrap = FlexWrap.wrap
        height = 100.pct
        justifyContent = JustifyContent.spaceBetween
        pointerEvents = PointerEvents.none
        width = 100.pct

        // When the parent container is in a "marking" state,
        // allow pointer events on the cell marks element
        "${sudokuMarking.selector} &" {
            pointerEvents = PointerEvents.unset
        }
    }

    "${sudokuCellValue.selector}[data-value]:not([data-value=\"\"]) + ${sudokuCellMarks.selector}" {
        display = Display.none
    }

    sudokuCellMark.selector {
        fontSize = 0.85.rem
        height = 32.5.pct
        position = Position.relative
        width = 32.5.pct

        after {
            property("content", "attr(data-value)")
            borderRadius = 50.pct
            color = Color.transparent
            display = Display.inlineBlock
            height = 100.pct
            lineHeight = 1.25.rem.lh
            position = Position.absolute
            textAlign = TextAlign.center
            width = 100.pct
        }

        "&:hover::after" {
            backgroundColor = SiteColor.BackgroundLight.color
            color = Color.unset
        }

        "&${sudokuMarked.selector}::after" {
            color = Color.unset
        }
    }

    sudokuCellValue.selector {
        fontSize = clamp(5.px, NumericLinearDimension(90, "cqi"), 2.5.rem)
        height = 100.pct
        lineHeight = NumericLinearDimension(100, "cqh").lh
        position = Position.absolute
        textAlign = TextAlign.center
        width = 100.pct

        after {
            property("content", "attr(data-value)")
        }

        hover {
            backgroundColor = SiteColor.BackgroundLight.color.withAlpha(0.5)
        }
    }
}

private fun CssBuilder.sudokuControlStyles() {
    sudokuControls.selector {
        flexColumn()
        frostedGlass(Color.transparent)
        border = light2pxBorder()
        borderRadius = cornerRadius
        height = LinearDimension.fitContent
        padding = Padding(1.rem)
        width = 256.px
    }

    sudokuNumpad.selector {
        grid3x3(gap = 1.ch)
        marginBottom = 1.rem
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
            width = 40.pct
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

    container("(max-width: ${576 + 256 + 32 - 1}.9px)") {
        sudokuControls.selector {
            maxWidth = 576.px
            padding = Padding(8.px)
            width = 100.pct
        }

        sudokuNumpad.selector {
            display = Display.flex
            gap = 5.px
        }
    }
}

private fun CssBuilder.grid3x3(gap: LinearDimension? = null) {
    display = Display.grid
    gridTemplateColumns = GridTemplateColumns.repeat("3, 1fr")

    if (gap != null) {
        this.gap = gap
    }
}

// ====================================================================================================================

fun CssBuilder.sudokuTabletStyles() {
    sudoku.selector {
        marginLeft = 0.px
        marginRight = 0.px
    }
}
