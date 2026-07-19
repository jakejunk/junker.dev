package dev.junker.maze.view

import dev.junker.ifError
import dev.junker.maze
import dev.junker.maze.Maze
import dev.junker.maze.MazeState
import dev.junker.maze.controls.MazeControlsView
import dev.junker.maze.controls.MazeControlsView.Companion.mazeControlsView
import dev.junker.maze.stats.MazeStatsView
import dev.junker.maze.stats.MazeStatsView.Companion.mazeStatsView
import dev.junker.maze.view.MazeGridView.Companion.mazeGridView
import dev.junker.mazeGridContainer
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
            val stats: MazeStatsView
            val grid: MazeGridView
            val controls: MazeControlsView

            root = div(classes = maze.className) {
                div(classes = mazeGridContainer.className) {
                    stats = mazeStatsView()
                    grid = mazeGridView(initial.sideLength)
                }

                div(classes = mazeSidePane.className) {
                    controls = mazeControlsView()
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
        },
        onTreasureMark = { index ->
            grid.markSideQuestCell(index)
        },
        onTreasureClear = { index ->
            grid.clearSideQuestCell(index)
        },
        onTreasureCollected = { index ->
            grid.clearSideQuestCell(index)
            stats.collectTreasure()
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
                val newMaze = Maze.simple(state.current.seed + 1, state.current.sideLength)

                state.current = newMaze
                stats.reset(newMaze.points.treasures.size)
            }

            onRewind = {
                rewindState()
            }
        }

        stats.reset(state.current.points.treasures.size)
    }

    private fun rewindState() {
        state.rewind()
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
