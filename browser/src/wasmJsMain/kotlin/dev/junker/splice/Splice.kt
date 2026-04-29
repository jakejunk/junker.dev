package dev.junker.splice

import dev.junker.Result
import dev.junker.err
import dev.junker.ok
import dev.junker.sudoku.SudokuCell
import kotlin.collections.List

class Splice private constructor(
    val sideLength: Int,
    val cells: List<SpliceCell>,
    val operators: List<Pair<Range, SpliceOperator>>
) {
    fun applyOperator(
        operator: SpliceOperator,
        startPos: Position
    ): Result<OperationResult, String> {
        val (endPos, resultPos) = when (operator.direction) {
            Direction.VERTICAL -> startPos.plusY(1) to startPos.plusY(2)
            Direction.HORIZONTAL -> startPos.plusX(1) to startPos.plusX(2)
        }

        val lhs = startPos.toIndex()
            ?.let { cells[it] }
            ?: return "Invalid start position".err()

        val rhs = endPos.toIndex()
            ?.let { cells[it] }
            ?: return "Invalid end position".err()

        val resultIndex = resultPos.toIndex()
            ?: return "Invalid result position".err()

        return OperationResult(
            new = Splice(
                sideLength = sideLength,
                cells = cells,
                operators = operators + (startPos upThrough resultPos to operator),
            ),
            index = resultIndex,
            cell = operator.perform(lhs, rhs)
        ).ok()
    }

    fun removeOperator(
        pos: Position
    ): Result<OperationResult, String> {
        // FIXME: Either forbid result cell overlaps, or add a second check
        //  for operations with overlapping result cells before resetting value

        val possible = operators
            .mapIndexed { i, (range, _) -> i to range }
            .filter { (_, range) -> range.contains(pos) }

        if (possible.isEmpty()) {
            return "No operator at position $pos".err()
        } else if (possible.size > 1) {
            return "Multiple operations at position $pos".err()
        }

        val (opIndex, range) = possible[0]
        val resultIndex = range.end.toIndex()
            ?: return "Invalid result position".err()

        return OperationResult(
            new = Splice(
                sideLength = sideLength,
                cells = cells,
                operators = operators - operators[opIndex],
            ),
            index = resultIndex,
            cell = cells[resultIndex]
        ).ok()
    }

    private fun Position.toIndex(): Int? {
        return when {
            x !in 0..sideLength || y !in 0..sideLength -> null
            else -> x + y * sideLength
        }
    }

    companion object {
        fun empty(sideLength: Int): Splice {
            return Splice(
                sideLength = sideLength,
                cells = List(sideLength * sideLength) { SpliceCell.Null },
                operators = emptyList()
            )
        }
    }
}

data class OperationResult(
    val new: Splice,
    val index: Int,
    val cell: SpliceCell
)
