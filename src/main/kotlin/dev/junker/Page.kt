package dev.junker

import io.ktor.http.*
import kotlinx.html.*

sealed interface Page {
    val title: String
    val block: FlowContent.() -> Unit

    sealed interface Error : Page {
        val status get() = when (this) {
            InternalServerError -> HttpStatusCode.InternalServerError
            NotFound -> HttpStatusCode.NotFound
        }
    }

    sealed interface Content : Page {
        val href: String
        val name: String
    }

    object InternalServerError : Error {
        override val title = "500 - Internal Server Error"
        override val block: FlowContent.() -> Unit = {
            +"view: Something went wrong!"
        }
    }

    object NotFound : Error {
        override val title = "404 - Not Found"
        override val block: FlowContent.() -> Unit = {
            +"view: Page not found"
        }
    }

    object Home : Content {
        override val title = "Jake Junker"
        override val name = "Home"
        override val href = "/"
        override val block: FlowContent.() -> Unit = {
            +"Under construction :)"
        }
    }

    object About : Content {
        override val title = "About - ${Home.title}"
        override val name = "About"
        override val href = "/about"
        override val block: FlowContent.() -> Unit = {
            +"I'll tell you later."
        }
    }
}

fun HTML.renderPage(page: Page) {
    val logoClasses = when (page) {
        is Page.Error -> "site-logo-image error"
        else -> "site-logo-image"
    }

    head {
        meta(charset = "utf-8")
        meta(content = "content-type") { httpEquiv = "text/html; charset=UTF-8" }
        meta(content = "width=device-width, initial-scale=1.0") { name = "viewport" }
        styleLink("/styles.css")

        title(page.title)
    }
    body {
        div(classes = "terminal") {
            header(classes = "terminal-header") {
                div(classes = logoClasses)
                nav(classes = "terminal-nav") {
                    renderNavLinks(page)
                }
            }
            div(classes = "terminal-main") {
                main {
                    id = "main"

                    div("terminal-prompt") {
                        when (page) {
                            is Page.Content -> +"view ${page.href}"
                            is Page.Error -> +""
                        }
                    }

                    div("terminal-output") {
                        with(page) { block() }
                    }
                }
            }
        }
    }
}

private val mainPages = listOf(Page.Home, Page.About)

private fun NAV.renderNavLinks(currentPage: Page) {
    mainPages.map {
        a(
            href = it.href,
            classes = when (it) {
                currentPage -> "nav-link selected"
                else -> "nav-link"
            }
        ) { +it.name }
    }
}
