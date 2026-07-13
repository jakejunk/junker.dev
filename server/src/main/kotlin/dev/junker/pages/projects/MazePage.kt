package dev.junker.pages.projects

import dev.junker.markdown.renderMarkdown
import dev.junker.mazeContainer
import dev.junker.mazePlaceholder
import dev.junker.pages.ProjectsPage
import dev.junker.spliceContainer
import dev.junker.splicePlaceholder
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.hr
import kotlinx.html.id

class MazePage(
    override val slug: String
) : ProjectsPage {
    override val title = "Maze"
    override val description = "Get lost!"
    override val isWip = true
    override val content: FlowContent.() -> Unit = {
        div {
            id = mazeContainer.id
            div {
                id = mazePlaceholder.id
                +"WASM must be enabled in order to play."
            }
        }

        hr()

        renderMarkdown("""
            ## Dev log
            
            - 6/24: Basic idea implemented.
            - 6/26: "Next" button for endless mazes!
            - 7/3: Marked start and end points for added difficulty.
              - ~~Need to add a visual trail for "walking" through a maze.~~
            - 7/7: Keyboard navigation (arrow keys) + trail added.
              - ~~Add visualization for current position.~~
              - ~~Refactor "Undo" button into a rewind button.~~
              - ~~Update key press throttler behavior to avoid punishing fast key tappers.~~
            - 7/8: Maze start point repurposed as current position; clicking the rewind button takes 3 steps back.
              - Input throttling has been removed entirely for now.
              - Hitting backspace will also kick off a rewind.
            - 7/12: Added basic stats (steps + rewound steps).
              - Needs a timer and some styling.
        """.trimIndent())
    }
}
