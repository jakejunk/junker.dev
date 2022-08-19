package dev.junker.components.main

import dev.junker.components.page.Page
import dev.junker.components.page.renderPage
import dev.junker.util.asClass
import dev.junker.util.asId
import kotlinx.html.*

val mainContentContainer = "main-content-container".asClass()
val mainContent = "main".asId()
val terminalPrompt = "terminal-prompt".asClass()
val terminalOutputContainer = "terminal-output-container".asClass()
val terminalOutput = "terminal-output".asClass()

fun BODY.renderMainContent(page: Page) {
    div(classes = mainContentContainer.className) {
        main {
            id = mainContent.id

            div(classes = terminalPrompt.className) {
                when (page) {
                    is Page.Content -> +"view ${page.slug}"
                    is Page.Error -> +""
                }
            }

            div(classes = terminalOutputContainer.className) {
                article(classes = terminalOutput.className) {
                    renderPage(page)
                }
            }
        }
    }
}
