package dev.junker.pages

import dev.junker.pages.projects.SudokuPage
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.article
import kotlinx.html.h1

interface ProjectsPage : Page.Content {
    companion object {
        const val ROOT_SLUG = "/projects"
    }

    class Index : ProjectsPage {
        override val slug = ROOT_SLUG
        override val title = "Projects"
        override val description = "Watch your step."
        override val isWip = true
        override val content: FlowContent.() -> Unit = {
            h1 { +"Projects" }

            // FIXME
            article {
                a(href = "$ROOT_SLUG/sudoku") { +"Sudoku" }
            }
        }
    }
}

fun projectPage(projectName: String): ProjectsPage? {
    val projectNameFq = "${ProjectsPage.ROOT_SLUG}/$projectName"

    return when (projectName) {
        "sudoku" -> SudokuPage(projectNameFq)
        else -> null
    }
}
