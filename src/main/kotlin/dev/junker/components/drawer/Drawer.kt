package dev.junker.components.drawer

import dev.junker.Page
import dev.junker.util.asClass
import kotlinx.html.*

val drawer = "drawer".asClass()
val drawerBackground = "drawer-bg".asClass()
val drawerContents = "drawer-contents".asClass()
val drawerButton = "drawer-button".asClass()

fun DIV.renderDrawer(page: Page) {
    div(classes = drawer.className) {
        div(classes = drawerBackground.className)
        div(classes = drawerContents.className) {
            tabIndex = "0"
            div(classes = drawerButton.className) { tabIndex = "0" }
            renderHeader(page)
            renderFooter()
        }
    }
}
