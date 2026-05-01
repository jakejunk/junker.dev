package dev.junker.splice

import dev.junker.Result
import dev.junker.err
import dev.junker.ifOk
import dev.junker.ok
import dev.junker.splice.validation.getEffectiveCells

class SpliceState(
    private val initialSnapshot: Splice,
    private val onCellFilled: (index: Int, value: UByte) -> Unit,
    private val onStateUpdated: () -> Unit
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

    private fun mostRecent(): Splice {
        return history.lastOrNull() ?: initialSnapshot
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

    private fun transition(
        current: Splice,
        target: Splice,
        force: Boolean = false
    ) {
        val from = current.getEffectiveCells()
        val to = target.getEffectiveCells()

        (from.effectiveCells zip to.effectiveCells).forEachIndexed { i, (from, to) ->
            if (from != to || force) {
                onCellFilled(i, to.value)
            }
        }

        to.errors.forEach {
            println(it)
        }

        onStateUpdated()
    }
}
