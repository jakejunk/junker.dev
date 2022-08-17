package dev.junker.components.page

import io.ktor.http.*
import kotlinx.html.FlowContent

sealed interface Page {
    val title: String
    val block: FlowContent.() -> Unit

    sealed interface Error : Page {
        val status get() = when (this) {
            InternalServerErrorPage -> HttpStatusCode.InternalServerError
            NotFoundPage -> HttpStatusCode.NotFound
        }
    }

    sealed interface Content : Page {
        val slug: String
        val name: String
        val description: String
    }
}

fun FlowContent.renderPage(page: Page) = with (page) { block() }
