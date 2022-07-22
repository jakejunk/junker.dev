package dev.junker

import dev.junker.main
import io.ktor.server.html.*
import kotlinx.html.*

private val mainLocations = listOf(Location.Home, Location.About)

sealed interface Location {
    val title: String
    val name: String
    val href: String

    object Home : Location {
        override val title = "Jake Junker"
        override val name = "Home"
        override val href = "/"
    }

    object About : Location {
        override val title = "About - ${Home.title}"
        override val name = "About"
        override val href = "/about"
    }
}

class BasePage(private val location: Location) : Template<HTML> {
    val content = Placeholder<FlowContent>()

    override fun HTML.apply() {
        head {
            meta(charset = "utf-8")
            meta(content = "content-type") { httpEquiv = "text/html; charset=UTF-8" }
            meta(content = "width=device-width, initial-scale=1.0") { name = "viewport" }
            styleLink("/styles.css")

            title(location.title)
        }
        body {
            div(classes = "terminal") {
                header(classes = "terminal-header") {
                    div(classes = "site-logo-image")
                    nav(classes = "terminal-nav") {
                        renderNavLinks()
                    }
                }
                div(classes = "terminal-main") {
                    main {
                        id = "main"

                        div("terminal-prompt") {
                            + "view ${location.href}"
                        }

                        div("terminal-output") {
                            insert(content)
                        }
                    }
                }
            }
        }
    }

    private fun NAV.renderNavLinks() {
        mainLocations.map {
            a(
                href = it.href,
                classes = when (it) {
                    location -> "nav-link selected"
                    else -> "nav-link"
                }
            ) { +it.name }
        }
    }
}