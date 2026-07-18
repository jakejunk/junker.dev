package dev.junker.maze.stats

import dev.junker.mazeStats
import dev.junker.mazeSteps
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class MazeStatsView private constructor(
    val root: HTMLElement,
    val stepElement: HTMLElement
) {
    private var steps = 0
        set(value) {
            stepElement.textContent = value.toString()
            field = value
        }

    fun recordStep() {
        steps += 1
    }

    fun reset() {
        steps = 0
    }

    companion object {
        fun TagConsumer<Element>.mazeStatsView(): MazeStatsView {
            val stats: HTMLDivElement
            val steps: HTMLDivElement

            stats = div(classes = mazeStats.className) {
                steps = div(classes = mazeSteps.className) {
                    +"0"
                }
            }

            return MazeStatsView(
                root = stats,
                stepElement = steps
            )
        }
    }
}
