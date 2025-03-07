package dev.junker.sudoku.view

import dev.junker.sudoku
import dev.junker.sudoku.controls.SudokuControlsView
import dev.junker.sudoku.controls.SudokuControlsView.Companion.sudokuControlsView
import dev.junker.sudoku.view.SudokuGridView.Companion.sudokuGridView
import dev.junker.sudokuMarking
import dev.junker.sudokuPreciseMarking
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

class SudokuView private constructor(
    val root: HTMLElement,
    val grid: SudokuGridView,
    val controls: SudokuControlsView
) {
    init {
        controls.onSetValue = { newValue ->
            grid.activeCell?.apply {
                when (controls.markingEnabled) {
                    true -> toggleMark(newValue)
                    false -> {
                        value = newValue
                        grid.highlightValue(newValue)
                    }
                }
            }
        }

        controls.onDeleteValue = {
            grid.activeCell?.apply {
                value = null
                setMarks(emptySet())
                grid.highlightValue(null)
            }
        }

        controls.onMarkingToggled = { enabled ->
            when (enabled) {
                true -> root.classList.add(sudokuMarking.className)
                false -> root.classList.remove(sudokuMarking.className)
            }
        }

        controls.onPreciseMarkingToggled = { enabled ->
            grid.activeCell?.unselect()
            grid.activeCell = null

            when (enabled) {
                true -> root.classList.add(sudokuPreciseMarking.className)
                false -> root.classList.remove(sudokuPreciseMarking.className)
            }
        }
    }

    companion object {
        fun TagConsumer<Element>.sudokuView(): SudokuView {
            val grid: SudokuGridView
            val controls: SudokuControlsView
            val root = div(classes = sudoku.className) {
                grid = sudokuGridView()
                controls = sudokuControlsView()
            }

            return SudokuView(root, grid, controls)
        }
    }
}
