package dev.junker.components.drawer

import dev.junker.Page
import dev.junker.util.asClass
import kotlinx.html.*

val drawerContainer = "drawer-container".asClass()
val drawerBackground = "drawer-bg".asClass()
val drawer = "drawer".asClass()
val drawerButton = "drawer-button".asClass()

fun BODY.renderDrawer(page: Page) {
    div(classes = drawerContainer.className) {
        div(classes = drawerBackground.className)
        div(classes = drawer.className) {
            tabIndex = "0"
            div(classes = drawerButton.className) { tabIndex = "0" }
            renderHeader(page)
        }
    }
}
