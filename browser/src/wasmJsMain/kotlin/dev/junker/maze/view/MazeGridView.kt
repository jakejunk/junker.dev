package dev.junker.maze.view

import dev.junker.maze.Direction
import dev.junker.maze.cell.MazeCell
import dev.junker.maze.cell.MazeCellView
import dev.junker.maze.cell.MazeCellView.Companion.mazeCellView
import dev.junker.mazeGrid
import dev.junker.util.Throttler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import kotlinx.html.tabIndex
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.time.Duration.Companion.milliseconds

class MazeGridView private constructor(
    private val root: HTMLElement,
    val cells: List<MazeCellView>,
    val sideLength: Int,
) {
    companion object {
        fun TagConsumer<Element>.mazeGridView(sideLength: Int): MazeGridView {
            val root: HTMLDivElement
            val cells: List<MazeCellView>

            root = div(classes = mazeGrid.className) {
                cells = List(sideLength * sideLength) { index ->
                    mazeCellView(index)
                }

                tabIndex = "0"
            }

            root.style.setProperty("--grid-cols", sideLength.toString())

            return MazeGridView(root, cells, sideLength)
        }
    }

    private val viewScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val throttler = Throttler(viewScope, 200.milliseconds)

    var onNavigation: ((Direction) -> Unit)? = null

    init {
        root.onkeydown = { e ->
            e.preventDefault()

            throttler.post {
                val direction = when (e.key) {
                    "ArrowUp" -> Direction.UP
                    "ArrowDown" -> Direction.DOWN
                    "ArrowLeft" -> Direction.LEFT
                    "ArrowRight" -> Direction.RIGHT
                    else -> null
                }

                direction?.also {
                    onNavigation?.invoke(it)
                }
            }
        }
    }

    fun updateCell(index: Int, cell: MazeCell) {
        cells[index].updateWalls(cell)
    }

    fun visitCell(index: Int) {
        cells[index].markCellVisited()
    }

    fun clearCell(index: Int) {
        cells[index].clearCellVisited()
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
}
