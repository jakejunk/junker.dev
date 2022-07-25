package dev.junker.components

import kotlinx.html.DIV
import kotlinx.html.footer
import kotlinx.html.span
import java.time.Year

fun DIV.renderTerminalFooter() {
    footer(classes = "terminal-footer") {
        span("totally-real-status-indicator") { +"POWER" }
        span("copyright") { +"©${Year.now()} Jake Junker" }
    }
}
