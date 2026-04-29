package dev.junker.pages.projects

import dev.junker.markdown.renderMarkdown
import dev.junker.pages.ProjectsPage
import dev.junker.spliceContainer
import dev.junker.splicePlaceholder
import dev.junker.sudokuContainer
import dev.junker.sudokuPlaceholder
import kotlinx.html.*

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
        """.trimIndent())
    }
}
