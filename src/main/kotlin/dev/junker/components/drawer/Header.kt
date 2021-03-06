package dev.junker.components.drawer

import dev.junker.Page
import dev.junker.util.asClass
import kotlinx.html.*

val drawerHeader = "drawer-header".asClass()
val headerNav = "header-nav".asClass()
val siteLogo = "site-logo".asClass()
val error = "error".asClass()
val navLink = "nav-link".asClass()
val selected = "selected".asClass()

fun DIV.renderHeader(page: Page) {
    header(classes = drawerHeader.className) {
        renderSiteLogo(page)
        nav(classes = headerNav.className) {
            renderNavLinks(page)
        }
    }
}

private fun HEADER.renderSiteLogo(page: Page) {
    div(classes = when (page) {
        is Page.Error -> "${siteLogo.className} ${error.className}"
        else -> siteLogo.className
    })
}

private fun NAV.renderNavLinks(currentPage: Page) {
    listOf(Page.Home, Page.About).map {
        val navLinkClasses = when (it) {
            currentPage -> "${navLink.className} ${selected.className}"
            else -> navLink.className
        }

        a(href = it.href, classes = navLinkClasses) { +it.name }
    }
}
