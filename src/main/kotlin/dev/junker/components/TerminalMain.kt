package dev.junker.components

import dev.junker.Page
import kotlinx.html.DIV
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.main

fun DIV.renderTerminalMain(page: Page) {
    div(classes = "terminal-main") {
        main {
            id = "main"

            div("terminal-prompt") {
                when (page) {
                    is Page.Content -> +"view ${page.href}"
                    is Page.Error -> +""
                }
            }

            div("terminal-output") {
                with(page) { block() }
            }
        }
    }
}
