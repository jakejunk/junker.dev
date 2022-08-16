package dev.junker.components

import dev.junker.Page
import dev.junker.util.asClass
import kotlinx.html.*

val terminalMain = "terminal-main".asClass()
val terminalPrompt = "terminal-prompt".asClass()
val terminalOutput = "terminal-output".asClass()
val terminalOutputContent = "terminal-output-content".asClass()

fun BODY.renderTerminalMain(page: Page) {
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
                div(classes = terminalOutputContent.className) {
                    with(page) { block() }
                }
            }
        }
    }
}
