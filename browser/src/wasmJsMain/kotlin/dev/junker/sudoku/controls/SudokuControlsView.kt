package dev.junker.sudoku.controls

import dev.junker.*
import dev.junker.sudoku.SudokuValue
import dev.junker.sudoku.toSudokuValue
import kotlinx.html.InputType
import kotlinx.html.TagConsumer
import kotlinx.html.js.button
import kotlinx.html.js.div
import kotlinx.html.js.input
import kotlinx.html.js.label
import kotlinx.html.span
import org.w3c.dom.*

class SudokuControlsView private constructor(
    val root: HTMLElement,
    val possibleValues: List<HTMLButtonElement>,
    val undoButton: HTMLButtonElement,
    val markingToggle: HTMLInputElement,
    val eraseButton: HTMLButtonElement,
    val preciseMarkingToggle: HTMLInputElement
) {
    var onUndo: (() -> Unit)? = null
    var onSetValue: ((SudokuValue) -> Unit)? = null
    var onEraseValue: (() -> Unit)? = null
    var onMarkingToggled: ((Boolean) -> Unit)? = null
    var onPreciseMarkingToggled: ((Boolean) -> Unit)? = null

    val markingEnabled: Boolean
        get() = markingToggle.checked

    val preciseMarkingEnabled: Boolean
        get() = preciseMarkingToggle.checked

    init {
        possibleValues.forEach { button ->
            button.onclick = {
                val valueInt = button.getAttribute("data-value")
                    ?.toIntOrNull()
                    ?: 0

                onSetValue?.invoke(valueInt.toSudokuValue())
            }
        }

        undoButton.onclick = {
            onUndo?.invoke()
        }

        markingToggle.onclick = {
            onMarkingToggled?.invoke(markingEnabled)
        }

        eraseButton.onclick = {
            onEraseValue?.invoke()
        }

        preciseMarkingToggle.onclick = {
            onPreciseMarkingToggled?.invoke(preciseMarkingEnabled)
        }
    }

    companion object {
        fun TagConsumer<Element>.sudokuControlsView(): SudokuControlsView {
            val controls: HTMLDivElement
            val buttons: List<HTMLButtonElement>
            val markingToggle: HTMLInputElement
            val eraseButton: HTMLButtonElement
            val undoButton: HTMLButtonElement
            val preciseMarkToggle: HTMLInputElement

            controls = div(classes = sudokuControls.className) {
                div(classes = sudokuNumpad.className) {
                    buttons = List(9) { i ->
                        val value = (i + 1).toSudokuValue()
                        button(classes = sudokuPossibleValue.className) {
                            val v = value.toString()
                            attributes["data-value"] = v
                            span { +v }
                        }
                    }
                }

                div(classes = sudokuActions.className) {
                    label {
                        undoButton = button(
                            classes = "${sudokuAction.className} ${sudokuActionUndo.className}",
                            name = "controls"
                        )
                        span { +"Undo" }
                    }

                    label {
                        markingToggle = input(
                            classes = "${sudokuAction.className} ${sudokuActionMark.className}",
                            name = "controls",
                            type = InputType.checkBox
                        )
                        span { +"Mark" }
                    }

                    label {
                        eraseButton = button(
                            classes = "${sudokuAction.className} ${sudokuActionErase.className}",
                            name = "controls"
                        )
                        span { +"Erase" }
                    }
                }

                div(classes = sudokuToggles.className) {
                    label {
                        preciseMarkToggle = input(
                            classes = sudokuAction.className,
                            name = "controls",
                            type = InputType.checkBox
                        )
                        +"Precise marking"
                    }
                }
            }

            return SudokuControlsView(
                root = controls,
                possibleValues = buttons,
                undoButton = undoButton,
                markingToggle = markingToggle,
                eraseButton = eraseButton,
                preciseMarkingToggle = preciseMarkToggle
            )
        }
    }
}
