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
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement

class SudokuControlsView private constructor(
    val root: HTMLElement,
    val possibleValues: List<HTMLButtonElement>,
    val markingToggle: HTMLInputElement,
    val preciseMarkingToggle: HTMLInputElement
) {
    var onSetValue: ((SudokuValue) -> Unit)? = null
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

        markingToggle.onclick = {
            onMarkingToggled?.invoke(markingEnabled)
        }

        preciseMarkingToggle.onclick = {
            onPreciseMarkingToggled?.invoke(preciseMarkingEnabled)
        }
    }

    companion object {
        fun TagConsumer<Element>.sudokuControlsView(): SudokuControlsView {
            val buttons: List<HTMLButtonElement>
            val markingToggle: HTMLInputElement
            val preciseMarkToggle: HTMLInputElement
            val controls = div(classes = sudokuControls.className) {
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

                div(classes = sudokuToggles.className) {
                    label {
                        markingToggle = input(
                            classes = sudokuMarkingToggle.className,
                            name = "controls",
                            type = InputType.checkBox
                        )
                        +"Mark mode"
                    }
                    label {
                        preciseMarkToggle = input(
                            classes = sudokuPreciseMarkingToggle.className,
                            name = "controls",
                            type = InputType.checkBox
                        )
                        +"Precise marking"
                    }
                }
            }

            return SudokuControlsView(controls, buttons, markingToggle, preciseMarkToggle)
        }
    }
}
