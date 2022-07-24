package dev.junker.components

import dev.junker.Page
import kotlinx.html.*

fun DIV.renderTerminalHeader(page: Page) {
    val logoClasses = when (page) {
        is Page.Error -> "site-logo-image error"
        else -> "site-logo-image"
    }

    header(classes = "terminal-header") {
        div(classes = logoClasses)
        nav(classes = "terminal-nav") {
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
