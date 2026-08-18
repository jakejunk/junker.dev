package dev.junker.maze.stats

import dev.junker.mazeCollectedTreasure
import dev.junker.mazeCollectedTreasurePlaceholder
import dev.junker.mazeCollectedTreasures
import dev.junker.mazeStats
import dev.junker.mazeSteps
import kotlinx.dom.appendElement
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

class MazeStatsView private constructor(
    val root: HTMLElement,
    val stepElement: HTMLElement,
    val collectedTreasuresElement: HTMLDivElement,
) {
    companion object {
        fun TagConsumer<Element>.mazeStatsView(): MazeStatsView {
            val stats: HTMLDivElement
            val steps: HTMLDivElement
            val collectedTreasures: HTMLDivElement

            stats = div(classes = mazeStats.className) {
                steps = div(classes = mazeSteps.className) {
                    +"0"
                }

                collectedTreasures = div(classes = mazeCollectedTreasures.className)
            }

            return MazeStatsView(
                root = stats,
                stepElement = steps,
                collectedTreasuresElement = collectedTreasures,
            )
        }
    }

    var steps = 0
        private set

    var treasures = 0
        private set

    fun recordStep() {
        steps += 1

        stepElement.textContent = steps.toString()
    }

    fun collectTreasure() {
        collectedTreasuresElement
            .querySelector(mazeCollectedTreasurePlaceholder.selector)
            ?.remove()

        collectedTreasuresElement.appendElement("div") {
            className = mazeCollectedTreasure.className
        }

        treasures += 1
    }

    fun reset(numTreasures: Int) {
        steps = 0
        collectedTreasuresElement.innerHTML = ""

        repeat(numTreasures) {
            collectedTreasuresElement.appendElement("div") {
                className = mazeCollectedTreasurePlaceholder.className
            }
        }
    }
}
