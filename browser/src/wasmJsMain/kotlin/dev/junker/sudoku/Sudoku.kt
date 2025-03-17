package dev.junker.sudoku

import dev.junker.Result
import dev.junker.err
import dev.junker.ok

class Sudoku private constructor(
    val cells: List<SudokuCell> =
        List(9 * 9) { SudokuCell.Empty.FULLY_EMPTY }
) {
    fun fillCell(
        index: Int,
        value: SudokuValue,
        onCellFilled: (index: Int, value: SudokuValue) -> Unit
    ): Result<Sudoku, String> {
        val updatedCells = cells.update(index) { cell ->
            when (cell) {
                is SudokuCell.Filled -> return "Cell has a value!".err()
                is SudokuCell.Empty -> {
                    onCellFilled(index, value)
                    SudokuCell.Filled(value)
                }
            }
        }

        return Sudoku(updatedCells).ok()
    }

    fun eraseCell(index: Int): Sudoku {
        val updatedCells = cells.update(index) { SudokuCell.Empty.FULLY_EMPTY }
        return Sudoku(updatedCells)
    }

    fun toggleMark(
        index: Int,
        value: SudokuValue,
        onMarkEnabled: (index: Int, value: SudokuValue) -> Unit,
        onMarkDisabled: (index: Int, value: SudokuValue) -> Unit
    ): Result<Sudoku, String> {
        val updatedCells = cells.update(index) { cell ->
            when (cell) {
                is SudokuCell.Filled -> return "Cell has a value!".err()
                is SudokuCell.Empty -> when {
                    cell.isMarked(value) -> {
                        onMarkDisabled(index, value)
                        cell.disableMark(value)
                    }
                    else -> {
                        onMarkEnabled(index, value)
                        cell.enableMark(value)
                    }
                }
            }
        }

        return Sudoku(updatedCells).ok()
    }

    fun disableMarksInSameHouse(
        index: Int,
        value: SudokuValue,
        onMarkDisabled: (index: Int, value: SudokuValue) -> Unit
    ): Sudoku {
        val mutableCells = cells.toMutableList()
        val row = index / 9
        val column = index % 9

        for (ci in 0 until 9) {
            val indexInRow = row * 9 + ci
            mutableCells.eraseMarkIfSet(indexInRow, value, onMarkDisabled)
        }

        for (ri in 0 until 9) {
            val indexInRow = ri * 9 + column
            mutableCells.eraseMarkIfSet(indexInRow, value, onMarkDisabled)
        }

        val boxRow = (row / 3) * 3
        val boxColumn = (column / 3) * 3
        for (ri in 0 until 3) {
            for (ci in 0 until 3) {
                val indexInBox = (boxRow + ri) * 9 + (boxColumn + ci)
                mutableCells.eraseMarkIfSet(indexInBox, value, onMarkDisabled)
            }
        }

        return Sudoku(mutableCells.toList())
    }

    companion object {
        val EMPTY = Sudoku()
    }
}

private inline fun <T> List<T>.update(index: Int, action: (T) -> T): List<T> {
    return when {
        index in 0..lastIndex -> {
            val updatedItem = action(get(index))
            slice(0 until index) + updatedItem + slice((index + 1)..lastIndex)
        }
        else -> this
    }
}

private fun MutableList<SudokuCell>.eraseMarkIfSet(
    index: Int,
    value: SudokuValue,
    onMarkDisabled: (index: Int, value: SudokuValue) -> Unit
) {
    val existing = this[index]
    if (existing is SudokuCell.Empty && existing.isMarked(value)) {
        onMarkDisabled(index, value)
        this[index] = existing.disableMark(value)
    }
}
