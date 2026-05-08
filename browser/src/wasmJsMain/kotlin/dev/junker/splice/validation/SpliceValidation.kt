package dev.junker.splice.validation

import dev.junker.splice.Splice
import dev.junker.splice.cell.SpliceCell
import dev.junker.splice.cell.perform
import kotlin.math.abs

data class SpliceValidation(
    val effectiveCells: List<SpliceCell>,
    val validations: Set<SpliceCellValidation>
)

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
    val allErrors = jumps + nullErrors + adjacencyErrors

    return SpliceValidation(effectiveCells, allErrors.toSet())
}

private fun validateJumps(
    cells: List<SpliceCell>
): List<SpliceCellValidation> {
    val validations = buildList {
        var jumpFound = false
        var jumpTarget: UByte? = null

        cells.forEachIndexed { i, cell ->
            if (cell.isJumpCell()) {
                add(SpliceCellValidation.Jump(i))
                jumpFound = true
            } else if (jumpFound) {
                add(SpliceCellValidation.JumpTarget(i))
                jumpFound = false
                jumpTarget = cell.value
            } else if (jumpTarget != null) {
                if (jumpTarget == cell.value) {
                    jumpTarget = null
                } else {
                    add(SpliceCellValidation.Skip(i))
                }
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

    fun sharedOperator(a: Int, b: Int): Boolean {
        return operators.any { op ->
            val indices = setOf(
                op.lhsPosition.toIndex(),
                op.rhsPosition.toIndex(),
                op.resultPosition.toIndex()
            )

            a in indices && b in indices
        }
    }

    return buildList {
        for (i in 1..<cells.size) {
            if (sharedOperator(i - 1, i)) {
                continue
            }

            val a = cells[i - 1].value.toInt()
            val b = cells[i].value.toInt()
            val distance = abs(a - b)

            if (distance > 1 && distance != UByte.MAX_VALUE.toInt()) {
                add(SpliceCellValidation.Adjacency(i))
            }
        }
    }
}
