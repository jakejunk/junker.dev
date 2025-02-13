package dev.junker.components.drawer

import dev.junker.pages.AboutPage
import dev.junker.pages.HomePage
import dev.junker.pages.NotesPage
import dev.junker.pages.Page
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
    listOf(NotesPage.ROOT_SLUG, AboutPage.slug).map { slug ->
        val navLinkClasses = when {
            currentPage is Page.Content && currentPage.slug.startsWith(slug) ->
                "${navLink.className} ${selected.className}"
            else -> navLink.className
        }

        a(href = slug, classes = navLinkClasses) { +slug }
    }
}
