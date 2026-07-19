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
    val rewindButton: HTMLButtonElement
) {
    var onNextMaze: (() -> Unit)? = null
    var onRewind: (() -> Unit)? = null

    init {
        nextButton.onclick = {
            onNextMaze?.invoke()
        }

        rewindButton.onclick = {
            onRewind?.invoke()
        }
    }

    companion object {
        fun TagConsumer<Element>.mazeControlsView(): MazeControlsView {
            val controls: HTMLDivElement
            val nextButton: HTMLButtonElement
            val rewindButton: HTMLButtonElement

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
                        rewindButton = button(
                            classes = "${mazeAction.className} ${mazeActionRewind.className}",
                            name = "controls"
                        )
                        span { +"Rewind" }
                    }
                }
            }

            return MazeControlsView(
                root = controls,
                nextButton = nextButton,
                rewindButton = rewindButton
            )
        }
    }
}
