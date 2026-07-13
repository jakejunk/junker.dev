package dev.junker.maze.view

import dev.junker.ifError
import dev.junker.ifOk
import dev.junker.maze
import dev.junker.maze.Maze
import dev.junker.maze.MazeState
import dev.junker.maze.controls.MazeControlsView
import dev.junker.maze.controls.MazeControlsView.Companion.mazeControlsView
import dev.junker.maze.stats.MazeStatsView
import dev.junker.maze.stats.MazeStatsView.Companion.mazeStatsView
import dev.junker.maze.view.MazeGridView.Companion.mazeGridView
import dev.junker.mazeSidePane
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
    private val controls: MazeControlsView,
    private val stats: MazeStatsView
) {
    companion object {
        fun TagConsumer<Element>.mazeView(initial: Maze): MazeView {
            val root: HTMLDivElement
            val grid: MazeGridView
            val controls: MazeControlsView
            val stats: MazeStatsView

            root = div(classes = maze.className) {
                grid = mazeGridView(initial.sideLength)

                div(classes = mazeSidePane.className) {
                    controls = mazeControlsView()
                    stats = mazeStatsView()
                }
            }

            return MazeView(initial, root, grid, controls, stats)
        }
    }

    private val state = MazeState(
        initial = initial,
        onCellUpdated = { index, cell ->
            grid.updateCell(index, cell)
        },
        onCellVisited = { index ->
            grid.visitCell(index)
            stats.recordStep()
        },
        onCellCleared = { index ->
            grid.clearCell(index)
        },
        onCurrentMark = { index ->
            grid.markCurrentCell(index)
        },
        onCurrentClear = { index ->
            grid.clearCurrentCell(index)
        },
        onEndMark = { index ->
            grid.markEndCell(index)
        },
        onEndClear = { index ->
            grid.clearEndCell(index)
        }
    )

    init {
        with(grid) {
            onNavigationInput = { direction ->
                state.navigateInDirection(direction)
            }

            onRewindInput = {
                rewindState()
            }
        }

        with(controls) {
            onNextMaze = {
                state.current = Maze.simple(state.current.seed + 1, state.current.sideLength)
            }

            onRewind = {
                rewindState()
            }
        }
    }

    private fun rewindState() {
        state.rewind()
            .ifOk { stepsRewound ->
                repeat(stepsRewound) {
                    stats.rewindStep()
                }
            }
            .ifError { controls.rewindButton.twitch() }
    }

    private fun setValidation(state: String) {
        root.setAttribute("data-state", state)
    }

    private fun HTMLButtonElement.twitch() {
        classList.remove("twitch")
        offsetWidth
        classList.add("twitch")
    }
}
