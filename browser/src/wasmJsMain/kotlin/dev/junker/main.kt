package dev.junker

import dev.junker.splice.view.SpliceView.Companion.spliceView
import dev.junker.sudoku.view.SudokuView.Companion.sudokuView
import dev.junker.syntax.highlightCodeBlocks
import kotlinx.browser.document
import kotlinx.html.dom.append

fun main() {
    highlightCodeBlocks()

    with(document) {
        getElementById(sudokuPlaceholder.id)?.remove()
        getElementById(sudokuContainer.id)
            ?.append { sudokuView() }

        getElementById(splicePlaceholder.id)?.remove()
        getElementById(spliceContainer.id)
            ?.append { spliceView() }
    }
}
