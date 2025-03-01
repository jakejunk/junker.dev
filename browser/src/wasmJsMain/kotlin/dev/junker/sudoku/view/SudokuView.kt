package dev.junker.sudoku.view

import dev.junker.sudoku
import dev.junker.sudoku.controls.GameMode
import dev.junker.sudoku.controls.SudokuControlsView
import dev.junker.sudoku.controls.SudokuControlsView.Companion.sudokuControlsView
import dev.junker.sudoku.view.SudokuGridView.Companion.sudokuGridView
import dev.junker.sudokuMarking
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
        controls.onChangeMode = { mode ->
            grid.activeCell?.unselect()
            grid.activeCell = null

            when (mode) {
                GameMode.MARKING -> root.classList.add(sudokuMarking.className)
                GameMode.PLAYING -> root.classList.remove(sudokuMarking.className)
            }
        }

        controls.onSetValue = { value ->
            grid.activeCell?.setValue(value)
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
