package dev.junker.splice.view

import dev.junker.spliceCell
import dev.junker.spliceSelected
import dev.junker.sudoku.SudokuValue
import dev.junker.sudoku.forEachSudokuValue
import dev.junker.sudoku.toSudokuValue
import dev.junker.sudoku.view.SudokuCellMarkView
import dev.junker.sudoku.view.SudokuCellMarkView.Companion.sudokuCellMarkView
import dev.junker.sudokuCell
import dev.junker.sudokuCellMarks
import dev.junker.sudokuSelected
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

class SpliceCellView private constructor(
    val index: Int,
    val root: HTMLElement
) {
    var onCellSelected: ((SpliceCellView) -> Unit)? = null

    var value: UByte?
        get() = root.getAttribute(VALUE_ATTRIBUTE)
            ?.toUByteOrNull()
        set(value) = when (value) {
            null -> root.removeAttribute(VALUE_ATTRIBUTE)
            else -> root.setAttribute(VALUE_ATTRIBUTE, value.toString())
        }

    init {
        root.onpointerdown = { onCellSelected?.invoke(this) }
    }

    fun select() {
        root.classList.add(spliceSelected.className)
    }

    fun unselect() {
        root.classList.remove(spliceSelected.className)
    }

    companion object {
        private const val VALUE_ATTRIBUTE = "data-value"

        fun TagConsumer<Element>.spliceCellView(index: Int): SpliceCellView {
            val root = div(classes = spliceCell.className)

            return SpliceCellView(index, root)
        }
    }
}
