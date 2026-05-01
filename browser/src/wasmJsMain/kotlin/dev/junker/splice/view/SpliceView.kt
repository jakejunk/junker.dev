package dev.junker.splice.view

import dev.junker.ifError
import dev.junker.ifOkTry
import dev.junker.orElse
import dev.junker.splice
import dev.junker.splice.Splice
import dev.junker.splice.cell.SpliceCell
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
    private val root: HTMLElement,
    private val grid: SpliceGridView,
    private val controls: SpliceControlsView
) {
    private val state = SpliceState(
        initialSnapshot = Splice.simple(4) { i ->
            SpliceCell((i * 2).toUByte())
        },
        onCellFilled = { index, value ->
            grid.fillCell(index, value)
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
                grid.activeCell?.unselect()
                grid.activeCell = this.also { it.select() }

                grid.highlightValue(value)
            }
        }

        with(controls) {
            onSetValue = { newValue ->
                grid.activeCell?.apply {
                    println("onSetValue: $index $newValue")
                    state.applyOperator(index, newValue)
                }
            }

            onUndo = {
                state.undo().ifError {
                    println("undo: $it")
                    undoButton.twitch()
                }
            }

            onEraseValue = {
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
                grid = spliceGridView(4)
                controls = spliceControlsView()
            }

            return SpliceView(root, grid, controls)
        }
    }
}
