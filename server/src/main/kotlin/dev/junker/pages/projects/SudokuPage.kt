package dev.junker.pages.projects

import dev.junker.markdown.renderMarkdown
import dev.junker.pages.ProjectsPage
import dev.junker.sudokuContainer
import kotlinx.html.*

class SudokuPage(
    override val slug: String
) : ProjectsPage {
    override val title = "Sudoku"
    override val description = "Play Sudoku!"
    override val isWip = true
    override val content: FlowContent.() -> Unit = {
        div {
            id = sudokuContainer.id
            noScript {
                +"WASM must be enabled in order to play."
            }
        }

        hr()

        renderMarkdown("""
            ## Dev log
            
            - 3/1: Basic interface. Missing most mobile support but 80% of the way there.
              - ~~Fonts don't scale correctly, probably need `calc`.~~
            - 3/2: Fixed font scaling. Apparently `calc` is old school, the new hotness is
            [container query length units](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_containment/Container_queries#container_query_length_units).
              - ~~Need better wrapping behavior for the numpad;
              not great to use on mobile currently.~~
              - ~~Look into better input method for marks;
              mobile users can't be expected to precisely click individual sections of a cell.~~
              - Start thinking about a basic solver.
            - 3/3: Adjusted numpad behavior when wrapping on mobile.
            - 3/4: Adjusted mark font scaling and added a marking mode better suited for mobile.
              - The controls UI needs some polish.
              - Need a better warning message for browsers without proper WASM support.
        """.trimIndent())
    }
}
