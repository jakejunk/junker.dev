package dev.junker.sudoku.controls

import dev.junker.sudoku.SudokuValue
import dev.junker.sudoku.toSudokuValue
import dev.junker.sudokuControls
import dev.junker.sudokuMarkingToggle
import dev.junker.sudokuNumpad
import dev.junker.sudokuPossibleValue
import kotlinx.html.InputType
import kotlinx.html.TagConsumer
import kotlinx.html.js.button
import kotlinx.html.js.div
import kotlinx.html.js.input
import kotlinx.html.js.label
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement

class SudokuControlsView private constructor(
    val root: HTMLElement,
    val possibleValues: List<HTMLButtonElement>,
    val markingToggle: HTMLInputElement
) {
    var onChangeMode: ((GameMode) -> Unit)? = null
    var onSetValue: ((SudokuValue) -> Unit)? = null

    init {
        possibleValues.forEach { button ->
            button.onclick = {
                val valueInt = button.getAttribute("data-value")
                    ?.toIntOrNull()
                    ?: 0

                onSetValue?.invoke(valueInt.toSudokuValue())
            }
        }

        markingToggle.onclick = {
            val mode = when (markingToggle.checked) {
                true -> GameMode.MARKING
                false -> GameMode.PLAYING
            }

            onChangeMode?.invoke(mode)
        }
    }

    companion object {
        fun TagConsumer<Element>.sudokuControlsView(): SudokuControlsView {
            val buttons: List<HTMLButtonElement>
            val markingToggle: HTMLInputElement
            val controls = div(classes = sudokuControls.className) {
                div(classes = sudokuNumpad.className) {
                    buttons = List(9) { i ->
                        val value = (i + 1).toSudokuValue()
                        button(classes = sudokuPossibleValue.className) {
                            val v = value.toString()
                            attributes["data-value"] = v
                            +v
                        }
                    }
                }

                label {
                    markingToggle = input(
                        classes = sudokuMarkingToggle.className,
                        name = "controls",
                        type = InputType.checkBox
                    )
                    +"Mark mode"
                }
            }

            return SudokuControlsView(controls, buttons, markingToggle)
        }
    }
}
