package dev.junker.components.drawer

import dev.junker.util.asClass
import kotlinx.html.DIV
import kotlinx.html.footer
import kotlinx.html.small
import kotlinx.html.span
import java.time.Year

val drawerFooter = "drawer-footer".asClass()
val statusIndicator = "totally-real-status-indicator".asClass()
val copyright = "copyright".asClass()

fun DIV.renderFooter() {
    footer(classes = drawerFooter.className) {
        span(classes = statusIndicator.className) { +"POWER" }
        small(classes = copyright.className) { +"©${Year.now()} Jake Junker" }
    }
}
