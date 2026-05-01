package dev.junker.splice

import dev.junker.Result
import dev.junker.err
import dev.junker.ok
import dev.junker.splice.cell.SpliceCell

class Splice private constructor(
    val sideLength: Int,
    val cells: List<SpliceCell>,
    val operators: List<PlacedOperator>
) {

    fun applyOperator(
        position: Position,
        operator: SpliceOperator
    ): Result<Splice, String> {
        val candidate = PlacedOperator(position, operator)
        if (candidate.resultPosition.x >= sideLength || candidate.resultPosition.y >= sideLength) {
            return "Result off grid at ${candidate.resultPosition}".err()
        } else if (operators.any { it.resultPosition == candidate.resultPosition }) {
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

    fun Position.toIndex(): Int? {
        return when {
            x !in 0..<sideLength || y !in 0..<sideLength -> null
            else -> x + y * sideLength
        }
    }

    companion object {
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
