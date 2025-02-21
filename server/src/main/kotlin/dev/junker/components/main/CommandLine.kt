package dev.junker.components.main

import dev.junker.pages.Page
import dev.junker.classSelector
import kotlinx.html.DIV
import kotlinx.html.div

val cliContainer = "cli-container".classSelector()
val cli = "cli".classSelector()


fun DIV.commandLine(page: Page) {
    div(classes = cliContainer.className) {
        div(classes = cli.className) {
            when (page) {
                is Page.Content -> +"view ${page.slug}"
                is Page.Error -> +""
            }
        }
    }
}
