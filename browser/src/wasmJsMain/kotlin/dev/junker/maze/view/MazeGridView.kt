package dev.junker.maze.view

import dev.junker.maze.Direction
import dev.junker.maze.cell.MazeCell
import dev.junker.maze.cell.MazeCellView
import dev.junker.maze.cell.MazeCellView.Companion.mazeCellView
import dev.junker.mazeGrid
import dev.junker.mazeGridCells
import dev.junker.mazeGridOverlay
import dev.junker.util.InputAdapter
import dev.junker.util.Throttler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.html.TagConsumer
import kotlinx.html.h1
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
) : AutoCloseable {
    companion object {
        fun TagConsumer<Element>.mazeGridView(sideLength: Int): MazeGridView {
            val root: HTMLDivElement
            val cells: List<MazeCellView>

            root = div(classes = mazeGrid.className) {
                val cellsContainer = div(classes = mazeGridCells.className) {
                    cells = List(sideLength * sideLength) { index ->
                        mazeCellView(index)
                    }
                }

                cellsContainer.style.setProperty("--grid-cols", sideLength.toString())

                div(classes = mazeGridOverlay.className) {
                    h1 {
                        +"Tap to play!"
                    }
                }

                tabIndex = "0"
            }

            return MazeGridView(root, cells, sideLength)
        }
    }

    // TODO: Might not need these...
    private val viewScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val throttler = Throttler(viewScope, 200.milliseconds)

    var onNavigationInput: ((Direction) -> Unit)? = null
    var onRewindInput: (() -> Unit)? = null

    private val inputAdapter = InputAdapter(
        target = root,
        onSwipe = { direction ->
            onNavigationInput?.invoke(direction)
        }
    )

    init {
        root.onkeydown = { e ->
            if (e.key == "Backspace") {
                onRewindInput?.invoke()
            } else {
                val direction = when (e.key) {
                    "ArrowUp" -> Direction.UP
                    "ArrowDown" -> Direction.DOWN
                    "ArrowLeft" -> Direction.LEFT
                    "ArrowRight" -> Direction.RIGHT
                    else -> null
                }

                direction?.also {
                    onNavigationInput?.invoke(it)

                    e.preventDefault()
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

    fun markCurrentCell(index: Int) {
        cells[index].markStartCell()
    }

    fun markEndCell(index: Int) {
        cells[index].markEndCell()
    }

    fun markSideQuestCell(index: Int) {
        cells[index].markSideQuestCell()
    }

    fun clearCell(index: Int) {
        cells[index].clearCellVisited()
    }

    fun clearCurrentCell(index: Int) {
        cells[index].clearStartCell()
    }

    fun clearEndCell(index: Int) {
        cells[index].clearEndCell()
    }

    fun clearSideQuestCell(index: Int) {
        cells[index].clearSideQuestCell()
    }

    override fun close() {
        inputAdapter.close()
    }
}
