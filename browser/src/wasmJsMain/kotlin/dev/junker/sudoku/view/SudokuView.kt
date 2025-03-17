package dev.junker.sudoku.view

import dev.junker.sudoku
import dev.junker.sudoku.SudokuState
import dev.junker.sudoku.controls.SudokuControlsView
import dev.junker.sudoku.controls.SudokuControlsView.Companion.sudokuControlsView
import dev.junker.sudoku.view.SudokuGridView.Companion.sudokuGridView
import dev.junker.sudokuMarking
import dev.junker.sudokuPreciseMarking
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class SudokuView private constructor(
    private val sudokuElement: HTMLElement,
    private val grid: SudokuGridView,
    private val controls: SudokuControlsView
) {
    private val state = SudokuState(
        onCellFilled = { index, value ->
            grid.fillCell(index, value)
        },
        onCellErased = { index ->
            grid.eraseCell(index)
        },
        onMarkEnabled = { index, value ->
            grid.enableMark(index, value)
        },
        onMarkDisabled = { index, value ->
            grid.disableMark(index, value)
        },
        onStateUpdated = {
            val activeCellValue = grid.activeCell?.value
            if (activeCellValue != null) {
                grid.highlightValue(activeCellValue)
            } else {
                grid.highlightValue(null)
            }
        }
    )

    init {
        grid.forEachCellView {
            onCellSelected = {
                grid.activeCell?.unselect()
                grid.activeCell = this.apply { select() }

                grid.highlightValue(value)
            }

            marks.entries.forEach { (value, markView) ->
                markView.onMarkSelected = {
                    state.toggleMark(index, value)
                }
            }
        }

        with(controls) {
            onSetValue = { newValue ->
                grid.activeCell?.apply {
                    when (controls.markingEnabled) {
                        true -> state.toggleMark(index, newValue)
                        false -> state.fillCell(index, newValue)
                    }
                }
            }

            onUndo = {
                state.undo()
            }

            onEraseValue = {
                grid.activeCell?.apply {
                    state.eraseCell(index)
                }
            }

            onMarkingToggled = { enabled ->
                when (enabled) {
                    true -> sudokuElement.classList.add(sudokuMarking.className)
                    false -> sudokuElement.classList.remove(sudokuMarking.className)
                }
            }

            onPreciseMarkingToggled = { enabled ->
                grid.activeCell?.unselect()
                grid.activeCell = null

                when (enabled) {
                    true -> sudokuElement.classList.add(sudokuPreciseMarking.className)
                    false -> sudokuElement.classList.remove(sudokuPreciseMarking.className)
                }
            }
        }
    }

    companion object {
        fun TagConsumer<Element>.sudokuView(): SudokuView {
            val root: HTMLDivElement
            val grid: SudokuGridView
            val controls: SudokuControlsView

            root = div(classes = sudoku.className) {
                grid = sudokuGridView()
                controls = sudokuControlsView()
            }

            return SudokuView(root, grid, controls)
        }
    }
}
