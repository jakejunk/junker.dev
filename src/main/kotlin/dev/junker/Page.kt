package dev.junker

import dev.junker.components.drawer.renderDrawer
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
        override val name = "junker.dev"
        override val href = "/"
        override val description = "Just a simple dev trying to make his way in the universe."
        override val block: FlowContent.() -> Unit = {
            +"Under construction :)"
        }
    }

    object About : Content {
        override val title = "About - ${Home.title}"
        override val name = "/about"
        override val href = "/about"
        override val description = "Founded in 1993, Jake somehow got to the point of writing nonsense page descriptions for the internet."
        override val block: FlowContent.() -> Unit = {
            h1(classes = "heading") { +"About me" }
            p {
                +"I'm Jake, a software engineer that rarely completes personal projects."
            }
            p {
                +"Like almost every other developer on this planet, the siren call of game development got me to where I am today; writing Java 8 Spring Boot applications professionally. "
                +"On my own time, I tend to overcomplicate projects for the fun of it. "
                +"Websites, game engines, and programming languages are just some of the things I enjoy hand-rolled."
            }
            p {
                +"It's not all about computers, though! "
                +"I balance this tech-heavy part of my life with a healthy serving of sports, hiking, and traveling. "
                +"I also enjoy trying to learn new languages; as in actual, human languages. "
                +"Maybe someday I'll actually get good at one."
            }
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
        renderFaviconStuff()
        renderFontStuff()
        styleLink("/styles.css")
    }
    body {
        div(classes = "terminal") {
            renderDrawer(page)
            renderTerminalMain(page)
        }
    }
}

private fun HEAD.renderFaviconStuff() {
    meta(name = "msapplication-TileColor", content = "#1abc9c")
    meta(name = "theme-color", content = "#1abc9c")
    link(rel="apple-touch-icon", href="/apple-touch-icon.png") { sizes = "180x180" }
    link(rel="icon", type="image/png", href="/favicon-32x32.png") { sizes = "32x32" }
    link(rel="icon", type="image/png", href="/favicon-16x16.png") { sizes = "16x16" }
    link(rel="manifest", href="/site.webmanifest")
    link(rel="mask-icon", href="/safari-pinned-tab.svg") { attributes["color"] = "#1abc9c" }
}

private fun HEAD.renderFontStuff() {
    link(rel = "preconnect", href = "https://fonts.googleapis.com")
    link(rel = "preconnect", href = "https://fonts.gstatic.com") { attributes["crossorigin"] = "" }
    styleLink("https://fonts.googleapis.com/css2?family=Source+Code+Pro&display=swap")
    styleLink("https://fonts.googleapis.com/css2?family=Montserrat&display=swap")
}
