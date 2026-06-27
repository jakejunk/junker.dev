package dev.junker.maze.controls

import dev.junker.*
import kotlinx.html.TagConsumer
import kotlinx.html.js.button
import kotlinx.html.js.div
import kotlinx.html.js.label
import kotlinx.html.span
import org.w3c.dom.Element
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class MazeControlsView private constructor(
    val root: HTMLElement,
    val nextButton: HTMLButtonElement,
    val undoButton: HTMLButtonElement
) {
    var onUndo: (() -> Unit)? = null
    var onNextMaze: (() -> Unit)? = null

    init {
        nextButton.onclick = {
            onNextMaze?.invoke()
        }

        undoButton.onclick = {
            onUndo?.invoke()
        }
    }

    companion object {
        fun TagConsumer<Element>.mazeControlsView(): MazeControlsView {
            val controls: HTMLDivElement
            val nextButton: HTMLButtonElement
            val undoButton: HTMLButtonElement

            controls = div(classes = mazeControls.className) {
                div(classes = mazeActions.className) {
                    label {
                        nextButton = button(
                            classes = "${mazeAction.className} ${mazeActionNext.className}",
                            name = "controls"
                        )
                        span { +"Next" }
                    }

                    label {
                        undoButton = button(
                            classes = "${mazeAction.className} ${mazeActionUndo.className}",
                            name = "controls"
                        ) { disabled = true }
                        span { +"Undo" }
                    }
                }
            }

            return MazeControlsView(
                root = controls,
                nextButton = nextButton,
                undoButton = undoButton
            )
        }
    }
}
