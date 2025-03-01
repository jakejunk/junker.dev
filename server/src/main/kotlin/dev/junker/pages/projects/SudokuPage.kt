package dev.junker.pages.projects

import dev.junker.sudokuContainer
import dev.junker.pages.ProjectsPage
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

        h2 { +"Dev log" }
        ul {
            li {
                +"3/1: Basic interface. Missing most mobile support but 80% of the way there."
                ul {
                    li {
                        +"Fonts don't scale correctly, probably need "
                        code { +"calc" }
                        +"."
                    }
                }
            }
        }
    }
}
