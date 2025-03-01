package dev.junker.sudoku.view

import dev.junker.sudoku.view.SudokuBoxView.Companion.sudokuBoxView
import dev.junker.sudokuGrid
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

class SudokuGridView private constructor(
    val root: HTMLElement,
    val boxes: List<SudokuBoxView>
) {
    var activeCell: SudokuCellView? = null

    init {
        boxes.forEach { box ->
            box.onBoxCellSelected = { cell ->
                activeCell?.unselect()
                activeCell = cell.apply { select() }
            }
        }
    }

    /**
     * @param row Index in local coordinates (0..2).
     * @param column Index in local coordinates (0..2).
     */
    fun boxAt(row: Int, column: Int): SudokuBoxView {
        val boxIndex = (column * 3 + row).coerceIn(0, boxes.lastIndex)
        return boxes[boxIndex]
    }

    companion object {
        fun TagConsumer<Element>.sudokuGridView(): SudokuGridView {
            val boxes: List<SudokuBoxView>
            val root = div(classes = sudokuGrid.className) {
                boxes = List(9) {
                    sudokuBoxView()
                }
            }

            return SudokuGridView(root, boxes)
        }
    }
}
