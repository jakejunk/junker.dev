package dev.junker.components.drawer

import kotlinx.html.DIV
import kotlinx.html.footer
import kotlinx.html.span
import java.time.Year

fun DIV.renderFooter() {
    footer(classes = "drawer-footer") {
        span("totally-real-status-indicator") { +"POWER" }
        span("copyright") { +"©${Year.now()} Jake Junker" }
    }
}
