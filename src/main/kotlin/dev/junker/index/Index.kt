package dev.junker.index

import kotlinx.html.*

fun HTML.renderIndex() {
    head {
        link(rel = "stylesheet", href = "/styles.css", type = "text/css")
        title("Jake Junker")
    }
    body {
        h1(classes = "page-title") {
            +"junker.dev"
        }
    }
}