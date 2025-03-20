package dev.junker.sudoku.view

import dev.junker.sudoku.SudokuValue
import dev.junker.sudoku.forEachSudokuValue
import dev.junker.sudoku.toSudokuValue
import dev.junker.sudoku.view.SudokuCellMarkView.Companion.sudokuCellMarkView
import dev.junker.sudokuCell
import dev.junker.sudokuCellMarks
import dev.junker.sudokuSelected
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

class SudokuCellView private constructor(
    val index: Int,
    val root: HTMLElement,
    val marks: Map<SudokuValue, SudokuCellMarkView>
) {
    var onCellSelected: ((SudokuCellView) -> Unit)? = null

    var value: SudokuValue?
        get() = root.getAttribute(VALUE_ATTRIBUTE)
            ?.toIntOrNull()
            ?.toSudokuValue()
        set(value) = when (value) {
            null -> root.removeAttribute(VALUE_ATTRIBUTE)
            else -> root.setAttribute(VALUE_ATTRIBUTE, value.toString())
        }

    init {
        root.onpointerdown = { onCellSelected?.invoke(this) }
    }

    fun select() {
        root.classList.add(sudokuSelected.className)
    }

    fun unselect() {
        root.classList.remove(sudokuSelected.className)
    }

    fun setMarks(visibleMarks: Set<SudokuValue>) {
        marks.forEach { (value, mark) ->
            when (value) {
                in visibleMarks -> mark.enable()
                else -> mark.disable()
            }
        }
    }

    fun enableMark(mark: SudokuValue) {
        marks[mark]?.enable()
    }

    fun disableMark(mark: SudokuValue) {
        marks[mark]?.disable()
    }

    companion object {
        private const val VALUE_ATTRIBUTE = "data-value"

        fun TagConsumer<Element>.sudokuCellView(index: Int): SudokuCellView {
            val marks: Map<SudokuValue, SudokuCellMarkView>
            val root = div(classes = sudokuCell.className) {
                div(classes = sudokuCellMarks.className) {
                    marks = buildMap {
                        forEachSudokuValue {
                            put(it, sudokuCellMarkView(it))
                        }
                    }
                }
            }

            return SudokuCellView(index, root, marks)
        }
    }
}
