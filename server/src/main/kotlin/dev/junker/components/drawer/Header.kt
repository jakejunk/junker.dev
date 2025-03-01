package dev.junker.components.drawer

import dev.junker.classSelector
import dev.junker.pages.*
import kotlinx.html.*

val mainHeader = "main-header".classSelector()
val headerNav = "header-nav".classSelector()
val siteLogo = "site-logo".classSelector()
val error = "error".classSelector()
val navLink = "nav-link".classSelector()
val selected = "selected".classSelector()

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
    listOf(ProjectsPage.ROOT_SLUG, NotesPage.ROOT_SLUG, AboutPage.slug).map { slug ->
        val navLinkClasses = when {
            currentPage is Page.Content && currentPage.slug.startsWith(slug) ->
                "${navLink.className} ${selected.className}"
            else -> navLink.className
        }

        a(href = slug, classes = navLinkClasses) { +slug }
    }
}
