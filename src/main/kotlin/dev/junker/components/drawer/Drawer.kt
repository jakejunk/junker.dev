package dev.junker.components.drawer

import dev.junker.Page
import kotlinx.html.*

fun DIV.renderDrawer(page: Page) {
    div(classes = "drawer") {
        div(classes = "drawer-bg")
        div(classes = "drawer-contents") {
            tabIndex = "0"
            div(classes = "drawer-button") { tabIndex = "0" }
            renderHeader(page)
            renderFooter()
        }
    }
}
