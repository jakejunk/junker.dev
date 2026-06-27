package dev.junker.maze

import dev.junker.maze.cell.MazeCell

class MazeState(
    initial: Maze,
//    private val onOperatorAdded: Splice.(PlacedOperator) -> Unit,
//    private val onOperatorRemoved: Splice.(PlacedOperator) -> Unit,
    private val onCellUpdated: Maze.(Int, MazeCell) -> Unit,
//    private val onValidation: Splice.(SpliceCellValidation) -> Unit,
//    private val onValidationCleared: Splice.(SpliceCellValidation) -> Unit,
//    private val onStateUpdated: Splice.(String) -> Unit
) {
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
//        (current.operators - target.operators).forEach { placedOperator ->
//            target.onOperatorRemoved(placedOperator)
//        }

//        (if (force) target.operators else target.operators - current.operators).forEach { placedOperator ->
//            target.onOperatorAdded(placedOperator)
//        }

//        val fromSnapshot = current.getEffectiveCells()
//        val toSnapshot = target.getEffectiveCells()

        (current.cells zip target.cells).forEachIndexed { i, (from, to) ->
            if (from != to || force) {
                target.onCellUpdated(i, to)
            }
        }

//        (fromSnapshot.validations - toSnapshot.validations).forEach { cleared ->
//            target.onValidationCleared(cleared)
//        }
//
//        (if (force) toSnapshot.validations else toSnapshot.validations - fromSnapshot.validations).forEach { validation ->
//            target.onValidation(validation)
//        }
//
//        if (toSnapshot.isLocked) {
//            target.onStateUpdated("locked")
//        } else {
//            target.onStateUpdated("unlocked")
//        }
    }
}
