package dev.junker.components.footer

import dev.junker.util.asClass
import kotlinx.html.*
import java.time.Year

val mainFooter = "main-footer".asClass()
val footerContent = "footer-content".asClass()
val statusIndicator = "totally-real-status-indicator".asClass()
val copyright = "copyright".asClass()

fun BODY.footer() {
    footer(classes = mainFooter.className) {
        div(classes = footerContent.className) {
            small(classes = copyright.className) { +"©${Year.now()} Jake Junker" }
            small(classes = statusIndicator.className)
        }
    }
}
