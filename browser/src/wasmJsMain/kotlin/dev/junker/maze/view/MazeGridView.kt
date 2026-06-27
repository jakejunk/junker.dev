package dev.junker.maze.view

import dev.junker.maze.cell.MazeCell
import dev.junker.maze.cell.MazeCellView
import dev.junker.maze.cell.MazeCellView.Companion.mazeCellView
import dev.junker.mazeGrid
import dev.junker.splice.SpliceOperator
import dev.junker.splice.cell.SpliceCellView
import dev.junker.splice.cell.SpliceCellView.Companion.spliceCellView
import dev.junker.splice.validation.SpliceCellValidation
import dev.junker.spliceGrid
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class MazeGridView private constructor(
    private val root: HTMLElement,
    val sideLength: Int,
    val cells: List<MazeCellView>,
) {
    var activeCell: MazeCellView? = null

    fun updateCell(index: Int, cell: MazeCell) {
        cells[index].update(cell)
    }

    inline fun forEachCellView(
        action: MazeCellView.() -> Unit
    ) {
        cells.forEach { cell ->
            cell.action()
        }
    }

    companion object {
        fun TagConsumer<Element>.mazeGridView(sideLength: Int): MazeGridView {
            val root: HTMLDivElement
            val cells: List<MazeCellView>

            root = div(classes = mazeGrid.className) {
                cells = List(sideLength * sideLength) { index ->
                    mazeCellView(index)
                }
            }

            root.style.setProperty("--grid-cols", sideLength.toString())

            return MazeGridView(root, sideLength, cells)
        }
    }
}
