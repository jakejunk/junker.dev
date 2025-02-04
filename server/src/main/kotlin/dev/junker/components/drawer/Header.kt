package dev.junker.components.drawer

import dev.junker.components.pages.AboutPage
import dev.junker.components.pages.HomePage
import dev.junker.components.pages.Page
import dev.junker.util.asClass
import kotlinx.html.*

val mainHeader = "main-header".asClass()
val headerNav = "header-nav".asClass()
val siteLogo = "site-logo".asClass()
val error = "error".asClass()
val navLink = "nav-link".asClass()
val selected = "selected".asClass()

fun DIV.header(page: Page) {
    header(classes = mainHeader.className) {
        siteLogo(page)
        nav(classes = headerNav.className) {
            navLinks(page)
        }
    }
}

private fun HEADER.siteLogo(page: Page) {
    a(href = HomePage.slug, classes = when (page) {
        is Page.Error -> "${siteLogo.className} ${error.className}"
        else -> siteLogo.className
    })
}

private fun NAV.navLinks(currentPage: Page) {
    listOf(HomePage, AboutPage).map {
        val navLinkClasses = when (it) {
            currentPage -> "${navLink.className} ${selected.className}"
            else -> navLink.className
        }

        a(href = it.slug, classes = navLinkClasses) { +it.name }
    }
}
