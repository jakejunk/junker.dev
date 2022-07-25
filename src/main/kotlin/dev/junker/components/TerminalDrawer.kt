package dev.junker.components

import dev.junker.Page
import kotlinx.html.*

fun DIV.renderTerminalDrawer(page: Page) {
    div(classes = "terminal-drawer") {
        div(classes = "terminal-drawer-bg")
        div(classes = "terminal-drawer-contents") {
            tabIndex = "0"
            div(classes = "terminal-drawer-button") { tabIndex = "0" }
            renderTerminalHeader(page)
            renderTerminalFooter()
        }
    }
}
