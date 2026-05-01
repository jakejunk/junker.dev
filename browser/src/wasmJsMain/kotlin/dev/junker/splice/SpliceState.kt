package dev.junker.splice

import dev.junker.Result
import dev.junker.err
import dev.junker.ifOk
import dev.junker.ok

// TODO: Try to consolidate the onFilled/Erased invocations,
//  to bring the logic into one spot

class SpliceState(
    private val initialSnapshot: Splice,
    private val onCellFilled: (index: Int, value: UByte) -> Unit,
    private val onStateUpdated: () -> Unit
) {
    private val history: MutableList<Splice> = mutableListOf()

    init {
        restoreState(
            current = Splice.empty(initialSnapshot.sideLength),
            target = initialSnapshot,
            force = true
        )
    }

    fun applyOperator(
        index: Int,
        operator: SpliceOperator
    ): Result<Unit, String> {
        return update {
            val position = Position(index % sideLength, index / sideLength)
            applyOperator(position, operator)
        }
    }

    fun removeOperator(
        index: Int
    ): Result<Unit, String> {
        return update {
            val position = Position(index % sideLength, index / sideLength)
            removeOperator(position)
        }
    }

    private inline fun update(
        action: Splice.() -> Result<Splice, String>
    ): Result<Unit, String> {
        val last = mostRecent()
        val updated = last.action()

        return updated.ifOk { updatedSnapshot ->
            restoreState(last, updatedSnapshot)
            history.add(updatedSnapshot)
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
        return history.lastOrNull() ?: initialSnapshot
    }

    private fun restoreState(
        current: Splice,
        target: Splice,
        force: Boolean = false
    ) {
        (current.effectiveCells zip target.effectiveCells).forEachIndexed { i, (from, to) ->
            if (from != to || force) {
                onCellFilled(i, to.value)
            }
        }

        onStateUpdated()
    }
}
