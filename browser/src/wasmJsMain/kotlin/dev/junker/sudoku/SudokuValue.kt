package dev.junker.sudoku

sealed class SudokuValue(
    val asInt: Int = 0
) {
    data object One : SudokuValue(1)
    data object Two : SudokuValue(2)
    data object Three : SudokuValue(3)
    data object Four : SudokuValue(4)
    data object Five : SudokuValue(5)
    data object Six : SudokuValue(6)
    data object Seven : SudokuValue(7)
    data object Eight : SudokuValue(8)
    data object Nine : SudokuValue(9)

    final override fun toString() = asInt.toString()
}

fun Int.toSudokuValue(): SudokuValue {
    return when (this) {
        1 -> SudokuValue.One
        2 -> SudokuValue.Two
        3 -> SudokuValue.Three
        4 -> SudokuValue.Four
        5 -> SudokuValue.Five
        6 -> SudokuValue.Six
        7 -> SudokuValue.Seven
        8 -> SudokuValue.Eight
        else -> SudokuValue.Nine
    }
}

inline fun forEachSudokuValue(crossinline action: (SudokuValue) -> Unit) {
    repeat(9) { i -> action((i + 1).toSudokuValue()) }
}
