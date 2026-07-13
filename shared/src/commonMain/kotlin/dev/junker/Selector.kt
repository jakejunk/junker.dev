package dev.junker

import kotlin.jvm.JvmInline

// Maze =======================================================================
val mazeContainer = "maze-container".idSelector()
val mazePlaceholder = "maze-placeholder".idSelector()
val maze = "maze".classSelector()
val mazeSidePane = "maze-side-pane".classSelector()
val mazeGrid = "maze-grid".classSelector()
val mazeCell = "maze-cell".classSelector()

val mazeNorthWall = "maze-north-wall".classSelector()
val mazeSouthWall = "maze-south-wall".classSelector()
val mazeEastWall = "maze-east-wall".classSelector()
val mazeWestWall = "maze-west-wall".classSelector()

val mazeStart = "maze-start".classSelector()
val mazeEnd = "maze-end".classSelector()
val mazeVisited = "maze-visited".classSelector()

// Controls
val mazeControls = "maze-controls".classSelector()
val mazeActions = "maze-actions".classSelector()
val mazeAction = "maze-action".classSelector()
val mazeActionNext = "maze-action-next".classSelector()
val mazeActionRewind = "maze-action-rewind".classSelector()

// Splice =====================================================================
// Grid
val spliceContainer = "splice-container".idSelector()
val splicePlaceholder = "splice-placeholder".idSelector()
val splice = "splice".classSelector()
val spliceGrid = "splice-grid".classSelector()
val spliceCell = "splice-cell".classSelector()

// States
val spliceSelected = "splice-selected".classSelector()
val spliceLhsVertical = "splice-lhs-v".classSelector()
val spliceLhsHorizontal = "splice-lhs-h".classSelector()
val spliceRhsVertical = "splice-rhs-v".classSelector()
val spliceRhsHorizontal = "splice-rhs-h".classSelector()
val spliceResultVertical = "splice-result-v".classSelector()
val spliceResultHorizontal = "splice-result-h".classSelector()
val spliceNull = "splice-null".classSelector()
val spliceSkip = "splice-skip".classSelector()
val spliceJump = "splice-jump".classSelector()
val spliceJumpTarget = "splice-jump-target".classSelector()
val spliceOutOfRange = "splice-oor".classSelector()

// Controls
val spliceControls = "splice-controls".classSelector()
val spliceOperations = "splice-operations".classSelector()
val splicePossibleOperation = "splice-possible-operation".classSelector()
val spliceActions = "splice-actions".classSelector()
val spliceAction = "splice-action".classSelector()
val spliceActionErase = "splice-action-erase".classSelector()
val spliceActionUndo = "splice-action-undo".classSelector()

// Sudoku =====================================================================
// Grid
val sudokuContainer = "sudoku-container".idSelector()
val sudokuPlaceholder = "sudoku-placeholder".idSelector()
val sudoku = "sudoku".classSelector()
val sudokuGrid = "sudoku-grid".classSelector()
val sudokuBox = "sudoku-box".classSelector()
val sudokuCell = "sudoku-cell".classSelector()
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
val sudokuActions = "sudoku-actions".classSelector()
val sudokuAction = "sudoku-action".classSelector()
val sudokuActionMark = "sudoku-action-mark".classSelector()
val sudokuActionErase = "sudoku-action-erase".classSelector()
val sudokuActionUndo = "sudoku-action-undo".classSelector()
val sudokuToggles = "sudoku-toggles".classSelector()

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
