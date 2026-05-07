package dev.junker.splice.view

import dev.junker.splice.SpliceOperator
import dev.junker.splice.cell.SpliceCellView
import dev.junker.splice.cell.SpliceCellView.Companion.spliceCellView
import dev.junker.splice.validation.SpliceError
import dev.junker.spliceGrid
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class SpliceGridView private constructor(
    private val root: HTMLElement,
    val sideLength: Int,
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
        println("fillCell: $index: $value")
        cells[index].value = value
    }

    fun formatOperationCells(
        op: SpliceOperator,
        lhsIndex: Int,
        rhsIndex: Int,
        resultIndex: Int
    ) {
        cells[lhsIndex].mark(op,"lhs")
        cells[rhsIndex].mark(op,"rhs")
        cells[resultIndex].mark(op,"result")
    }

    fun clearOperationCells(
        op: SpliceOperator,
        lhsIndex: Int,
        rhsIndex: Int,
        resultIndex: Int
    ) {
        cells[lhsIndex].clear("lhs", op.direction)
        cells[rhsIndex].clear("rhs", op.direction)
        cells[resultIndex].clear("result", op.direction)
    }

    fun markCell(error: SpliceError) {
        cells[error.index].mark(error)
    }

    fun clearCell(error: SpliceError) {
        cells[error.index].clear(error)
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

            return SpliceGridView(root, sideLength, cells)
        }
    }
}
