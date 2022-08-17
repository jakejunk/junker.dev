package dev.junker.components.page

import kotlinx.html.FlowContent
import kotlinx.html.h1

object InternalServerErrorPage : Page.Error {
    override val title = "500 - Internal Server Error"
    override val block: FlowContent.() -> Unit = {
        h1(classes = "error") { +"500-server-error" }
    }
}

object NotFoundPage : Page.Error {
    override val title = "404 - Not Found"
    override val block: FlowContent.() -> Unit = {
        h1(classes = "error") { +"404-not-found" }
    }
}
