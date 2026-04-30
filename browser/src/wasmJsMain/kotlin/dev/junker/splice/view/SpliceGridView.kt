package dev.junker.splice.view

import dev.junker.splice.view.SpliceCellView.Companion.spliceCellView
import dev.junker.spliceGrid
import dev.junker.sudoku.SudokuValue
import dev.junker.sudoku.view.SudokuBoxView
import dev.junker.sudoku.view.SudokuBoxView.Companion.sudokuBoxView
import dev.junker.sudoku.view.SudokuCellView
import dev.junker.sudokuGrid
import kotlinx.html.TagConsumer
import kotlinx.html.itemProp
import kotlinx.html.js.div
import kotlinx.html.style
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class SpliceGridView private constructor(
    private val root: HTMLElement,
    val cells: List<SpliceCellView>,
) {
    var activeCell: SpliceCellView? = null

    fun highlightValue(value: UByte?) {
        when (value) {
            null -> root.removeAttribute(HIGHLIGHT_ATTRIBUTE)
            else -> root.setAttribute(HIGHLIGHT_ATTRIBUTE, value.toString())
        }
    }

    fun fillCell(index: Int, value: UByte) {
        cells[index].value = value
    }

    fun eraseCell(index: Int) {
        cells[index].value = null
    }

    inline fun forEachCellView(
        action: SpliceCellView.() -> Unit
    ) {
        cells.forEach { cell ->
            cell.action()
        }
    }

    companion object {
        const val HIGHLIGHT_ATTRIBUTE = "data-highlight"

        fun TagConsumer<Element>.spliceGridView(sideLength: Int): SpliceGridView {
            val root: HTMLDivElement
            val cells: List<SpliceCellView>

            root = div(classes = spliceGrid.className) {
                cells = List(sideLength * sideLength) { index ->
                    spliceCellView(index)
                }
            }

            root.style.setProperty("--grid-cols", sideLength.toString())

            return SpliceGridView(root, cells)
        }
    }
}
