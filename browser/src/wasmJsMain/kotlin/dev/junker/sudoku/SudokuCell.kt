package dev.junker.sudoku

import dev.junker.BitField16

sealed interface SudokuCell {
    value class Empty private constructor(
        private val marks: BitField16
    ) : SudokuCell {
        companion object {
            val FULLY_EMPTY = Empty(BitField16())
        }

        fun isMarked(value: SudokuValue): Boolean {
            return marks.getBit(value.asInt)
        }

        fun mark(value: SudokuValue): Empty {
            val newBitField = marks.setBit(value.asInt, true)
            return Empty(newBitField)
        }

        fun unMark(value: SudokuValue): Empty {
            val newBitField = marks.setBit(value.asInt, false)
            return Empty(newBitField)
        }

        fun forEachMark(action: (value: SudokuValue, marked: Boolean) -> Unit) {
            repeat(9) { i ->
                val marked = marks.getBit(i)
                val value =  i.toSudokuValue()

                action(value, marked)
            }
        }
    }

    value class Filled(
        val value: SudokuValue
    ) : SudokuCell
}
