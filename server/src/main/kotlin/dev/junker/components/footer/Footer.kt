package dev.junker.components.footer

import dev.junker.classSelector
import kotlinx.html.*
import java.time.Year

val mainFooter = "main-footer".classSelector()
val footerContent = "footer-content".classSelector()
val statusIndicator = "totally-real-status-indicator".classSelector()
val copyright = "copyright".classSelector()

fun BODY.footer() {
    footer(classes = mainFooter.className) {
        div(classes = footerContent.className) {
            small(classes = copyright.className) { +"©${Year.now()} Jake Junker" }
            small(classes = statusIndicator.className)
        }
    }
}
