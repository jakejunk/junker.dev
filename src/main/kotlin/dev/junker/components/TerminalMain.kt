package dev.junker.components

import dev.junker.Page
import dev.junker.util.asClass
import kotlinx.html.DIV
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.main

val terminalMain = "terminal-main".asClass()
val terminalPrompt = "terminal-prompt".asClass()
val terminalOutput = "terminal-output".asClass()

fun DIV.renderTerminalMain(page: Page) {
    div(classes = terminalMain.className) {
        main {
            id = "main"

            div(classes = terminalPrompt.className) {
                when (page) {
                    is Page.Content -> +"view ${page.href}"
                    is Page.Error -> +""
                }
            }

            div(classes = terminalOutput.className) {
                with(page) { block() }
            }
        }
    }
}
