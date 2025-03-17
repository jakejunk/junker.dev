package dev.junker.sudoku.view

import dev.junker.sudoku.view.SudokuCellView.Companion.sudokuCellView
import dev.junker.sudokuBox
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class SudokuBoxView private constructor(
    val root: HTMLElement,
    val cells: List<SudokuCellView>
) {
    companion object {
        fun TagConsumer<Element>.sudokuBoxView(boxIndex: Int): SudokuBoxView {
            val root: HTMLDivElement
            val cells: List<SudokuCellView>

            root = div(classes = sudokuBox.className) {
                cells = List(9) { cellIndex ->
                    val index = globalIndex(boxIndex, cellIndex)

                    sudokuCellView(index)
                }
            }

            return SudokuBoxView(root, cells)
        }

        private fun globalIndex(bi: Int, ci: Int): Int {
            val boxRow = bi / 3
            val boxColumn = bi % 3
            val cellRow = ci / 3
            val cellColumn = ci % 3
            val gridRow = boxRow * 3 + cellRow
            val gridColumn = boxColumn * 3 + cellColumn

            return gridColumn * 9 + gridRow
        }
    }
}
