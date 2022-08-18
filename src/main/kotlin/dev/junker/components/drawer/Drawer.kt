package dev.junker.components.drawer

import dev.junker.components.page.Page
import dev.junker.util.asClass
import kotlinx.html.BODY
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.tabIndex

val drawerContainer = "drawer-container".asClass()
val drawerBackground = "drawer-bg".asClass()
val drawer = "drawer".asClass()
val drawerButton = "drawer-button".asClass()

fun BODY.renderDrawer(page: Page) {
    div(classes = drawerContainer.className) {
        div(classes = drawerBackground.className)
        div(classes = drawer.className) {
            tabIndex = "0"
            button(classes = drawerButton.className)
            renderHeader(page)
        }
    }
}
