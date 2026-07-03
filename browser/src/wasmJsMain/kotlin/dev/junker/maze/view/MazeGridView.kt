package dev.junker.maze.view

import dev.junker.maze.cell.MazeCell
import dev.junker.maze.cell.MazeCellView
import dev.junker.maze.cell.MazeCellView.Companion.mazeCellView
import dev.junker.mazeGrid
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class MazeGridView private constructor(
    private val root: HTMLElement,
    val cells: List<MazeCellView>,
    val sideLength: Int,
) {
    var activeCell: MazeCellView? = null

    fun updateCell(index: Int, cell: MazeCell) {
        cells[index].updateWalls(cell)
    }

    fun markStartCell(index: Int) {
        cells[index].markStartCell()
    }

    fun markEndCell(index: Int) {
        cells[index].markEndCell()
    }

    fun clearStartCell(index: Int) {
        cells[index].clearStartCell()
    }

    fun clearEndCell(index: Int) {
        cells[index].clearEndCell()
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

            return MazeGridView(root, cells, sideLength)
        }
    }
}
