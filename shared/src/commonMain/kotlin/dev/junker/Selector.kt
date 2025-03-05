package dev.junker

import kotlin.jvm.JvmInline

// Sudoku =====================================================================
// Grid
val sudokuContainer = "sudoku-container".idSelector()
val sudoku = "sudoku".classSelector()
val sudokuGrid = "sudoku-grid".classSelector()
val sudokuBox = "sudoku-box".classSelector()
val sudokuCell = "sudoku-cell".classSelector()
val sudokuCellValue = "sudoku-cell-value".classSelector()
val sudokuCellMark = "sudoku-cell-mark".classSelector()
val sudokuCellMarks = "sudoku-cell-marks".classSelector()

// States
val sudokuMarking = "sudoku-marking".classSelector()
val sudokuPreciseMarking = "sudoku-precise-marking".classSelector()
val sudokuMarked = "sudoku-marked".classSelector()
val sudokuSelected = "sudoku-selected".classSelector()

// Controls
val sudokuControls = "sudoku-controls".classSelector()
val sudokuNumpad = "sudoku-numpad".classSelector()
val sudokuPossibleValue = "sudoku-possible-value".classSelector()
val sudokuToggles = "sudoku-toggles".classSelector()
val sudokuMarkingToggle = "sudoku-marking-toggle".classSelector()
val sudokuPreciseMarkingToggle = "sudoku-precise-marking-toggle".classSelector()

// Syntax highlighting ========================================================
val annotation = "annotation".classSelector()
val keyword = "keyword".classSelector()
val string = "string".classSelector()
val literal = "literal".classSelector()
val comment = "comment".classSelector()

// ============================================================================

sealed interface Selector {
    val selector: String

    @JvmInline
    value class Class(
        val className: String
    ) : Selector {
        override val selector get() = ".$className"
    }

    @JvmInline
    value class Id(
        val id: String
    ) : Selector {
        override val selector get() = "#$id"
    }
}

fun String.classSelector() = Selector.Class(this)

fun String.idSelector() = Selector.Id(this)
