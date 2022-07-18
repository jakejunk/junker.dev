package dev.junker.index

import kotlinx.html.*

fun HTML.renderIndex() {
    head {
        meta(charset = "utf-8")
        meta(content = "content-type") { httpEquiv = "text/html; charset=UTF-8" }
        meta(content = "width=device-width, initial-scale=1.0") { name = "viewport" }
        styleLink("/styles.css")

        title("Jake Junker")
    }
    body {
        div(classes = "terminal") {
            header {
                + "junker.dev"
            }
            div(classes = "terminal-main") {
                main {
                    id = "main"

                    +"Under construction :)"
                }
            }
        }
    }
}
