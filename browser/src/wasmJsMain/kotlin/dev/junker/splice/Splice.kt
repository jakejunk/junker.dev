package dev.junker.splice

import dev.junker.Result
import dev.junker.err
import dev.junker.ok

class Splice private constructor(
    val sideLength: Int,
    val cells: List<SpliceCell>,
    val operators: List<PlacedOperator>
) {
    val effectiveCells: List<SpliceCell>
        get() {
            val result = cells.toMutableList()

            operators
                .sortedBy { placedOp -> placedOp.resultPosition.toIndex() }
                .forEach { placedOp ->
                    val lhs = result[placedOp.lhsPosition.toIndex()!!]
                    val rhs = result[placedOp.rhsPosition.toIndex()!!]

                    placedOp.resultPosition.toIndex()
                        ?.let { result[it] = placedOp.operator.perform(lhs, rhs) }
                }

            return result
        }

    fun applyOperator(
        position: Position,
        operator: SpliceOperator
    ): Result<Splice, String> {
        val candidate = PlacedOperator(position, operator)
        if (operators.any { it.resultPosition == candidate.resultPosition }) {
            return "Multiple results at ${candidate.resultPosition}".err()
        }

        return Splice(
            sideLength = sideLength,
            cells = cells,
            operators = operators + candidate,
        ).ok()
    }

    fun removeOperator(
        position: Position
    ): Result<Splice, String> {
        val possible = operators
            .mapIndexed { i, placedOp -> i to placedOp }
            .filter { (_, placedOp) -> placedOp contains position }

        if (possible.isEmpty()) {
            return "No operator at $position".err()
        } else if (possible.size > 1) {
            return "Multiple operations at $position".err()
        }

        val (opIndex, _) = possible[0]

        return Splice(
            sideLength = sideLength,
            cells = cells,
            operators = operators - operators[opIndex],
        ).ok()
    }

    private fun Position.toIndex(): Int? {
        return when {
            x !in 0..<sideLength || y !in 0..<sideLength -> null
            else -> x + y * sideLength
        }
    }

    companion object {
        fun empty(sideLength: Int): Splice {
            return Splice(
                sideLength = sideLength,
                cells = List(sideLength * sideLength) { SpliceCell(0u) },
                operators = emptyList()
            )
        }

        fun simple(
            sideLength: Int,
            init: (Int) -> SpliceCell
        ): Splice {
            return Splice(
                sideLength = sideLength,
                cells = List(sideLength * sideLength) { i -> init(i) },
                operators = emptyList()
            )
        }
    }
}
