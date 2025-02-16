package dev.junker.components.main

import dev.junker.pages.Page
import dev.junker.util.classSelector
import dev.junker.util.idSelector
import kotlinx.html.*

val mainContainer = "main-container".classSelector()
val main = "main".idSelector()
val mainContent = "main-content".classSelector()

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
