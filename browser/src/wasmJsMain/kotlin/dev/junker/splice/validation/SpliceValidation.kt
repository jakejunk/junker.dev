package dev.junker.splice.validation

import dev.junker.splice.Splice
import dev.junker.splice.cell.SpliceCell
import dev.junker.splice.cell.perform
import kotlin.math.abs

data class SpliceValidation(
    val effectiveCells: List<SpliceCell>,
    val errors: Set<SpliceError>
)

sealed interface SpliceError {
    val index: Int

    data class Null(
        override val index: Int
    ) : SpliceError

    data class Adjacency(
        override val index: Int
    ) : SpliceError
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

    val nullErrors = validateNulls(effectiveCells)
    val adjacencyErrors = validateAdjacency(effectiveCells)
    val allErrors = nullErrors + adjacencyErrors

    return SpliceValidation(effectiveCells, allErrors.toSet())
}

private fun validateNulls(
    cells: List<SpliceCell>
): List<SpliceError> {
    return cells.mapIndexed { i, cell -> i to cell }
        .filter { (_, cell) -> cell.isNullCell() }
        .map { (i, _) -> SpliceError.Null(i) }
}

private fun Splice.validateAdjacency(
    cells: List<SpliceCell>
): List<SpliceError> {
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
                add(SpliceError.Adjacency(i))
            }
        }
    }
}
