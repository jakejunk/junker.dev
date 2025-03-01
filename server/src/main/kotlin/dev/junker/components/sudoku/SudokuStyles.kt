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
    sudokuContainer.selector {
        flexColumn()
        margin = Margin(32.px, LinearDimension.auto)
    }

    sudoku.selector {
        display = Display.flex
        flexDirection = FlexDirection.row
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.center
    }

    sudokuGrid.selector {
        frostedGlass(Color.transparent)
        grid3x3()
        monospaceFont()
        aspectRatio = AspectRatio(1, 1)
        border = lightBorder(3.px)
        flexBasis = 100.pct.basis
        flexGrow = 1
        marginBottom = 4.ch
        maxWidth = 576.px
    }

    sudokuBox.selector {
        grid3x3()
        border = lightBorder(2.px)
    }

    sudokuCell.selector {
        border = lightBorder(1.px)
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
        fontSize = 2.5.rem
        height = 100.pct
        lineHeight = 150.pct.lh
        position = Position.absolute
        textAlign = TextAlign.center
        top = 0.px
        width = 100.pct

        after {
            property("content", "attr(data-value)")
        }

        hover {
            backgroundColor = SiteColor.BackgroundDarkish.color
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
        margin = Margin(horizontal = 4.ch)
        padding = Padding(1.rem)
    }

    sudokuNumpad.selector {
        grid3x3(gap = 1.ch)
        marginBottom = 1.rem
    }

    sudokuPossibleValue.selector {
        monospaceFont(fontWeight = FontWeight.bold, fontSize = 32.px)
        backgroundImage = radialGradient {
            circle()
            at(RelativePosition.center)
            colorStop(hex(0xDDD))
            colorStop(hex(0xAAA))
        }
        border = Border.none
        borderRadius = 50.pct
        boxShadow += BoxShadowInset(color = hex(0xFFF).withAlpha(0.6), offsetX = (-2).px, blurRadius = 3.px)
        boxShadow += BoxShadowInset(color = hex(0x000).withAlpha(0.3), offsetX = 3.px, blurRadius = 5.px)
        boxShadow += BoxShadow(color = hex(0x000).withAlpha(0.4), offsetX = (-2).px, blurRadius = 4.px)
        color = hex(0x222)
        cursor = Cursor.pointer
        height = 64.px
        textShadow += TextShadow(
            color = hex(0xFFF).withAlpha(0.6),
            offsetX = (-1).px,
            blurRadius = 2.px,
            spreadRadius = LinearDimension(" ")
        )
        width = 64.px

        active {
            filter = "brightness(0.75)"
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

}
