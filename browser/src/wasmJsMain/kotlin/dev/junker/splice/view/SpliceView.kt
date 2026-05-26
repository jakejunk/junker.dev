package dev.junker.splice.view

import dev.junker.ifError
import dev.junker.ifOkTry
import dev.junker.orElse
import dev.junker.splice
import dev.junker.splice.Splice
import dev.junker.splice.SpliceState
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
    initial: Splice,
    private val root: HTMLElement,
    private val grid: SpliceGridView,
    private val controls: SpliceControlsView
) {
    private val state = SpliceState(
        initialSnapshot = initial,
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
        onValidation = { validation ->
            grid.markCell(validation)
        },
        onValidationCleared = { error ->
            grid.clearCell(error)
        },
        onStateUpdated = { state ->
            println("State updated: $state")
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

            val initial = Splice.simple(4) { i ->
                (i + 1).toUByte()
            }

            root = div(classes = splice.className) {
                // FIXME: This shouldn't be decoupled from SpliceState.sidelength used above
                grid = spliceGridView(initial.sideLength)
                controls = spliceControlsView()
            }

            return SpliceView(initial, root, grid, controls)
        }
    }
}
