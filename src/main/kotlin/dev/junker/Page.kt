package dev.junker

import dev.junker.components.renderTerminalDrawer
import dev.junker.components.renderTerminalMain
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
        val description: String
    }

    object InternalServerError : Error {
        override val title = "500 - Internal Server Error"
        override val block: FlowContent.() -> Unit = {
            +"view: Something went wrong."
        }
    }

    object NotFound : Error {
        override val title = "404 - Not Found"
        override val block: FlowContent.() -> Unit = {
            +"view: Page not found."
        }
    }

    object Home : Content {
        override val title = "Jake Junker"
        override val name = "HOME"
        override val href = "/"
        override val description = "Just a simple dev trying to make his way in the universe."
        override val block: FlowContent.() -> Unit = {
            +"Under construction :)"
        }
    }

    object About : Content {
        override val title = "About - ${Home.title}"
        override val name = "ABOUT"
        override val href = "/about"
        override val description = "Founded in 1993, Jake somehow got to the point of writing nonsense page descriptions for the internet."
        override val block: FlowContent.() -> Unit = {
            +"I'll tell you later."
        }
    }
}

fun HTML.renderPage(page: Page) {
    head {
        meta(charset = "utf-8")
        title(page.title)
        if (page is Page.Content) {
            meta(name = "description", content = page.description)
        }
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        styleLink("/styles.css")
    }
    body {
        div(classes = "terminal") {
            renderTerminalDrawer(page)
            renderTerminalMain(page)
        }
    }
}
