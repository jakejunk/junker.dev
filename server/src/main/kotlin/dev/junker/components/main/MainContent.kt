package dev.junker.components.main

import dev.junker.components.pages.Page
import dev.junker.util.asClass
import dev.junker.util.asId
import kotlinx.html.*

val mainContainer = "main-container".asClass()
val commandLine = "cli".asClass()
val main = "main".asId()
val mainContent = "main-content".asClass()

fun BODY.mainContent(page: Page) {
    div(classes = mainContainer.className) {
        div(classes = commandLine.className) {
            when (page) {
                is Page.Content -> +"view ${page.slug}"
                is Page.Error -> +""
            }
        }

        main {
            id = main.id

            article(classes = mainContent.className) {
                with(page) { content() }
            }
        }
    }
}
