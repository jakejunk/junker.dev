package dev.junker.maze.validation

import dev.junker.splice.Splice
import dev.junker.splice.cell.SpliceCell
import dev.junker.splice.cell.perform
import kotlin.math.abs

data class SpliceValidation(
    val effectiveCells: List<SpliceCell>,
    val validations: Set<SpliceCellValidation>
) {
    val isLocked: Boolean
        get() = validations
            .filterIsInstance<SpliceCellValidation.Error>()
            .isNotEmpty()
}

sealed interface SpliceCellValidation {
    val index: Int

    data class Jump(
        override val index: Int
    ) : SpliceCellValidation

    data class JumpTarget(
        override val index: Int
    ) : SpliceCellValidation

    data class JumpDestination(
        override val index: Int
    ) : SpliceCellValidation

    data class Skip(
        override val index: Int
    ) : SpliceCellValidation

    sealed interface Error : SpliceCellValidation

    data class Null(
        override val index: Int
    ) : Error

    data class Adjacency(
        override val index: Int
    ) : Error
}

fun Splice.getEffectiveCells(): SpliceValidation {
    val effectiveCells = cells.toMutableList()

    operators
        .sortedBy { placedOp -> placedOp.resultPosition.toIndex() }
        .forEach { placedOp ->
            val lhs = effectiveCells[placedOp.lhsPosition.toIndex()!!]
            val rhs = effectiveCells[placedOp.rhsPosition.toIndex()!!]

            placedOp.resultPosition.toIndex()
                ?.let { effectiveCells[it] = placedOp.operator.perform(lhs, rhs) }
        }

    return SpliceValidation(effectiveCells, validate(effectiveCells))
}

private fun Splice.validate(
    cells: List<SpliceCell>
): Set<SpliceCellValidation> {
    return buildSet {
        var previousValue = cells.first().value
        var jumpFound = false
        var jumpTarget: UByte? = null

        cells.forEachIndexed { i, cell ->
            if (jumpFound) {
                add(SpliceCellValidation.JumpTarget(i))
                jumpFound = false
                jumpTarget = cell.value
            } else if (jumpTarget != null) {
                if (jumpTarget == cell.value) {
                    jumpTarget = null
                    add(SpliceCellValidation.JumpDestination(i))
                } else {
                    add(SpliceCellValidation.Skip(i))
                }
            } else if (cell.isJumpCell()) {
                add(SpliceCellValidation.Jump(i))
                jumpFound = true
            } else if (cell.isNullCell()) {
                add(SpliceCellValidation.Null(i))
            } else if (!partOfOperator(i)) {
                val lhs = previousValue.toInt()
                val rhs = cell.value.toInt()
                val distance = abs(lhs - rhs)

                if (distance > 1 && distance != UByte.MAX_VALUE.toInt()) {
                    add(SpliceCellValidation.Adjacency(i))
                }
            }

            previousValue = cell.value
        }
    }
}

private fun Splice.partOfOperator(i: Int): Boolean {
    return operators.any { op ->
        i in setOf(
            op.lhsPosition.toIndex(),
            op.rhsPosition.toIndex(),
            op.resultPosition.toIndex()
        )
    }
}
