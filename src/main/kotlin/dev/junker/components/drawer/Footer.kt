package dev.junker.components.drawer

import dev.junker.util.asClass
import kotlinx.html.*
import java.time.Year

val drawerFooter = "drawer-footer".asClass()
val statusIndicator = "totally-real-status-indicator".asClass()
val copyright = "copyright".asClass()

fun BODY.renderFooter() {
    footer(classes = drawerFooter.className) {
        span(classes = statusIndicator.className) { +"POWER" }
        small(classes = copyright.className) { +"©${Year.now()} Jake Junker" }
    }
}
