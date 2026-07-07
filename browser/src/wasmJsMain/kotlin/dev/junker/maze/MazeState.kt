package dev.junker.maze

import dev.junker.Result
import dev.junker.err
import dev.junker.maze.cell.MazeCell
import dev.junker.maze.cell.WallDirection
import dev.junker.ok

class MazeState(
    initial: Maze,
//    private val onOperatorAdded: Splice.(PlacedOperator) -> Unit,
//    private val onOperatorRemoved: Splice.(PlacedOperator) -> Unit,
    private val onCellUpdated: Maze.(Int, MazeCell) -> Unit,
    private val onCellVisited: Maze.(Int) -> Unit,
    private val onCellCleared: Maze.(Int) -> Unit,
    private val onStartMark: Maze.(Int) -> Unit,
    private val onEndMark: Maze.(Int) -> Unit,
    private val onStartClear: Maze.(Int) -> Unit,
    private val onEndClear: Maze.(Int) -> Unit,
//    private val onValidation: Splice.(SpliceCellValidation) -> Unit,
//    private val onValidationCleared: Splice.(SpliceCellValidation) -> Unit,
//    private val onStateUpdated: Splice.(String) -> Unit
) {
    private val progress = mutableListOf<Int>()

    val currentCellIndex: Int
        get() = when {
            progress.isEmpty() -> current.startIndex
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
        val (cell, position) = with(current) {
            cells[currentCellIndex] to currentCellIndex.toPosition()
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

        progress.add(destinationIndex)

        current.onCellVisited(destinationIndex)

        return Unit.ok()
    }

//
//    fun undo(): Result<Unit, String> {
//        if (history.isEmpty()) {
//            return "No history.".err()
//        }
//
//        val current = history.removeLast()
//        val target = mostRecent()
//
//        return transition(current, target).ok()
//    }

//    fun applyOperator(
//        index: Int,
//        operator: SpliceOperator
//    ): Result<Unit, String> {
//        return update {
//            applyOperator(index.toPosition(), operator)
//        }
//    }
//
//    fun removeOperator(
//        index: Int
//    ): Result<Unit, String> {
//        return update {
//            removeOperator(index.toPosition())
//        }
//    }
//
//    private inline fun update(
//        action: Splice.() -> Result<Splice, String>
//    ): Result<Unit, String> {
//        val last = mostRecent()
//        val updated = last.action()
//
//        return updated.ifOk { updatedSnapshot ->
//            transition(last, updatedSnapshot)
//            history.add(updatedSnapshot)
//        }
//    }
//
//    private fun mostRecent(): Splice {
//        return history.lastOrNull() ?: initialSnapshot
//    }
//

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

        if (current.startIndex != target.startIndex || force) {
            current.onStartClear(current.startIndex)
            target.onStartMark(target.startIndex)
        }

        if (current.endIndex != target.endIndex || force) {
            current.onEndClear(current.endIndex)
            target.onEndMark(target.endIndex)
        }
    }
}
