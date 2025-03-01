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
    init {
        root.onclick = { toggleVisibility() }
    }

    fun toggleVisibility() {
        val shouldMark = !root.classList.contains(sudokuMarked.className)
        setVisibility(shouldMark)
    }

    fun setVisibility(visible: Boolean) {
        val markClassList = root.classList
        when (visible) {
            true -> markClassList.add(sudokuMarked.className)
            false -> markClassList.remove(sudokuMarked.className)
        }
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
