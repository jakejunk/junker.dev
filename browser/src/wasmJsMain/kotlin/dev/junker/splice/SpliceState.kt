package dev.junker.splice

import dev.junker.Result
import dev.junker.err
import dev.junker.ifOk
import dev.junker.ok
import dev.junker.splice.validation.SpliceCellValidation
import dev.junker.splice.validation.getEffectiveCells

class SpliceState(
    private val initialSnapshot: Splice,
    private val onOperatorAdded: Splice.(PlacedOperator) -> Unit,
    private val onOperatorRemoved: Splice.(PlacedOperator) -> Unit,
    private val onCellUpdated: Splice.(Int, UByte) -> Unit,
    private val onValidation: Splice.(SpliceCellValidation) -> Unit,
    private val onValidationCleared: Splice.(SpliceCellValidation) -> Unit,
    private val onStateUpdated: Splice.() -> Unit
) {
    private val history: MutableList<Splice> = mutableListOf()

    init {
        transition(
            current = initialSnapshot,
            target = initialSnapshot,
            force = true
        )
    }

    fun undo(): Result<Unit, String> {
        if (history.isEmpty()) {
            return "No history.".err()
        }

        val current = history.removeLast()
        val target = mostRecent()

        return transition(current, target).ok()
    }

    fun applyOperator(
        index: Int,
        operator: SpliceOperator
    ): Result<Unit, String> {
        return update {
            applyOperator(index.toPosition(), operator)
        }
    }

    fun removeOperator(
        index: Int
    ): Result<Unit, String> {
        return update {
            removeOperator(index.toPosition())
        }
    }

    private inline fun update(
        action: Splice.() -> Result<Splice, String>
    ): Result<Unit, String> {
        val last = mostRecent()
        val updated = last.action()

        return updated.ifOk { updatedSnapshot ->
            transition(last, updatedSnapshot)
            history.add(updatedSnapshot)
        }
    }

    private fun mostRecent(): Splice {
        return history.lastOrNull() ?: initialSnapshot
    }

    private fun transition(
        current: Splice,
        target: Splice,
        force: Boolean = false
    ) {
        (current.operators - target.operators).forEach { placedOperator ->
            target.onOperatorRemoved(placedOperator)
        }

        (if (force) target.operators else target.operators - current.operators).forEach { placedOperator ->
            target.onOperatorAdded(placedOperator)
        }

        val fromSnapshot = current.getEffectiveCells()
        val toSnapshot = target.getEffectiveCells()

        (fromSnapshot.effectiveCells zip toSnapshot.effectiveCells).forEachIndexed { i, (from, to) ->
            if (from != to || force) {
                target.onCellUpdated(i, to.value)
            }
        }

        (fromSnapshot.validations - toSnapshot.validations).forEach { clearedError ->
            target.onValidationCleared(clearedError)
        }

        (if (force) toSnapshot.validations else toSnapshot.validations - fromSnapshot.validations).forEach { newError ->
            target.onValidation(newError)
        }

        target.onStateUpdated()
    }
}
