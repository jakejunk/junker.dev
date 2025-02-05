package dev.junker.components.main

import dev.junker.components.pages.Page
import dev.junker.util.asClass
import kotlinx.html.DIV
import kotlinx.html.div

val cliContainer = "cli-container".asClass()
val cli = "cli".asClass()


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
