package dev.junker.maze.view

import dev.junker.maze
import dev.junker.maze.Maze
import dev.junker.maze.MazeState
import dev.junker.maze.cell.WallDirection
import dev.junker.maze.controls.MazeControlsView
import dev.junker.maze.controls.MazeControlsView.Companion.mazeControlsView
import dev.junker.maze.view.MazeGridView.Companion.mazeGridView
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class MazeView private constructor(
    initial: Maze,
    private val root: HTMLElement,
    private val grid: MazeGridView,
    private val controls: MazeControlsView
) {
    private val state = MazeState(
        initial = initial,
//        onOperatorAdded = { placedOperator ->
//            val lhsIndex = placedOperator.lhsPosition.toIndex()!!
//            val rhsIndex = placedOperator.rhsPosition.toIndex()!!
//            val resultIndex = placedOperator.resultPosition.toIndex()!!
//
//            grid.formatOperationCells(placedOperator.operator, lhsIndex, rhsIndex, resultIndex)
//        },
//        onOperatorRemoved = { placedOperator ->
//            val lhsIndex = placedOperator.lhsPosition.toIndex()!!
//            val rhsIndex = placedOperator.rhsPosition.toIndex()!!
//            val resultIndex = placedOperator.resultPosition.toIndex()!!
//
//            grid.clearOperationCells(placedOperator.operator, lhsIndex, rhsIndex, resultIndex)
//        },
        onCellUpdated = { index, cell ->
            grid.updateCell(index, cell)
        },
        onStartMark = { index ->
            grid.markStartCell(index)
        },
        onEndMark = { index ->
            grid.markEndCell(index)
        },
        onStartClear = { index ->
            grid.clearStartCell(index)
        },
        onEndClear = { index ->
            grid.clearEndCell(index)
        }
//        onValidation = { validation ->
//            grid.markCell(validation)
//        },
//        onValidationCleared = { error ->
//            grid.clearCell(error)
//        },
//        onStateUpdated = { state ->
//            if (state == "unlocked") {
//                window.alert("UNLOCKED")
//            }
//
//            setValidation(state)
//        }
    )

    init {
        grid.forEachCellView {
            onCellSelected = {
                grid.activeCell?.selected = false
                selected = true

                grid.activeCell = this
            }
        }

        with(controls) {
            onNextMaze = {
                state.current = Maze.simple(state.current.seed + 1, state.current.sideLength)
            }

            onUndo = {
//                state.undo().ifError {
//                    undoButton.twitch()
//                }
            }
        }
    }

    private fun setValidation(state: String) {
        root.setAttribute("data-state", state)
    }

    private fun HTMLButtonElement.twitch() {
        classList.remove("twitch")
        offsetWidth
        classList.add("twitch")
    }

    companion object {
        fun TagConsumer<Element>.mazeView(initial: Maze): MazeView {
            val root: HTMLDivElement
            val grid: MazeGridView
            val controls: MazeControlsView

            root = div(classes = maze.className) {
                grid = mazeGridView(initial.sideLength)
                controls = mazeControlsView()
            }

            return MazeView(initial, root, grid, controls)
        }
    }
}
