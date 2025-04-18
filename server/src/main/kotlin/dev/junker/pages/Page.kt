package dev.junker.pages

import dev.junker.components.background.background
import dev.junker.components.drawer.drawer
import dev.junker.components.footer.footer
import dev.junker.components.general.skipLink
import dev.junker.components.main.mainContent
import io.ktor.http.*
import kotlinx.html.*

sealed interface Page {
    val content: FlowContent.() -> Unit
    val title: String?

    val isWip: Boolean
        get() = false

    val fullTitle: String
        get() = when (title) {
            null -> "junker.dev"
            else -> "$title - junker.dev"
        }

    sealed interface Error : Page {
        val status get() = when (this) {
            InternalServerErrorPage -> HttpStatusCode.InternalServerError
            NotFoundPage -> HttpStatusCode.NotFound
        }
    }

    sealed interface Content : Page {
        val slug: String
        val description: String
    }
}

fun HTML.page(page: Page) {
    body {
        background()
        skipLink()
        drawer(page)
        mainContent(page)
        footer()
    }
}
