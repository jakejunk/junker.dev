package dev.junker.maze

import dev.junker.Result
import dev.junker.err
import dev.junker.maze.cell.MazeCell
import dev.junker.maze.cell.WallDirection
import dev.junker.ok
import kotlin.math.min

class MazeState(
    initial: Maze,
    private val onCellUpdated: Maze.(Int, MazeCell) -> Unit,
    private val onCellVisited: Maze.(Int) -> Unit,
    private val onCellCleared: Maze.(Int) -> Unit,
    private val onCurrentMark: Maze.(Int) -> Unit,
    private val onCurrentClear: Maze.(Int) -> Unit,
    private val onEndMark: Maze.(Int) -> Unit,
    private val onEndClear: Maze.(Int) -> Unit,
    private val onTreasureMark: Maze.(Int) -> Unit,
    private val onTreasureClear: Maze.(Int) -> Unit,
    private val onTreasureCollected: Maze.(Int) -> Unit,
//    private val onValidation: Splice.(SpliceCellValidation) -> Unit,
//    private val onValidationCleared: Splice.(SpliceCellValidation) -> Unit,
//    private val onStateUpdated: Splice.(String) -> Unit
) {
    private val progress = mutableListOf<Int>()

    val currentCellIndex: Int
        get() = when {
            progress.isEmpty() -> current.points.start
            else -> progress.last()
        }

    var current: Maze = initial
        set(value) {
            transition(
                current = current,
                target = value,
            )

            field = value
        }

    init {
        transition(
            current = current,
            target = current,
            force = true
        )
    }

    fun navigateInDirection(direction: Direction): Result<Unit, String> {
        val currentIndex = currentCellIndex
        val (cell, position) = with(current) {
            cells[currentIndex] to currentCellIndex.toPosition()
        }

        val (asWallTag, destination) = when (direction) {
            Direction.UP -> WallDirection.NORTH to position.plusY(-1)
            Direction.DOWN -> WallDirection.SOUTH to position.plusY(1)
            Direction.LEFT -> WallDirection.WEST to position.plusX(-1)
            Direction.RIGHT -> WallDirection.EAST to position.plusX(1)
        }

        if (cell.hasWall(asWallTag)) {
            return "Wall".err()
        }

        val (destinationCell, destinationIndex) = with(current) {
            val destinationIndex = destination.toIndex()
                ?: return "OOB".err()

            cells[destinationIndex] to destinationIndex
        }

        if (destinationCell.hasWall(asWallTag.opposite())) {
            return "Wall but weird".err()
        }

        with(current) {
            onCellVisited(currentIndex)
            onCurrentClear(currentIndex)
            onCurrentMark(destinationIndex)

            // TODO: Feels hacky. Consider transitioning to a new maze state with the treasure point removed
            if (destinationIndex in points.treasures && destinationIndex !in progress) {
                onTreasureCollected(destinationIndex)
            }
        }

        progress.add(destinationIndex)

        return Unit.ok()
    }


    fun rewind(): Result<Int, String> {
        if (progress.isEmpty()) {
            return "No progress.".err()
        }

        val stepsToRewind = min(3, progress.size)

        current.onCellVisited(currentCellIndex)

        repeat(stepsToRewind) {
            val currentIndex = progress.removeLast()

            with(current) {
                // TODO: Decide if rewinding should actually clear visited cells
//                if (currentIndex !in progress) {
//                    onCellCleared(currentIndex)
//                }

                onCurrentClear(currentIndex)
                onCurrentMark(currentCellIndex)
            }
        }

        return stepsToRewind.ok()
    }

    private fun transition(
        current: Maze,
        target: Maze,
        force: Boolean = false
    ) {
        (current.cells zip target.cells).forEachIndexed { i, (from, to) ->
            if (from != to || force) {
                target.onCellUpdated(i, to)
            }

            target.onCellCleared(i)
        }

        current.onCurrentClear(currentCellIndex)
        target.onCurrentMark(target.points.start)

        current.onEndClear(current.points.end)
        target.onEndMark(target.points.end)

        for (sideQuest in current.points.treasures) {
            current.onTreasureClear(sideQuest)
        }

        for (treasure in target.points.treasures) {
            target.onTreasureMark(treasure)
        }

        progress.clear()
    }
}
