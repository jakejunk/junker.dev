package dev.junker.splice.view

import dev.junker.ifError
import dev.junker.ifOkTry
import dev.junker.orElse
import dev.junker.splice
import dev.junker.splice.Splice
import dev.junker.splice.SpliceState
import dev.junker.splice.cell.SpliceCell
import dev.junker.splice.controls.SpliceControlsView
import dev.junker.splice.controls.SpliceControlsView.Companion.spliceControlsView
import dev.junker.splice.view.SpliceGridView.Companion.spliceGridView
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class SpliceView private constructor(
    private val root: HTMLElement,
    private val grid: SpliceGridView,
    private val controls: SpliceControlsView
) {
    private val state = SpliceState(
        initialSnapshot = Splice.simple(grid.sideLength) { i ->
            SpliceCell(i.toUByte())
        },
        onOperatorAdded = { placedOperator ->
            val lhsIndex = placedOperator.lhsPosition.toIndex()!!
            val rhsIndex = placedOperator.rhsPosition.toIndex()!!
            val resultIndex = placedOperator.resultPosition.toIndex()!!

            grid.formatOperationCells(placedOperator.operator, lhsIndex, rhsIndex, resultIndex)
        },
        onOperatorRemoved = { placedOperator ->
            val lhsIndex = placedOperator.lhsPosition.toIndex()!!
            val rhsIndex = placedOperator.rhsPosition.toIndex()!!
            val resultIndex = placedOperator.resultPosition.toIndex()!!

            grid.clearOperationCells(placedOperator.operator, lhsIndex, rhsIndex, resultIndex)
        },
        onCellUpdated = { index, value ->
            grid.fillCell(index, value)
        },
        onValidationError = {
            println(it)
        },
        onStateUpdated = {
//            val activeCellValue = grid.activeCell?.value
//            if (activeCellValue != null) {
//                grid.highlightValue(activeCellValue)
//            } else {
//                grid.highlightValue(null)
//            }
        }
    )

    init {
        grid.forEachCellView {
            onCellSelected = {
                grid.activeCell?.selected = false
                selected = true

                grid.activeCell = this
                grid.highlightValue(value)
            }
        }

        with(controls) {
            onSetValue = { newValue ->
                grid.activeCell?.apply {
                    state.applyOperator(index, newValue)
                }
            }

            onUndo = {
                state.undo().ifError {
                    undoButton.twitch()
                }
            }

            onEraseOperator = {
                grid.activeCell
                    .orElse("No cell selected.")
                    .ifOkTry { cell -> state.removeOperator(cell.index) }
                    .ifError { eraseButton.twitch() }
            }
        }
    }

    private fun HTMLButtonElement.twitch() {
        classList.remove("twitch")
        offsetWidth
        classList.add("twitch")
    }

    companion object {
        fun TagConsumer<Element>.spliceView(): SpliceView {
            val root: HTMLDivElement
            val grid: SpliceGridView
            val controls: SpliceControlsView

            root = div(classes = splice.className) {
                // FIXME: This shouldn't be decoupled from SpliceState.sidelength used above
                grid = spliceGridView(6)
                controls = spliceControlsView()
            }

            return SpliceView(root, grid, controls)
        }
    }
}
