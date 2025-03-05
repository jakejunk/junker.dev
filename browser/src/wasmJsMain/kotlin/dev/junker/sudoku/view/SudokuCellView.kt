package dev.junker.sudoku.view

import dev.junker.sudoku.SudokuValue
import dev.junker.sudoku.forEachSudokuValue
import dev.junker.sudoku.view.SudokuCellMarkView.Companion.sudokuCellMarkView
import dev.junker.sudokuCell
import dev.junker.sudokuCellMarks
import dev.junker.sudokuCellValue
import dev.junker.sudokuSelected
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

class SudokuCellView private constructor(
    val root: HTMLElement,
    val value: HTMLElement,
    val marks: Map<SudokuValue, SudokuCellMarkView>
) {
    var onCellSelected: ((SudokuCellView) -> Unit)? = null

    init {
        value.onclick = { onCellSelected?.invoke(this) }
    }

    fun select() {
        root.classList.add(sudokuSelected.className)
    }

    fun unselect() {
        root.classList.remove(sudokuSelected.className)
    }

    fun setValue(newValue: SudokuValue) {
        value.setAttribute("data-value", newValue.toString())
    }

    fun setMarks(visibleMarks: Set<SudokuValue>) {
        marks.forEach { (value, view) ->
            val visible = when (value) {
                in visibleMarks -> true
                else -> false
            }

            view.setVisibility(visible)
        }
    }

    fun toggleMark(mark: SudokuValue) {
        marks[mark]?.toggleVisibility()
    }

    companion object {
        fun TagConsumer<Element>.sudokuCellView(): SudokuCellView {
            val value: HTMLElement
            val marks: Map<SudokuValue, SudokuCellMarkView>
            val root = div(classes = sudokuCell.className) {
                value = div(classes = sudokuCellValue.className) {
                    attributes["data-value"] = ""
                }

                div(classes = sudokuCellMarks.className) {
                    marks = buildMap {
                        forEachSudokuValue {
                            put(it, sudokuCellMarkView(it))
                        }
                    }
                }
            }

            return SudokuCellView(root, value, marks)
        }
    }
}
