package dev.junker.components.main

import dev.junker.pages.Page
import dev.junker.classSelector
import dev.junker.idSelector
import kotlinx.html.*

val workInProgress = "wip".classSelector()
val mainContainer = "main-container".classSelector()
val main = "main".idSelector()
val mainContent = "main-content".classSelector()

fun BODY.mainContent(page: Page) {
    val wipClass = if (page.isWip) workInProgress.className else ""

    div(classes = mainContainer.className) {
        commandLine(page)

        main(classes = wipClass) {
            id = main.id

            article(classes = mainContent.className) {
                with(page) { content() }
            }
        }
    }
}
