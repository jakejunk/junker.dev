package dev.junker.sudoku.view

import dev.junker.sudoku.view.SudokuCellView.Companion.sudokuCellView
import dev.junker.sudokuBox
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

class SudokuBoxView private constructor(
    val root: HTMLElement,
    val cells: List<SudokuCellView>
) {
    var onBoxCellSelected: ((SudokuCellView) -> Unit)? = null

    init {
        cells.forEach { cell ->
            cell.onCellSelected = { onBoxCellSelected?.invoke(it) }
        }
    }

    /**
     * @param row Index in local coordinates (0..2).
     * @param column Index in local coordinates (0..2).
     */
    fun cellAt(row: Int, column: Int): SudokuCellView {
        val localCellIndex = (column * 3 + row).coerceIn(0, cells.lastIndex)
        return cells[localCellIndex]
    }

    companion object {
        fun TagConsumer<Element>.sudokuBoxView(): SudokuBoxView {
            val cells: List<SudokuCellView>
            val root = div(classes = sudokuBox.className) {
                cells = List(9) {
                    sudokuCellView()
                }
            }

            return SudokuBoxView(root, cells)
        }
    }
}
