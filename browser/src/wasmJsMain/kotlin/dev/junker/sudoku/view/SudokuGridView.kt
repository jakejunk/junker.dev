package dev.junker.sudoku.view

import dev.junker.sudoku.SudokuValue
import dev.junker.sudoku.view.SudokuBoxView.Companion.sudokuBoxView
import dev.junker.sudokuGrid
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class SudokuGridView private constructor(
    private val root: HTMLElement,
    val boxes: List<SudokuBoxView>,
) {
    var activeCell: SudokuCellView? = null

    fun highlightValue(value: SudokuValue?) {
        when (value) {
            null -> root.removeAttribute(HIGHLIGHT_ATTRIBUTE)
            else -> root.setAttribute(HIGHLIGHT_ATTRIBUTE, value.toString())
        }
    }

    fun fillCell(index: Int, value: SudokuValue) {
        cellAt(index).value = value
    }

    fun eraseCell(index: Int) {
        cellAt(index).apply {
            value = null
            setMarks(emptySet())
        }
    }

    fun enableMark(index: Int, value: SudokuValue) {
        cellAt(index).enableMark(value)
    }

    fun disableMark(index: Int, value: SudokuValue) {
        cellAt(index).disableMark(value)
    }

    private fun cellAt(index: Int): SudokuCellView {
        val row = index / 9
        val column = index % 9

        val boxRow = row / 3
        val boxColumn = column / 3
        val boxIndex = boxColumn * 3 + boxRow

        val localRow = row % 3
        val localColumn = column % 3
        val cellIndex = localColumn * 3 + localRow

        return boxes[boxIndex].cells[cellIndex]
    }

    inline fun forEachCellView(
        crossinline action: SudokuCellView.() -> Unit
    ) {
        boxes.forEach { box ->
            box.cells.forEach { cell ->
                cell.action()
            }
        }
    }

    companion object {
        const val HIGHLIGHT_ATTRIBUTE = "data-highlight"

        fun TagConsumer<Element>.sudokuGridView(): SudokuGridView {
            val root: HTMLDivElement
            val boxes: List<SudokuBoxView>

            root = div(classes = sudokuGrid.className) {
                boxes = List(9) { boxIndex ->
                    sudokuBoxView(boxIndex)
                }
            }

            return SudokuGridView(root, boxes)
        }
    }
}
