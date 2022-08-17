package dev.junker.components.main

import dev.junker.Page
import dev.junker.util.asClass
import kotlinx.html.*

val mainContentContainer = "main-content-container".asClass()
val mainContent = "main-content".asClass()
val terminalPrompt = "terminal-prompt".asClass()
val terminalOutputContainer = "terminal-output-container".asClass()
val terminalOutput = "terminal-output".asClass()

fun BODY.renderMainContent(page: Page) {
    div(classes = mainContentContainer.className) {
        main(classes = mainContent.className) {
            id = "main"

            div(classes = terminalPrompt.className) {
                when (page) {
                    is Page.Content -> +"view ${page.slug}"
                    is Page.Error -> +""
                }
            }

            div(classes = terminalOutputContainer.className) {
                article(classes = terminalOutput.className) {
                    with(page) { block() }
                }
            }
        }
    }
}
