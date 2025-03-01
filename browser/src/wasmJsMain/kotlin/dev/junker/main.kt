package dev.junker

import dev.junker.sudoku.view.SudokuView.Companion.sudokuView
import dev.junker.syntax.highlightCodeBlocks
import kotlinx.browser.document
import kotlinx.html.dom.append

fun main() {
    highlightCodeBlocks()

    document.getElementById(sudokuContainer.id)
        ?.append { sudokuView() }
}
