package dev.junker.splice.controls

import dev.junker.*
import dev.junker.splice.Direction
import dev.junker.splice.SpliceOperator
import dev.junker.splice.parseSpliceOperator
import dev.junker.splice.tryParseDirection
import kotlinx.html.TagConsumer
import kotlinx.html.js.button
import kotlinx.html.js.div
import kotlinx.html.js.label
import kotlinx.html.span
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class SpliceControlsView private constructor(
    val root: HTMLElement,
    val possibleValues: List<HTMLButtonElement>,
    val undoButton: HTMLButtonElement,
    val eraseButton: HTMLButtonElement
) {
    var onUndo: (() -> Unit)? = null
    var onSetValue: ((SpliceOperator) -> Unit)? = null
    var onEraseOperator: (() -> Unit)? = null

    init {
        possibleValues.forEach { button ->
            button.onclick = {
                val direction = button.getAttribute(DIRECTION_ATTRIBUTE)
                    ?.tryParseDirection()
                    ?: Direction.HORIZONTAL

                button.getAttribute(OPERATION_ATTRIBUTE)
                    ?.let { parseSpliceOperator(it, direction) }
                    ?.also { onSetValue?.invoke(it) }
            }
        }

        undoButton.onclick = {
            onUndo?.invoke()
        }

        eraseButton.onclick = {
            onEraseOperator?.invoke()
        }
    }

    companion object {
        const val OPERATION_ATTRIBUTE = "data-operation"
        const val DIRECTION_ATTRIBUTE = "data-direction"

        fun TagConsumer<Element>.spliceControlsView(): SpliceControlsView {
            val controls: HTMLDivElement
            val buttons: List<HTMLButtonElement>
            val eraseButton: HTMLButtonElement
            val undoButton: HTMLButtonElement

            controls = div(classes = spliceControls.className) {
                div(classes = spliceOperations.className) {
                    val tempOps = listOf(
                        SpliceOperator.Add(Direction.HORIZONTAL),
                        SpliceOperator.Subtract(Direction.HORIZONTAL),
                        SpliceOperator.Multiply(Direction.HORIZONTAL),
                        SpliceOperator.Divide(Direction.HORIZONTAL)
                    )

                    buttons = tempOps.map { op ->
                        button(classes = splicePossibleOperation.className) {
                            val v = op.toString()
                            attributes[OPERATION_ATTRIBUTE] = v
                            attributes[DIRECTION_ATTRIBUTE] = op.direction.toString()
                            span { +v }
                        }
                    }
                }

                div(classes = spliceActions.className) {
                    label {
                        undoButton = button(
                            classes = "${spliceAction.className} ${spliceActionUndo.className}",
                            name = "controls"
                        )
                        span { +"Undo" }
                    }

                    label {
                        eraseButton = button(
                            classes = "${spliceAction.className} ${spliceActionErase.className}",
                            name = "controls"
                        )
                        span { +"Erase" }
                    }
                }
            }

            return SpliceControlsView(
                root = controls,
                possibleValues = buttons,
                undoButton = undoButton,
                eraseButton = eraseButton
            )
        }
    }
}
