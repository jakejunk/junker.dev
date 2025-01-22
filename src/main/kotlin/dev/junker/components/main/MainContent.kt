package dev.junker.components.main

import dev.junker.components.pages.Page
import dev.junker.util.asClass
import dev.junker.util.asId
import kotlinx.html.*

val mainContainer = "main-container".asClass()
val mainContent = "main".asId()
val terminalPrompt = "terminal-prompt".asClass()
val outputContainer = "output-container".asClass()
val output = "output".asClass()

fun BODY.mainContent(page: Page) {
    div(classes = mainContainer.className) {
        main {
            id = mainContent.id

            div(classes = terminalPrompt.className) {
                when (page) {
                    is Page.Content -> +"view ${page.slug}"
                    is Page.Error -> +""
                }
            }

            div(classes = outputContainer.className) {
                article(classes = output.className) {
                    with(page) { content() }
                }
            }
        }
    }
}
