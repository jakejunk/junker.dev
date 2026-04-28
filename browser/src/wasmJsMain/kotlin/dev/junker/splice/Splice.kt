package dev.junker.splice

import dev.junker.Result
import dev.junker.err
import dev.junker.ok

class Splice(
    val sideLength: Int,
    val cells: List<SpliceCell> = List(sideLength * sideLength) { SpliceCell.Empty },
    val operators: List<Pair<Range?, SpliceOperator>>
) {
    fun applyOperator(
        op: SpliceOperator,
        position: Position
    ): Result<Splice, String> {
        val endPos = when (op.direction) {
            Direction.VERTICAL -> position.plusY(2)
            Direction.HORIZONTAL -> position.plusX(2)
        }

        position.toIndex() ?: return "Invalid start position".err()
        endPos.toIndex() ?: return "Invalid end position".err()

        val opRange = position upThrough endPos
        val opIndex = operators
            .indexOfFirst { (range, operator) -> range == null && operator == op }
            .takeIf { it >= 0 }
            ?: return "Invalid operator".err()

        return Splice(
            sideLength = sideLength,
            cells = cells,
            operators = operators.update(opIndex) { opRange to op }
        ).ok()
    }

    fun removeOperator(
        op: SpliceOperator,
        position: Position
    ): Result<Splice, String> {
        position.toIndex() ?: return "Invalid position".err()

        val opIndex = operators
            .indexOfFirst { (range, operator) ->
                range != null && range.contains(position) && operator == op
            }
            .takeIf { it >= 0 }
            ?: return "Invalid operator".err()

        return Splice(
            sideLength = sideLength,
            cells = cells,
            operators = operators.update(opIndex) { null to op }
        ).ok()
    }

    private fun Position.toIndex(): Int? {
        return when {
            x !in 0..sideLength || y !in 0..sideLength -> null
            else -> x + y * sideLength
        }
    }

    private inline fun <T> List<T>.update(
        index: Int,
        action: (T) -> T
    ): List<T> {
        return mapIndexed { i, t ->
            when {
                i == index -> action(t)
                else -> t
            }
        }
    }
}