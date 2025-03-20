package dev.junker.sudoku

import dev.junker.Result
import dev.junker.err
import dev.junker.ifOk
import dev.junker.ok

class SudokuState(
    private val initialState: Sudoku,
    private val onCellFilled: (index: Int, value: SudokuValue) -> Unit,
    private val onCellErased: (index: Int) -> Unit,
    private val onMarkEnabled: (index: Int, value: SudokuValue) -> Unit,
    private val onMarkDisabled: (index: Int, value: SudokuValue) -> Unit,
    private val onStateUpdated: () -> Unit
) {
    private val history: MutableList<Sudoku> = mutableListOf()

    val hasHistory: Boolean
        get() = history.isEmpty()

    fun fillCell(index: Int, value: SudokuValue): Result<Unit, String> {
        return update {
            fillCell(index, value, onCellFilled)
                .ifOk { it.disableMarksInSameHouse(index, value, onMarkDisabled) }
        }
    }

    fun toggleMark(index: Int, value: SudokuValue): Result<Unit, String> {
        return update {
            toggleMark(index, value, onMarkEnabled, onMarkDisabled)
        }
    }

    fun eraseCell(index: Int): Result<Unit, String> {
        return update {
            eraseCell(index, onCellErased)
        }
    }

    private inline fun update(
        crossinline action: Sudoku.() -> Result<Sudoku, String>
    ): Result<Unit, String> {
        val current = mostRecent()
        return current.action()
            .ifOk { updatedState ->
                history.add(updatedState)
                onStateUpdated()
            }
    }

    fun undo(): Result<Unit, String> {
        if (hasHistory) {
            return "No history.".err()
        }

        val current = history.removeLast()
        val target = mostRecent()

        return restoreState(current, target).ok()
    }

    private fun restoreState(current: Sudoku, target: Sudoku) {
        (current.cells zip target.cells).forEachIndexed { i, (currentCell, targetCell) ->
            when (targetCell) {
                is SudokuCell.Filled -> {
                    if (currentCell !is SudokuCell.Filled || currentCell.value != targetCell.value) {
                        onCellFilled(i, targetCell.value)
                    }
                }
                is SudokuCell.Empty -> when (currentCell) {
                    is SudokuCell.Empty -> {
                        targetCell.forEachMark { value, shouldBeMarked ->
                            val currentlyMarked = currentCell.isMarked(value)
                            when {
                                shouldBeMarked && !currentlyMarked -> onMarkEnabled(i, value)
                                !shouldBeMarked && currentlyMarked -> onMarkDisabled(i, value)
                            }
                        }
                    }
                    is SudokuCell.Filled -> {
                        onCellErased(i)

                        targetCell.forEachMark { value, shouldBeMarked ->
                            if (shouldBeMarked) {
                                onMarkEnabled(i, value)
                            }
                        }
                    }
                }
            }
        }

        onStateUpdated()
    }

    private fun mostRecent(): Sudoku {
        return history.lastOrNull() ?: initialState
    }
}
