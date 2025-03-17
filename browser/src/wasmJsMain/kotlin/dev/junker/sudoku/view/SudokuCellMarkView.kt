package dev.junker.sudoku.view

import dev.junker.sudoku.SudokuValue
import dev.junker.sudokuCellMark
import dev.junker.sudokuMarked
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

class SudokuCellMarkView private constructor(
    private val root: HTMLElement
) {
    var onMarkSelected: ((SudokuCellMarkView) -> Unit)? = null

    init {
        root.onclick = { onMarkSelected?.invoke(this) }
    }

    fun toggle() {
        if (root.classList.contains(sudokuMarked.className)) {
            disable()
        } else {
            enable()
        }
    }

    fun enable() {
        root.classList.add(sudokuMarked.className)
    }

    fun disable() {
        root.classList.remove(sudokuMarked.className)
    }

    companion object {
        fun TagConsumer<Element>.sudokuCellMarkView(value: SudokuValue): SudokuCellMarkView {
            val root = div(classes = sudokuCellMark.className) {
                attributes["data-value"] = value.toString()
            }

            return SudokuCellMarkView(root)
        }
    }
}
