package dev.junker.components.drawer

import dev.junker.pages.Page
import dev.junker.classSelector
import kotlinx.html.BODY
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.tabIndex

val drawerContainer = "drawer-container".classSelector()
val drawerBackground = "drawer-bg".classSelector()
val drawer = "drawer".classSelector()
val drawerButton = "drawer-button".classSelector()

fun BODY.drawer(page: Page) {
    div(classes = drawerContainer.className) {
        div(classes = drawerBackground.className)
        div(classes = drawer.className) {
            tabIndex = "-1"
            button(classes = drawerButton.className)
            header(page)
        }
    }
}
