package dev.junker.splice.validation

import dev.junker.splice.Splice
import dev.junker.splice.cell.SpliceCell
import dev.junker.splice.cell.perform
import kotlin.math.abs

data class SpliceValidation(
    val effectiveCells: List<SpliceCell>,
    val validations: Set<SpliceCellValidation>
) {
    val isLocked: Boolean
        get() = validations.isNotEmpty()
}

sealed interface SpliceCellValidation {
    val index: Int

    data class Jump(
        override val index: Int
    ) : SpliceCellValidation

    data class JumpTarget(
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

    val jumps = validateJumps(effectiveCells)
    val nullErrors = validateNulls(effectiveCells)
    val adjacencyErrors = validateAdjacency(effectiveCells)
    val validations = (jumps + nullErrors + adjacencyErrors).toSet()

    return SpliceValidation(effectiveCells, validations)
}

private fun validateJumps(
    cells: List<SpliceCell>
): List<SpliceCellValidation> {
    val validations = buildList {
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
                } else {
                    add(SpliceCellValidation.Skip(i))
                }
            } else if (cell.isJumpCell()) {
                add(SpliceCellValidation.Jump(i))
                jumpFound = true
            }
        }
    }

    return validations
}

private fun validateNulls(
    cells: List<SpliceCell>
): List<SpliceCellValidation.Null> {
    return cells
        .mapIndexed { i, cell -> i to cell }
        .filter { (_, cell) -> cell.isNullCell() }
        .map { (i, _) -> SpliceCellValidation.Null(i) }
}

private fun Splice.validateAdjacency(
    cells: List<SpliceCell>
): List<SpliceCellValidation.Adjacency> {
    if (cells.size < 2) {
        return emptyList()
    }

    fun partOfOperator(a: Int): Boolean {
        return operators.any { op ->
            a in setOf(
                op.lhsPosition.toIndex(),
                op.rhsPosition.toIndex(),
                op.resultPosition.toIndex()
            )
        }
    }

    return buildList {
        for (i in 1..<cells.size) {
            if (partOfOperator(i)) {
                continue
            }

            val lhs = cells[i - 1].value.toInt()
            val rhs = cells[i].value.toInt()
            val distance = abs(lhs - rhs)

            if (distance > 1 && distance != UByte.MAX_VALUE.toInt()) {
                add(SpliceCellValidation.Adjacency(i))
            }
        }
    }
}
