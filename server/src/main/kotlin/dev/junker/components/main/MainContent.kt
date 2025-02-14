package dev.junker.components.main

import dev.junker.pages.Page
import dev.junker.util.asClass
import dev.junker.util.asId
import kotlinx.html.*

val mainContainer = "main-container".asClass()
val main = "main".asId()
val mainContent = "main-content".asClass()

fun BODY.mainContent(page: Page) {
    div(classes = mainContainer.className) {
        commandLine(page)

        main {
            id = main.id

            article(classes = mainContent.className) {
                with(page) { content() }
            }
        }
    }
}
