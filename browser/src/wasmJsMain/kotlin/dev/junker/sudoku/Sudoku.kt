package dev.junker.sudoku

class Sudoku private constructor(
    private val cells: List<SudokuCell> =
        List(9 * 9) { SudokuCell.Empty.FULLY_EMPTY }
) {
    companion object {
        val EMPTY = Sudoku()
    }

    fun toggleMark(row: Int, column: Int, value: SudokuValue): Sudoku {
        val index = column * 9 + row
        val updatedCells = cells.mapIndexed { i, cell ->
            when (i) {
                index -> when (cell) {
                    is SudokuCell.Empty -> cell.mark(value)
                    is SudokuCell.Filled -> SudokuCell.Empty.FULLY_EMPTY.mark(value)
                }
                else -> cell
            }
        }

        return Sudoku(updatedCells)
    }

    fun forEachCell(action: (ri: Int, ci: Int, value: SudokuCell) -> Unit) {
        cells.forEachIndexed { i, sudokuCell ->
            val ri = i % 9
            val ci = i / 9

            action(ri, ci, sudokuCell)
        }
    }
}
