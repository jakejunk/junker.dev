package dev.junker.components.drawer

import dev.junker.Page
import kotlinx.html.*

fun DIV.renderHeader(page: Page) {
    val logoClasses = when (page) {
        is Page.Error -> "site-logo-image error"
        else -> "site-logo-image"
    }

    header(classes = "drawer-header") {
        div(classes = logoClasses)
        nav(classes = "header-nav") {
            renderNavLinks(page)
        }
    }
}

private fun NAV.renderNavLinks(currentPage: Page) {
    listOf(Page.Home, Page.About).map {
        val navLinkClasses = when (it) {
            currentPage -> "nav-link selected"
            else -> "nav-link"
        }

        a(href = it.href, classes = navLinkClasses) { +it.name }
    }
}
