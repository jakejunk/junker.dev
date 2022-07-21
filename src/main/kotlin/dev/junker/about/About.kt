package dev.junker.about

import kotlinx.html.*

fun HTML.renderAbout() {
    head {
        meta(charset = "utf-8")
        meta(content = "content-type") { httpEquiv = "text/html; charset=UTF-8" }
        meta(content = "width=device-width, initial-scale=1.0") { name = "viewport" }
        styleLink("/styles.css")

        title("About - Jake Junker")
    }
    body {
        div(classes = "terminal") {
            header(classes = "terminal-header") {
                div(classes = "site-logo-image")
                nav(classes = "terminal-nav") {
                    a(href = "/", classes = "nav-link") { + "Home" }
                    a(href = "/about", classes = "nav-link selected") { + "About" }
                }
            }
            div(classes = "terminal-main") {
                main {
                    id = "main"

                    div("terminal-prompt") {
                        + "view /about"
                    }

                    div("terminal-output") {
                        +"I'll tell you later."
                    }
                }
            }
        }
    }
}
