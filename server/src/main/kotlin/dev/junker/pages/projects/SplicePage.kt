package dev.junker.pages.projects

import dev.junker.markdown.renderMarkdown
import dev.junker.pages.ProjectsPage
import dev.junker.spliceContainer
import dev.junker.splicePlaceholder
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.hr
import kotlinx.html.id

class SplicePage(
    override val slug: String
) : ProjectsPage {
    override val title = "Splice"
    override val description = "Play Splice!"
    override val isWip = true
    override val content: FlowContent.() -> Unit = {
        div {
            id = spliceContainer.id
            div {
                id = splicePlaceholder.id
                +"WASM must be enabled in order to play."
            }
        }

        hr()

        renderMarkdown("""
            ## Dev log
            
            - 4/29: Basic idea implemented. Still figuring out the flow of the game and what's "fun".
            - 5/1: Corrected cell arithmetic and added basic validation.
            - 5/4: Added visualization for grid operations.
              - ~~Need to decide if the overlap logic is worth keeping.~~ Overlap seems interesting enough.
            - 5/7: Added basic null and adjacency error styling.
              - For adjacency errors, find something more visually interesting.
        """.trimIndent())
    }
}
