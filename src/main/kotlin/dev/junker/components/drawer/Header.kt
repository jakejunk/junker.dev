package dev.junker.components.drawer

import dev.junker.components.page.AboutPage
import dev.junker.components.page.HomePage
import dev.junker.components.page.Page
import dev.junker.util.asClass
import kotlinx.html.*

val mainHeader = "main-header".asClass()
val headerNav = "header-nav".asClass()
val siteLogo = "site-logo".asClass()
val error = "error".asClass()
val navLink = "nav-link".asClass()
val selected = "selected".asClass()

fun DIV.renderHeader(page: Page) {
    header(classes = mainHeader.className) {
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
    listOf(HomePage, AboutPage).map {
        val navLinkClasses = when (it) {
            currentPage -> "${navLink.className} ${selected.className}"
            else -> navLink.className
        }

        a(href = it.slug, classes = navLinkClasses) { +it.name }
    }
}
