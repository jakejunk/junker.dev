package dev.junker.splice

import dev.junker.Result
import dev.junker.err
import dev.junker.ifOk
import dev.junker.ok

class SpliceState(
    private val initialState: Splice,
    private val onCellFilled: (index: Int, value: UByte) -> Unit,
    private val onCellErased: (index: Int) -> Unit,
    private val onStateUpdated: () -> Unit
) {
    private val history: MutableList<Splice> = mutableListOf()

    fun applyOperator(
        operator: SpliceOperator,
        startPos: Position
    ): Result<Unit, String> {
        return update {
            applyOperator(operator, startPos)
        }
    }

    fun removeOperator(
        pos: Position
    ): Result<Unit, String> {
        return update {
            removeOperator(pos)
        }
    }

    private inline fun update(
        action: Splice.() -> Result<OperationResult, String>
    ): Result<Unit, String> {
        val updated = mostRecent().action()

        return updated.ifOk { (state, index, cell) ->
            when (cell) {
                // TODO: For remove actions, can a cell be really "filled", or reset?
                is SpliceCell.Filled -> onCellFilled(index, cell.value)
                is SpliceCell.Null -> onCellErased(index)
            }

            history.add(state)
            onStateUpdated()
        }
    }

    fun undo(): Result<Unit, String> {
        if (history.isEmpty()) {
            return "No history.".err()
        }

        val current = history.removeLast()
        val target = mostRecent()

        return restoreState(current, target).ok()
    }

    private fun mostRecent(): Splice {
        return history.lastOrNull() ?: initialState
    }

    private fun restoreState(current: Splice, target: Splice) {
        (current.cells zip target.cells).forEachIndexed { i, (from, to) ->
            if (to is SpliceCell.Filled) {
                if (from is SpliceCell.Null || from is SpliceCell.Filled && from != to) {
                    onCellFilled(i, to.value)
                }
            } else if (to is SpliceCell.Null) {
                if (from is SpliceCell.Filled) {
                    onCellErased(i)
                }
            }
        }

        onStateUpdated()
    }
}