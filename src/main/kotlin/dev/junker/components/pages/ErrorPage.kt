package dev.junker.components.pages

import kotlinx.html.FlowContent
import kotlinx.html.h1

data object InternalServerErrorPage : Page.Error {
    override val title = "500 - Internal Server Error"
    override val content: FlowContent.() -> Unit = {
        h1(classes = "error") { +"500-server-error" }
    }
}

data object NotFoundPage : Page.Error {
    override val title = "404 - Not Found"
    override val content: FlowContent.() -> Unit = {
        h1(classes = "error") { +"404-not-found" }
    }
}
